package com.example.restapi.Request;

import lombok.Getter;

@Getter
public class HapeReq {
    private String nama;
    private String merk;
    private String harga;
    private String kondisi;
    private String deskripsi;

    public HapeReq(){

    }

    public HapeReq(String nama, String merk, String harga, String kondisi, String deskripsi) {
        this.nama = nama;
        this.merk = merk;
        this.harga = harga;
        this.kondisi = kondisi;
        this.deskripsi = deskripsi;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setMerk(String merk) {
        this.merk = merk;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public void setKondisi(String kondisi) {
        this.kondisi = kondisi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }
}
