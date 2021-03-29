package com.louisngatale.realestate.Models;


public class House {

    private String house_name;
    private int house_image;
    private Integer house_price;
    private String house_description;
    private String address;
    private boolean saved = false;

    public House(String house_name, int house_image, int house_price, String house_description,String address) {
        this.house_name = house_name;
        this.house_image = house_image;
        this.house_price = house_price;
        this.house_description = house_description;
        this.address = address;
    }

    public House(String house_name, int house_price, String house_description, String address) {
        this.house_name = house_name;
        this.house_price = house_price;
        this.house_description = house_description;
        this.address = address;
    }

    public House() {
    }

    public String getHouse_name() {
        return house_name;
    }

    public void setHouse_name(String house_name) {
        this.house_name = house_name;
    }

    public int getHouse_image() {
        return house_image;
    }

    public void setHouse_image(int house_image) {
        this.house_image = house_image;
    }

    public String getHouse_price() {
        return house_price.toString();
    }

    public void setHouse_price(int house_price) {
        this.house_price = house_price;
    }

    public String getHouse_description() {
        return house_description;
    }

    public void setHouse_description(String house_description) {
        this.house_description = house_description;
    }

    public boolean isSaved() {
        return saved;
    }

    public void setSaved(boolean saved) {
        this.saved = saved;
    }

    public void setHouse_price(Integer house_price) {
        this.house_price = house_price;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
