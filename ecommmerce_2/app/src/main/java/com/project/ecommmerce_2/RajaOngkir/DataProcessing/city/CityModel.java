package com.project.ecommmerce_2.RajaOngkir.DataProcessing.city;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CityModel implements Serializable {
    @SerializedName("city_id")
    @Expose
    public String cityId;

    @SerializedName("city_name")
    @Expose
    public String cityName;

    @SerializedName("province_id")
    @Expose
    public String provinceId;

    @SerializedName("province")
    @Expose
    public String province;

    @SerializedName("type")
    @Expose
    public String type;

    @SerializedName("postal_code")
    @Expose
    public String postalCode;

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
}
