package com.project.ecommmerce_2.RajaOngkir.DataProcessing.cost;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CostResponse implements Serializable {
    @SerializedName("rajaongkir")
    @Expose
    public RajaOngkirCost rajaongkir;

    public RajaOngkirCost getRajaongkir() {
        return rajaongkir;
    }

    public void setRajaongkir(RajaOngkirCost rajaongkir) {
        this.rajaongkir = rajaongkir;
    }
}
