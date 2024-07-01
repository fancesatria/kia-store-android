package com.project.ecommmerce_2.Model;

public class RegisterModel {
    public String name, email, password, nomor_handphone, provinsi, kota_kabupaten, kode_pos, alamat_lengkap;
    public int id;

    public RegisterModel() {
        this.name = name;
        this.email = email;
        this.password = password;
        this.nomor_handphone = nomor_handphone;
        this.provinsi = provinsi;
        this.kota_kabupaten = kota_kabupaten;
        this.kode_pos = kode_pos;
        this.alamat_lengkap = alamat_lengkap;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}
