package com.project.ecommmerce_2.Model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "barangs")
public class ProductModel {
    private String nama, harga, gambar, kategori;
    private int id_kategori;
    @PrimaryKey@NonNull
    private int id;

    public ProductModel(String nama, String harga, String gambar, String kategori, int id, int id_kategori) {
        this.nama = nama;
        this.harga = harga;
        this.gambar = gambar;
        this.kategori = kategori;
        this.id = id;
        this.id_kategori = id_kategori;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_kategori() {
        return id_kategori;
    }

    public void setId_kategori(int id_kategori) {
        this.id_kategori = id_kategori;
    }
}
