package com.biryo.kamus_plesetan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MainActivity extends AppCompatActivity {
    Spinner spinner;
    String fitur;
    //ini banner
    String ADUNIT_ID = "ca-app-pub-6929212370466200/8558859131";
//    banner real ca-app-pub-6929212370466200/8558859131
//    inter real ca-app-pub-6929212370466200/4736638973

//     inter test ca-app-pub-3940256099942544/1033173712
    // banner test ca-app-pub-3940256099942544/6300978111
    FrameLayout adcontainer;
    AdView adView;
    private InterstitialAd mInterstitialAd;
    ExecutorService es = Executors.newSingleThreadExecutor();
    ProgressDialog progressDialog;
    TextView seeAll;
    long actualSearchTime;
    long lastSearchTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mInterstitialAd = new InterstitialAd(this);
        //ini inter
        mInterstitialAd.setAdUnitId("ca-app-pub-6929212370466200/4736638973");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        adcontainer = findViewById(R.id.adviewContainer);
        adcontainer.post(new Runnable() {
            @Override
            public void run() {
                loadBanner();
            }
        });

        spinner = (Spinner) findViewById(R.id.spinner4);

        final SearchView searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String query) {

                actualSearchTime = (Calendar.getInstance()).getTimeInMillis();
                // Only one search every second to avoid key-down & key-up

                if (actualSearchTime > lastSearchTime + 1000)
                {
                    lastSearchTime=actualSearchTime;
                    progressDialog = new ProgressDialog(MainActivity.this);
                    progressDialog.show();
                    progressDialog.setContentView(R.layout.progress_dialog);
                    progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                    fitur = spinner.getSelectedItem().toString();

                    final Bundle b = new Bundle();
                    final Intent i = new Intent(MainActivity.this, result_plesetan.class);
                    i.putExtra("query", query);
                    i.putExtra("fitur", fitur);

                    es.submit(new Runnable() {
                        Future<List<String>> bgr = es.submit(new Ucapan(query,MainActivity.this,fitur));

                        @Override
                        public void run() {
                            Log.d("BG Thread","harus nya ini 1 kali di void run");

                            while(true){//Loop Trs buat ditek bg proses udh selesai, akan stop jika hasilnya sudah keluar
                                if (bgr.isDone()) { //logika pencocokan udh kelar blm bg prosesnye
                                    Log.d("BG Thread","harus nya ini 1 kali bgr is done");
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {

                                            try {
                                                final List<String> res = bgr.get();
                                                progressDialog.dismiss();

                                                if (res.size() != 0) {
                                                    if (mInterstitialAd.isLoaded()) {
                                                        mInterstitialAd.show();
                                                        mInterstitialAd.setAdListener(new AdListener() {

                                                            @Override
                                                            public void onAdFailedToLoad(LoadAdError adError) {
                                                                b.putSerializable("array", (Serializable) res);
                                                                i.putExtras(b);
                                                                startActivity(i);
                                                            }

                                                            @Override
                                                            public void onAdClosed() {
                                                                mInterstitialAd.loadAd(new AdRequest.Builder().build());
                                                                b.putSerializable("array", (Serializable) res);
                                                                i.putExtras(b);
                                                                startActivity(i);
                                                            }
                                                        });
                                                    }
                                                    else {
                                                        mInterstitialAd.loadAd(new AdRequest.Builder().build());
                                                        b.putSerializable("array", (Serializable) res);
                                                        i.putExtras(b);
                                                        startActivity(i);
                                                    }
                                                }
                                                else{
                                                    Toast.makeText(MainActivity.this,"Pencarian Tidak Ditemukan",Toast.LENGTH_SHORT).show();
                                                }

                                            } catch (ExecutionException e) {
                                                e.printStackTrace();
                                            } catch (InterruptedException e) {
                                                e.printStackTrace();
                                            }

                                        }
                                    });

                                    break;
                                }else{

                                }
                            }
                        }
                    });
                }else{

                }

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        seeAll = findViewById(R.id.seeAll);
        seeAll.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                spinner.setSelection(2);
                searchView.setQuery("Tampilkan Semua Data", true);
                return false;
            }
        });
    }

    private void loadBanner() {
        adView = new AdView(MainActivity.this);
        adView.setAdUnitId(ADUNIT_ID);
        adcontainer.removeAllViews();
        adcontainer.addView(adView);

        AdSize adSize = getAdSize();
        adView.setAdSize(adSize);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
    }

    private AdSize getAdSize() {
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        display.getMetrics(displayMetrics);

        float density = displayMetrics.density;
        float adwithpixels = adcontainer.getWidth();

        if(adwithpixels == 0){
            adwithpixels = displayMetrics.widthPixels;
        }

        int adWidth = (int) (adwithpixels / density);
        return AdSize.getCurrentOrientationBannerAdSizeWithWidth(MainActivity.this,adWidth);
    }

    public void showInt(){
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            mInterstitialAd.loadAd(new AdRequest.Builder().build());
        }
    }

}




