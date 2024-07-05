package com.project.ecommmerce_2.RajaOngkir.DataProcessing.cost;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class RajaOngkirCost implements Serializable {
    @SerializedName("query")
    @Expose
    public DataQuery query;
    @SerializedName("status")
    @Expose
    public DataStatus status;
    @SerializedName("origin_details")
    @Expose
    public DataShipping originDetails;
    @SerializedName("destination_details")
    @Expose
    public DataDestination destinationDetails;
    @SerializedName("results")
    @Expose
    public List<DataResult> results = null;

    public DataQuery getQuery() {
        return query;
    }

    public void setQuery(DataQuery query) {
        this.query = query;
    }

    public DataStatus getStatus() {
        return status;
    }

    public void setStatus(DataStatus status) {
        this.status = status;
    }

    public DataShipping getOriginDetails() {
        return originDetails;
    }

    public void setOriginDetails(DataShipping originDetails) {
        this.originDetails = originDetails;
    }

    public DataDestination getDestinationDetails() {
        return destinationDetails;
    }

    public void setDestinationDetails(DataDestination destinationDetails) {
        this.destinationDetails = destinationDetails;
    }

    public List<DataResult> getResults() {
        return results;
    }

    public void setResults(List<DataResult> results) {
        this.results = results;
    }
}
