package com.example.restapi.Models;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "hp")
public class hp {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer id;

    @Column(name = "nama")
    public String nama;

    @Column(name = "harga")
    public String harga;

    @Column(name = "deskripsi")
    public String deskripsi;

    @Column(name = "tahun_rilis")
    public String rilis;

    @Lob
    @Column(name = "foto")
    public String foto;

    @Transient
    public String getImagePath(){
        if (foto == null) return null;
        return "uploads/" + id + "/" + foto;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public void setRilis(String rilis) {
        this.rilis = rilis;
    }
}
