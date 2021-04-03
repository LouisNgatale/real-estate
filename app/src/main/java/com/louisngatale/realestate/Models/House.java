package com.louisngatale.realestate.Models;

import java.util.ArrayList;

public class House {

    private String houseType;
    private ArrayList<String> houseImages;
    private Integer housePrice;
    private String houseDescription;
    private String address;
    private String agentName;
    private String agentAuthority;
    private boolean saved = false;

    public House(String houseType, ArrayList<String> houseImages, Integer housePrice, String houseDescription, String address, String agentName, String agentAuthority) {
        this.houseType = houseType;
        this.houseImages = houseImages;
        this.housePrice = housePrice;
        this.houseDescription = houseDescription;
        this.address = address;
        this.agentName = agentName;
        this.agentAuthority = agentAuthority;
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

    public Integer getHousePrice() {
        return housePrice;
    }

    public void setHousePrice(Integer housePrice) {
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

}
