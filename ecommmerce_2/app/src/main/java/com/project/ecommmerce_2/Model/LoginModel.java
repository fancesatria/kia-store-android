package com.project.ecommmerce_2.Model;

public class LoginModel {
    public String email, password, name;
    public int id;

    public LoginModel(String email, String password) {
        this.email = email;
        this.password = password;
        this.name = name;
//        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
