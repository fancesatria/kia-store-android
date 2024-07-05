package com.project.ecommmerce_2.Response;

import com.project.ecommmerce_2.Model.RegisterModel;

public class RegisterResponse {
    private String pesan, token;
    private int status;
    private RegisterModel data;

    public String getPesan() {
        return pesan;
    }

    public void setPesan(String pesan) {
        this.pesan = pesan;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public RegisterModel getData() {
        return data;
    }

    public void setData(RegisterModel data) {
        this.data = data;
    }

}
