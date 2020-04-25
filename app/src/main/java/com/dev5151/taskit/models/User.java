package com.dev5151.taskit.models;

public class User {

    public String name, email, phone;

    public User(String name, String email, String phone) {

        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public User() {

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}
