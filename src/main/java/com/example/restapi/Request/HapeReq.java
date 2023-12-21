package com.example.restapi.Request;

import lombok.Getter;

@Getter
public class HapeReq {
    private String nama;
    private String rilis;
    private String harga;
    private String kondisi;
    private String deskripsi;

    public HapeReq(){

    }

    public HapeReq(String nama, String rilis, String harga, String kondisi, String deskripsi) {
        this.nama = nama;
        this.rilis = rilis;
        this.harga = harga;
        this.kondisi = kondisi;
        this.deskripsi = deskripsi;
    }

    public void setNama(String nama) {
        this.nama = nama;
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

    public void setRilis(String rilis) {
        this.rilis = rilis;
    }
}
