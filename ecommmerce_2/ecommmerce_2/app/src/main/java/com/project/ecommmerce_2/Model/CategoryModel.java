package com.project.ecommmerce_2.Model;

public class CategoryModel {
    private int id;
    private String nama;
    private String created_at;
    private String updated_at;

    public CategoryModel(int id, String nama, String created_at, String updated_at) {
        this.id = id;
        this.nama = nama;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public CategoryModel() {
        this.id = id;
        this.nama = nama;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}
