package com.example.mobiledev;

public class LinkModel {

    private String address, name, user;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public LinkModel(String address, String name, String user) {
        super();
        this.address = address;
        this.name = name;
        this.user = user;
    }

    public LinkModel() {
    }

}
