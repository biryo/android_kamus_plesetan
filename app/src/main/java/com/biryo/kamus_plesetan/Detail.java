package com.biryo.kamus_plesetan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;

public class Detail extends AppCompatActivity {
    String key;
    String fitur;
    Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Bundle extras = getIntent().getExtras();
        String key = extras.getString("query");
        String fitur = extras.getString("fitur");

        if(fitur.equals("Ucapan")){
            Ucapan(key);
        }else if(fitur.equals("Akronim")){
            Akronim(key);
        }else{
            Makna(key);
        }
    }


    public void Ucapan(String key){
        RecyclerView risekel = findViewById(R.id.risekel_detail);
        risekel.setHasFixedSize(true);
        risekel.setLayoutManager(new LinearLayoutManager(this));

        JSONArray mJsonArray = null;
        try {
            mJsonArray = new JSONArray(LoadLocalJson());
//            List<LinkedHashSet<String>> result = new ArrayList<>();
            List<List<String>> result = new ArrayList<List<String>>();

            for(int x = 0; x < mJsonArray.length(); x++){
                JSONObject mJsonObject = mJsonArray.getJSONObject(x);
                String kata = mJsonObject.getString("kata");
                String makna = mJsonObject.getString("makna_kata");

                if (kata.indexOf(key.trim()) != -1){
                    result.add(new ArrayList<String>(Arrays.asList(kata,makna)));
//                    Toast.makeText(getBaseContext(), output, Toast.LENGTH_SHORT).show();
                }else{

                }
            }

            detail_data[] data = new detail_data[result.size()];
            for (int x = 0; x < result.size() ; x++) data[x]= new detail_data(result.get(x).get(0),result.get(x).get(1));

            detail_adapter data_adapters = new detail_adapter(data,Detail.this);
            risekel.setAdapter(data_adapters);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void Akronim(String key){
        RecyclerView risekel = findViewById(R.id.risekel_detail);
        risekel.setHasFixedSize(true);
        risekel.setLayoutManager(new LinearLayoutManager(this));

        JSONArray mJsonArray = null;
        try {
            mJsonArray = new JSONArray(LoadLocalJson());
//            List<LinkedHashSet<String>> result = new ArrayList<>();
            List<List<String>> result = new ArrayList<List<String>>();

            for(int x = 0; x < mJsonArray.length(); x++){
                JSONObject mJsonObject = mJsonArray.getJSONObject(x);
                String kata = mJsonObject.getString("kata");
                String makna = mJsonObject.getString("makna_kata");

                if (kata.equals(key)){
                    result.add(new ArrayList<String>(Arrays.asList(kata,makna)));
//                    Toast.makeText(getBaseContext(), output, Toast.LENGTH_SHORT).show();
                }else{

                }
            }

            detail_data[] data = new detail_data[result.size()];
            for (int x = 0; x < result.size() ; x++) data[x]= new detail_data(result.get(x).get(0),result.get(x).get(1));

            detail_adapter data_adapters = new detail_adapter(data,Detail.this);
            risekel.setAdapter(data_adapters);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void Makna(String key){
        RecyclerView risekel = findViewById(R.id.risekel_detail);
        risekel.setHasFixedSize(true);
        risekel.setLayoutManager(new LinearLayoutManager(this));

        JSONArray mJsonArray = null;
        try {
            mJsonArray = new JSONArray(LoadLocalJson());
//            List<LinkedHashSet<String>> result = new ArrayList<>();
            List<List<String>> result = new ArrayList<List<String>>();

            for(int x = 0; x < mJsonArray.length(); x++){
                JSONObject mJsonObject = mJsonArray.getJSONObject(x);
                String kata = mJsonObject.getString("kata");
                String makna = mJsonObject.getString("makna_kata");

                if (kata.equals(key)){
                    result.add(new ArrayList<String>(Arrays.asList(kata,makna)));
//                    Toast.makeText(getBaseContext(), output, Toast.LENGTH_SHORT).show();
                }else{

                }
            }

            detail_data[] data = new detail_data[result.size()];
            for (int x = 0; x < result.size() ; x++) data[x]= new detail_data(result.get(x).get(0),result.get(x).get(1));

            detail_adapter data_adapters = new detail_adapter(data,Detail.this);
            risekel.setAdapter(data_adapters);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String LoadLocalJson(){
        String json = null;
        try {
            InputStream in = this.getAssets().open("db.json");
            int size = in.available();
            byte[] bbufer = new byte[size];
            in.read(bbufer);
            in.close();

            json = new String(bbufer, "UTF-8");
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }
        return json;
    }
}