package com.project.ecommmerce_2.Response;

import com.project.ecommmerce_2.Model.OrderDetailModel;
import com.project.ecommmerce_2.Model.OrderDetailModel;

import java.util.List;

public class OrderDetailResponse {
    private boolean success;
    private List<OrderDetailModel> orders;
    private int total_harga;
    private String status;
    private String ongkir;
    private String harga_final;
    private String resi;

    public String getOngkir() {
        return ongkir;
    }

    public void setOngkir(String ongkir) {
        this.ongkir = ongkir;
    }

    public String getHarga_final() {
        return harga_final;
    }

    public void setHarga_final(String harga_final) {
        this.harga_final = harga_final;
    }

    public String getResi() {
        return resi;
    }

    public void setResi(String resi) {
        this.resi = resi;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<OrderDetailModel> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderDetailModel> orders) {
        this.orders = orders;
    }

    public int getTotal_harga() {
        return total_harga;
    }

    public void setTotal_harga(int total_harga) {
        this.total_harga = total_harga;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}