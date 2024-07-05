package com.project.ecommmerce_2.RajaOngkir.DataProcessing.city;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CityResponse implements Serializable {
    @SerializedName("rajaongkir")
    @Expose
    public RajaOngkirCity rajaongkir;

    public RajaOngkirCity getRajaongkir() {
        return rajaongkir;
    }

    public void setRajaongkir(RajaOngkirCity rajaongkir) {
        this.rajaongkir = rajaongkir;
    }
}
