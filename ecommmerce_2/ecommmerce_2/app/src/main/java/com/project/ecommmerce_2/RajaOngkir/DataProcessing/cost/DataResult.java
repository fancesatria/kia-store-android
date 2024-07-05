package com.project.ecommmerce_2.RajaOngkir.DataProcessing.cost;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class DataResult implements Serializable {
    @SerializedName("code")
    @Expose
    public String code;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("costs")
    @Expose
    public List<DataCourier> costs = null;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<DataCourier> getCosts() {
        return costs;
    }

    public void setCosts(List<DataCourier> costs) {
        this.costs = costs;
    }
}
