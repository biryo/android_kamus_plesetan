package com.biryo.kamus_plesetan;

public class detail_data {
    private String kata;
    private String makna;

    public detail_data(String kata, String makna) {
        this.kata = kata;
        this.makna = makna;
    }

    public String getKata() {
        return kata;
    }

    public void setKata(String kata) {
        this.kata = kata;
    }

    public String getMakna() {
        return makna;
    }

    public void setMakna(String makna) {
        this.makna = makna;
    }
}
