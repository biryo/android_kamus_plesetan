package com.biryo.kamus_plesetan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class result_plesetan extends AppCompatActivity {
    String key;
    String fitur;
    Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_plesetan);

//        RecyclerView risekel = findViewById(R.id.risekel);
//        risekel.setHasFixedSize(true);
//        risekel.setLayoutManager(new LinearLayoutManager(this));
//
//        data_plesetan[] data = new data_plesetan[10];
//        for (int x = 0; x < 10 ; x++) data[x]= new data_plesetan("siap");
//
//        data_adapter data_adapters = new data_adapter(data,result_plesetan.this);
//        risekel.setAdapter(data_adapters);

        extras = getIntent().getExtras();
        key = extras.getString("query");
        List<String> result = (List<String>) extras.getSerializable("array");
        fitur = extras.getString("fitur");

        if(fitur.equals("Ucapan")){
//            Ucapan(key);
            RecyclerView risekel = findViewById(R.id.risekel);
            risekel.setHasFixedSize(true);
            risekel.setLayoutManager(new LinearLayoutManager(this));

            data_plesetan[] data = new data_plesetan[result.size()];
            for (int x = 0; x < result.size(); x++) data[x] = new data_plesetan(result.get(x));

            data_adapter data_adapters = new data_adapter(data, result_plesetan.this, "Ucapan");
            risekel.setAdapter(data_adapters);
        }else if(fitur.equals("Akronim")){
//            Akronim(key);
            RecyclerView risekel = findViewById(R.id.risekel);
            risekel.setHasFixedSize(true);
            risekel.setLayoutManager(new LinearLayoutManager(this));

            data_plesetan[] data = new data_plesetan[result.size()];
            for (int x = 0; x < result.size(); x++) data[x] = new data_plesetan(result.get(x));

            data_adapter data_adapters = new data_adapter(data, result_plesetan.this, "Akronim");
            risekel.setAdapter(data_adapters);
        }else{
//            Makna();
            RecyclerView risekel = findViewById(R.id.risekel);
            risekel.setHasFixedSize(true);
            risekel.setLayoutManager(new LinearLayoutManager(this));

            data_plesetan[] data = new data_plesetan[result.size()];
            for (int x = 0; x < result.size(); x++) data[x] = new data_plesetan(result.get(x));

            data_adapter data_adapters = new data_adapter(data, result_plesetan.this, "Makna");
            risekel.setAdapter(data_adapters);
        }
    }

