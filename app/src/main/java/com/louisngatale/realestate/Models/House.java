package com.louisngatale.realestate.Models;

public class House {
    private String house_name;
    private String house_image;
    private Integer house_price;
    private String house_description;

    public House(String house_name, String house_image, int house_price, String house_description) {
        this.house_name = house_name;
        this.house_image = house_image;
        this.house_price = house_price;
        this.house_description = house_description;
    }

    public House(String house_name, int house_price, String house_description) {
        this.house_name = house_name;
        this.house_price = house_price;
        this.house_description = house_description;
    }

    public String getHouse_name() {
        return house_name;
    }

    public void setHouse_name(String house_name) {
        this.house_name = house_name;
    }

    public String getHouse_image() {
        return house_image;
    }

    public void setHouse_image(String house_image) {
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
}
