package com.project.ecommmerce_2.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PaymentModel {
    @SerializedName("user_id")
    private int user_id;

    @SerializedName("items")
    private List<Item> items;

    @SerializedName("provinsi")
    private String provinsi;

    @SerializedName("kota_kabupaten")
    private String kota_kabupaten;

    @SerializedName("kecamatan")
    private String kecamatan;

    @SerializedName("alamat_lengkap")
    private String alamat_lengkap;

    @SerializedName("nama_ongkir")
    private String nama_ongkir;

    @SerializedName("harga_ongkir")
    private String harga_ongkir;


    public PaymentModel(int user_id, List<Item> items, String provinsi, String kota_kabupaten, String kecamatan,String alamat_lengkap, String nama_ongkir, String harga_ongkir) {
        this.user_id = user_id;
        this.items = items;
        this.provinsi = provinsi;
        this.kota_kabupaten = kota_kabupaten;
        this.kecamatan = kecamatan;
        this.alamat_lengkap = alamat_lengkap;
        this.nama_ongkir = nama_ongkir;
        this.harga_ongkir = harga_ongkir;
    }

    public String getKecamatan() {
        return kecamatan;
    }

    public void setKecamatan(String kecamatan) {
        this.kecamatan = kecamatan;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getProvinsi() {
        return provinsi;
    }

    public void setProvinsi(String provinsi) {
        this.provinsi = provinsi;
    }

    public String getKota_kabupaten() {
        return kota_kabupaten;
    }

    public void setKota_kabupaten(String kota_kabupaten) {
        this.kota_kabupaten = kota_kabupaten;
    }

    public String getAlamat_lengkap() {
        return alamat_lengkap;
    }

    public void setAlamat_lengkap(String alamat_lengkap) {
        this.alamat_lengkap = alamat_lengkap;
    }

    public String getNama_ongkir() {
        return nama_ongkir;
    }

    public void setNama_ongkir(String nama_ongkir) {
        this.nama_ongkir = nama_ongkir;
    }

    public String getHarga_ongkir() {
        return harga_ongkir;
    }

    public void setHarga_ongkir(String harga_ongkir) {
        this.harga_ongkir = harga_ongkir;
    }

    public int getUserId() {
        return user_id;
    }

    public void setUserId(int userId) {
        this.user_id = userId;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public static class Item {
        @SerializedName("id")
        private String id; // Changed from int to String

        @SerializedName("jumlah")
        private String jumlah; // Changed from int to String

        public Item(String id, String jumlah) {
            this.id = id;
            this.jumlah = jumlah;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getJumlah() {
            return jumlah;
        }

        public void setJumlah(String jumlah) {
            this.jumlah = jumlah;
        }
    }
}
