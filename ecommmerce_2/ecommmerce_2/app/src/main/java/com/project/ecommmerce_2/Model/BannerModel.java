package com.project.ecommmerce_2.Model;

import com.google.gson.annotations.SerializedName;

public class BannerModel {
    @SerializedName("id")
    private int id;

    @SerializedName("banner_1")
    private String banner_1;

    @SerializedName("banner_2")
    private String banner_2;

    @SerializedName("banner_3")
    private String banner_3;

    @SerializedName("banner_4")
    private String banner_4;

    @SerializedName("banner_5")
    private String banner_5;

    @SerializedName("created_at")
    private String created_at;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBanner_1() {
        return banner_1;
    }

    public void setBanner_1(String banner_1) {
        this.banner_1 = banner_1;
    }

    public String getBanner_2() {
        return banner_2;
    }

    public void setBanner_2(String banner_2) {
        this.banner_2 = banner_2;
    }

    public String getBanner_3() {
        return banner_3;
    }

    public void setBanner_3(String banner_3) {
        this.banner_3 = banner_3;
    }

    public String getBanner_4() {
        return banner_4;
    }

    public void setBanner_4(String banner_4) {
        this.banner_4 = banner_4;
    }

    public String getBanner_5() {
        return banner_5;
    }

    public void setBanner_5(String banner_5) {
        this.banner_5 = banner_5;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
