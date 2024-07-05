package com.project.ecommmerce_2.Response;

import com.google.gson.annotations.SerializedName;
import com.project.ecommmerce_2.Model.SourceAddressModel;

public class SourceAdressResponse {
    @SerializedName("success")
    private boolean success;

    @SerializedName("data")
    private SourceAddressModel data;

    // Getters and Setters
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public SourceAddressModel getData() {
        return data;
    }

    public void setData(SourceAddressModel data) {
        this.data = data;
    }
}
