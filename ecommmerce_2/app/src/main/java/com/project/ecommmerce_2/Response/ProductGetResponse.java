package com.project.ecommmerce_2.Response;

import com.project.ecommmerce_2.Model.ProductModel;

import java.util.List;

public class ProductGetResponse {
    private List<ProductModel> data;

    public ProductGetResponse(List<ProductModel> data) {
        this.data = data;
    }

    public List<ProductModel> getData() {
        return data;
    }

    public void setData(List<ProductModel> data) {
        this.data = data;
    }
}
