package com.biryo.kamus_plesetan;


import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.app.AppOpsManagerCompat;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

class Ucapan implements Callable<List<String>> {
    String key;
    Context context;
    String fitur;

    public Ucapan(String key, Context context, String fitur) {
        this.key = key;
        this.context = context;
        this.fitur = fitur;
    }

    @Override
    public List<String> call() throws Exception {
        InputStream in = context.getAssets().open("db.json");
        int size = in.available();
        byte[] bbufer = new byte[size];
        in.read(bbufer);
        in.close();
        String json = new String(bbufer, "UTF-8");

        JSONArray mJsonArray = new JSONArray(json);
        String temp = "ini Kata yang tidak ada dua nya";
        List<String> result = new ArrayList<>();
        String temp2 = "nullasodasdasdasdasdasd";

        if (fitur.equals("Ucapan")){
            for(int x = 0; x < mJsonArray.length(); x++){
                JSONObject mJsonObject = mJsonArray.getJSONObject(x);
                String kata = mJsonObject.getString("kata");
                if(!temp.equals(kata)) {
                    temp = kata;
                    String input = key.trim().toLowerCase();
                    String output = kata.trim().toLowerCase();
                    String[] arrIn = input.split("");
                    String[] arrOut = output.split("");
                    Integer jmlVokalIn = 0;
                    Integer jmlVokalOut = 0;
                    Integer jmlCocok = 0;

                    for (int i = 0; i < Math.min(arrIn.length, arrOut.length); i++) {
                        if (arrIn[i].equals("a") || arrIn[i].equals("i") || arrIn[i].equals("u") || arrIn[i].equals("e") || arrIn[i].equals("o")) {
                            jmlVokalIn++;
                        }

                        if (arrIn[i].equals(arrOut[i])) {
                            if (arrIn[i].equals("a") || arrIn[i].equals("i") || arrIn[i].equals("u") || arrIn[i].equals("e") || arrIn[i].equals("o")) {
                                jmlVokalOut++;
                            }
                            jmlCocok++;
                        }
                    }

                    if (jmlVokalIn == jmlVokalOut && jmlCocok == (arrIn.length - 1) && output.length() == key.length()) {
                        result.add(output);
                    } else {

                    }
                }else{}
            }
        }else if(fitur.equals("Akronim")){
            for(int x = 0; x < mJsonArray.length(); x++){
                JSONObject mJsonObject = mJsonArray.getJSONObject(x);
                String kata = mJsonObject.getString("kata");

                if(!temp.equals(kata)) {
                    temp = kata;

                    String input=key.toLowerCase();
                    String output=kata.trim().toLowerCase();
                    String[] arrIn =input.split(" ");
                    String[] arrOuts =output.split("");
                    Integer indexOut = 0;
                    Integer indexD1 = 0;
                    Integer indexD2 = 0;

                    String[] arrOut = new String[arrOuts.length-1];
                    for (int i = 0; i < arrOuts.length-1; i++) {
                        arrOut[i] = arrOuts[i + 1];
                    }

                    List<String[]> arrInp = new ArrayList<String[]>();

                    for (int i=0; i<arrIn.length; i++) {
                        String[] temparr =arrIn[i].split("");
                        String[] isiarr = new String[temparr.length-1];
                        for (int g = 0; g < temparr.length-1; g++) {
                            isiarr[g] = temparr[g + 1];
                        }
                        arrInp.add(isiarr);
                    }

                    // Jika Huruf Pertama Pada Input dan Output Sama Maka ...
                    if (arrOut[0].equals(arrInp.get(0)[0])) {
                        for (; indexD2 < arrInp.get(indexD1).length; ) {//Looping buat cocokin setiap huruf output dengan input
                            if (arrOut[indexOut].equals(arrInp.get(indexD1)[indexD2])) {//pencocokan setiap huruf sama atau tidak berdasarkan index ke brp
                                if (indexD1 == (arrIn.length - 1) && (arrOut.length - 1) == indexOut) {//cocokin jika sarat semua perwakilan kata pada input mewakili output
                                    result.add(kata);
                                    break;
                                }else {

                                }

                                //klo nilainya masih ada atau tidak habis atau bukan kata terakhir pada setiap kalimat
                                if (indexOut < (arrOut.length - 1)) {
                                    indexOut++;
                                } else {
                                    break;
                                }

                                if (indexD2 < (arrInp.get(indexD1).length - 1)) {//bukan huruf terakhir maka ...
                                    indexD2++;
                                } else {
                                    if ((arrIn.length - 1) > indexD1) {//lanjut cari jika huruf terakhir pada 1 kata yg mewakili 1 kalimat
                                        indexD1++;
                                        indexD2 = 0;
                                    } else { // selesai kalo udh kata dan huruf terakhir
                                        break;
                                    }
                                }
                            } else {
                                if (indexD1 > arrIn.length) { // kalo nilainye melebihi panjang array berhentiin
                                    break;
                                } else {
                                    if ((arrIn.length - 1) > indexD1) {//cek ketersediaan kata selanjutnya
                                        if (indexD2 == 0) {//kalo udah 2 kali skip setiap kata dan tidak ada kata yg sama pertama kali(indexd2=0) maka berhenti
                                            break;
                                        } else {
                                            indexD1++;
                                            indexD2 = 0;
                                        }
                                    } else {
                                        break;
                                    }
                                }
                            }
                        }


                    } else {

                    }
                }else{

                }

            }
        }else{
            for(int x = 0; x < mJsonArray.length(); x++){
                JSONObject mJsonObject = mJsonArray.getJSONObject(x);
                String kata = mJsonObject.getString("kata");

                String output =kata.trim().toLowerCase();
                String input=key.toLowerCase().equals("tampilkan semua data") ? output: key.toLowerCase() ;

                if (output.equals(temp) && !temp2.equals(temp) && output.equals(input)){
                    temp = output;
                    temp2 = output;
                    result.add(output);
                }else{
                    temp = output;
                }
            }
        }

        return result;
    }
}
