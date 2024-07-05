package com.project.ecommmerce_2.RajaOngkir.DataProcessing.city;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class RajaOngkirCity implements Serializable{
    @SerializedName("query")
    @Expose
    public List<Object> query = null;

    @SerializedName("status")
    @Expose
    public DataStatus status;

    @SerializedName("results")
    @Expose
    public List<CityModel> result = null;

    public List<Object> getQuery() {
        return query;
    }

    public void setQuery(List<Object> query) {
        this.query = query;
    }

    public DataStatus getStatus() {
        return status;
    }

    public void setStatus(DataStatus status) {
        this.status = status;
    }

    public List<CityModel> getResult() {
        return result;
    }

    public void setResult(List<CityModel> result) {
        this.result = result;
    }
}
