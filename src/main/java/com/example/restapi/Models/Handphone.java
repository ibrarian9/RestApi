package com.example.restapi.Models;

import jakarta.persistence.*;
import lombok.Getter;

import java.sql.Date;
import java.sql.Timestamp;

@Getter
@Entity
@Table(name = "handphone")
public class Handphone {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer id;

    @Column(name = "nama")
    public String nama;

    @Column(name = "merk")
    public String merk;

    @Column(name = "harga")
    public String harga;

    @Column(name = "kondisi")
    public String kondisi;

    @Column(name = "deskripsi")
    public String deskripsi;

    @Column(name = "tanggal_publish")
    public java.sql.Date publish;

    @Column(nullable = true, length = 240)
    public String foto;

    @Transient
    public String getPhotosImagePath(){
        if (foto == null) return null;
        return "uploads/" + id + "/" + foto;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public void setPublish(Date publish) {
        this.publish = publish;
    }
}