//
//    public void Ucapan(String key){
//        String temp = "NullSksasds";
//        JSONArray mJsonArray = null;
//        List<String> result = new ArrayList<>();
//        try {
//            mJsonArray = new JSONArray(LoadLocalJson());
//            for(int x = 0; x < mJsonArray.length(); x++){
//                JSONObject mJsonObject = mJsonArray.getJSONObject(x);
//                String kata = mJsonObject.getString("kata");
//
//                String input =key.trim();
//                String output =kata.trim();
//                String[] arrIn = input.split("");
//                String[] arrOut = output.split("");
//                Integer jmlVokalIn = 0;
//                Integer jmlVokalOut = 0;
//                Integer jmlCocok = 0;
//
//                for(int i = 0; i < Math.min(arrIn.length,arrOut.length); i++){
//                    if (arrIn[i].equals("a") || arrIn[i].equals("i") || arrIn[i].equals("u") || arrIn[i].equals("e") || arrIn[i].equals("o")) {
//                        jmlVokalIn++;
//                    }
//
//                    if (arrIn[i].equals(arrOut[i])){
//                        if (arrIn[i].equals("a") || arrIn[i].equals("i") || arrIn[i].equals("u") || arrIn[i].equals("e") || arrIn[i].equals("o")) {
//                            jmlVokalOut++;
//                        }
//                        jmlCocok++;
//                    }
//                }
//
//                if (jmlVokalIn == jmlVokalOut && jmlCocok == (arrIn.length - 1) && !temp.equals(output) && output.length() == key.length()) {
////                    Toast.makeText(getBaseContext(), kata, Toast.LENGTH_SHORT).show();
//                    result.add(output);
//                    temp = output;
//                }else{
////                    Toast.makeText(getBaseContext(), "Kata Tidak Ditemukan", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            if (result.size() != 0) {
//                RecyclerView risekel = findViewById(R.id.risekel);
//                risekel.setHasFixedSize(true);
//                risekel.setLayoutManager(new LinearLayoutManager(this));
//
//                data_plesetan[] data = new data_plesetan[result.size()];
//                for (int x = 0; x < result.size(); x++) data[x] = new data_plesetan(result.get(x));
//
//                data_adapter data_adapters = new data_adapter(data, result_plesetan.this, "Ucapan");
//                risekel.setAdapter(data_adapters);
//            }else{
//                finish();
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void Akronim(String key){
//        RecyclerView risekel = findViewById(R.id.risekel);
//        risekel.setHasFixedSize(true);
//        risekel.setLayoutManager(new LinearLayoutManager(this));
//
//        JSONArray mJsonArray = null;
//        try {
//            mJsonArray = new JSONArray(LoadLocalJson());
//            List<String> result = new ArrayList<>();
//            String temp = "nullokasodkasd";
//
//            for(int x = 0; x < mJsonArray.length(); x++){
//                JSONObject mJsonObject = mJsonArray.getJSONObject(x);
//                String kata = mJsonObject.getString("kata");
//
//                String input =key;
//                String output =kata.trim();
//                String[] arrIn =input.split(" ");
//                String[] arrOut =output.split("");
//                Integer indexOut =0;
//                Integer indexD1 =0;
//                Integer indexD2 =0;
//
//                List<String[]> arrInp = new ArrayList<String[]>();
//                for (int i=0;i<arrIn.length; i++) {
//                    arrInp.add(arrIn[i].split(""));
//                }
//
//                // Jika Huruf Pertama Pada Input dan Output Sama Maka ...
//                if (arrOut[0].equals(arrInp.get(0)[0])) {
//                    for (; indexD2<arrInp.get(indexD1).length; ) {//Looping buat cocokin setiap huruf output dengan input
//                        if (arrOut[indexOut].equals(arrInp.get(indexD1)[indexD2])) {//pencocokan setiap huruf sama atau tidak berdasarkan index ke brp
//                            if(indexD1 == (arrIn.length-1) && (arrOut.length-1) == indexOut && !output.equals(temp)){//cocokin jika sarat semua perwakilan kata pada input mewakili output
//                                temp = output;
//                                result.add(output);
////                                Toast.makeText(getBaseContext(), indexD1, Toast.LENGTH_SHORT).show();
//                            }else{}
//
//                            //klo nilainya masih ada atau tidak habis atau bukan kata terakhir pada setiap kalimat
//                            if (indexOut < (arrOut.length-1)) {
//                                indexOut++;
//                            }else{
//                                break;
//                            }
//
//                            if(indexD2 < (arrInp.get(indexD1).length-1)) {//bukan huruf terakhir maka ...
//                                indexD2++;
//                            }else{
//                                if((arrIn.length-1) > indexD1){//lanjut cari jika huruf terakhir pada 1 kata yg mewakili 1 kalimat
//                                    indexD1++;
//                                    indexD2=0;
//                                }else{ // selesai kalo udh kata dan huruf terakhir
//                                    break;
//                                }
//                            }
//                        }else{
//                            if(indexD1 > arrIn.length){ // kalo nilainye melebihi panjang array berhentiin
//                                break;
//                            }else{
//                                if((arrIn.length - 1) > indexD1){//cek ketersediaan kata selanjutnya
//                                    if (indexD2 == 0) {//kalo udah 2 kali skip setiap kata dan tidak ada kata yg sama pertama kali(indexd2=0) maka berhenti
//                                        break;
//                                    }else{
//                                        indexD1++;
//                                        indexD2=0;
//                                    }
//                                }else{
//                                    break;
//                                }
//                            }
//                        }
//                    }
//                }
//
////                if(output != "tidak cocok"){
////                    System.out.println("cocok");
////                }else{
////                    System.out.println("tidak cocok");
////                }
//                //
//
//            }
//
//
//            data_plesetan[] data = new data_plesetan[result.size()];
//            for (int x = 0; x < result.size() ; x++) data[x]= new data_plesetan(result.get(x));
//
//            data_adapter data_adapters = new data_adapter(data,result_plesetan.this,"Akronim");
//            risekel.setAdapter(data_adapters);

//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }
//
}
