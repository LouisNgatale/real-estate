package com.louisngatale.realestate.Models;

import java.util.ArrayList;

public class House {

    private String houseType;
    private ArrayList<String> houseImages;
    private String housePrice;
    private String houseDescription;
    private String address;
    private String agentName;
    private String bathCount;
    private String bedCount;
    private String houseSize;
    private String agentAuthority;
    private String phone_number;
    private boolean saved = false;

    public House() {
    }

    public House(String houseType, ArrayList<String> houseImages, String housePrice, String houseDescription, String address, String agentName, String bathCount, String bedCount, String houseSize, String agentAuthority) {
        this.houseType = houseType;
        this.houseImages = houseImages;
        this.housePrice = housePrice;
        this.houseDescription = houseDescription;
        this.address = address;
        this.agentName = agentName;
        this.bathCount = bathCount;
        this.bedCount = bedCount;
        this.houseSize = houseSize;
        this.agentAuthority = agentAuthority;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getHouseType() {
        return houseType;
    }

    public void setHouseType(String houseType) {
        this.houseType = houseType;
    }

    public ArrayList<String> getHouseImages() {
        return houseImages;
    }

    public void setHouseImages(ArrayList<String> houseImages) {
        this.houseImages = houseImages;
    }

    public String getHousePrice() {
        return housePrice;
    }

    public void setHousePrice(String housePrice) {
        this.housePrice = housePrice;
    }

    public String getHouseDescription() {
        return houseDescription;
    }

    public void setHouseDescription(String houseDescription) {
        this.houseDescription = houseDescription;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getAgentAuthority() {
        return agentAuthority;
    }

    public void setAgentAuthority(String agentAuthority) {
        this.agentAuthority = agentAuthority;
    }

    public boolean isSaved() {
        return saved;
    }

    public void setSaved(boolean saved) {
        this.saved = saved;
    }

    public String getBathCount() {
        return bathCount;
    }

    public void setBathCount(String bathCount) {
        this.bathCount = bathCount;
    }

    public String getBedCount() {
        return bedCount;
    }

    public void setBedCount(String bedCount) {
        this.bedCount = bedCount;
    }

    public String getHouseSize() {
        return houseSize;
    }

    public void setHouseSize(String houseSize) {
        this.houseSize = houseSize;
    }
}
