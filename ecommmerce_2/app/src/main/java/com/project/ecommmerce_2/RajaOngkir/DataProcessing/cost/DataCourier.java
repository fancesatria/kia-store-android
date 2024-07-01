package com.project.ecommmerce_2.RajaOngkir.DataProcessing.cost;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class DataCourier implements Serializable {
    @SerializedName("service")
    @Expose
    public String service;
    @SerializedName("description")
    @Expose
    public String description;
    @SerializedName("cost")
    @Expose
    public List<DataCost> cost = null;

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<DataCost> getCost() {
        return cost;
    }

    public void setCost(List<DataCost> cost) {
        this.cost = cost;
    }
}
