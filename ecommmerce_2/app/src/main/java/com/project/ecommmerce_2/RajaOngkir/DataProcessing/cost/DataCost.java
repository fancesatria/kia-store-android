package com.project.ecommmerce_2.RajaOngkir.DataProcessing.cost;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DataCost implements Serializable {
    @SerializedName("value")
    @Expose
    public Integer value;

    @SerializedName("etd")
    @Expose
    public String etd;

    @SerializedName("note")
    @Expose
    public String note;

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getEtd() {
        return etd;
    }

    public void setEtd(String etd) {
        this.etd = etd;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
