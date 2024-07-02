package com.project.ecommmerce_2.Model;

public class ProfileModel {
    private int id;
    private String name;
    private String email;
    private String level;
    private String token;
    private String nomor_handphone;
    private String provinsi;
    private String kota_kabupaten;
    private String kecamatan;
    private String kode_pos;
    private String alamat_lengkap;
    private String created_at;
    private String updated_at;

    // Constructors, getters and setters
    public ProfileModel() {}

    public ProfileModel(int id, String name, String email, String level, String token, String nomor_handphone, String provinsi, String kota_kabupaten, String kecamatan, String kode_pos, String alamat_lengkap, String created_at, String updated_at) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.level = level;
        this.token = token;
        this.nomor_handphone = nomor_handphone;
        this.provinsi = provinsi;
        this.kota_kabupaten = kota_kabupaten;
        this.kecamatan = kecamatan;
        this.kode_pos = kode_pos;
        this.alamat_lengkap = alamat_lengkap;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getNomor_handphone() {
        return nomor_handphone;
    }

    public void setNomor_handphone(String nomor_handphone) {
        this.nomor_handphone = nomor_handphone;
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

    public String getKecamatan() {
        return kecamatan;
    }

    public void setKecamatan(String kecamatan) {
        this.kecamatan = kecamatan;
    }

    public String getKode_pos() {
        return kode_pos;
    }

    public void setKode_pos(String kode_pos) {
        this.kode_pos = kode_pos;
    }

    public String getAlamat_lengkap() {
        return alamat_lengkap;
    }

    public void setAlamat_lengkap(String alamat_lengkap) {
        this.alamat_lengkap = alamat_lengkap;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}
