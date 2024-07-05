package com.project.ecommmerce_2.Model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class OrderModel {
    private String snap_token;
    private String status;
    private String created_at;
    private String total_harga;
    private String username;
    private String total_jumlah;
    private String nama;

    public OrderModel(String snap_token, String created_at, String status_order, String total_harga, String user_name, String total_jumlah, String nama) {
        this.snap_token = snap_token;
        this.created_at = created_at;
        this.status = status_order;
        this.total_harga = total_harga;
        this.username = user_name;
        this.total_jumlah = total_jumlah;
        this.nama = nama;
    }

    public String getSnap_token() {
        return snap_token;
    }

    public void setSnap_token(String snap_token) {
        this.snap_token = snap_token;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTotal_harga() {
        return total_harga;
    }

    public void setTotal_harga(String total_harga) {
        this.total_harga = total_harga;
    }

    public String newDate() throws ParseException {
        // Parsing tanggal input
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        Date tanggal = inputFormat.parse(getCreated_at());

        // Memformat ulang tanggal
        SimpleDateFormat outputFormat = new SimpleDateFormat("EEEE, MMMM dd yyyy", Locale.US);
        return outputFormat.format(tanggal);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTotal_jumlah() {
        return total_jumlah;
    }

    public void setTotal_jumlah(String total_jumlah) {
        this.total_jumlah = total_jumlah;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }
}
