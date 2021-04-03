package com.louisngatale.realestate.Utils;

import com.louisngatale.realestate.Models.House;
import com.louisngatale.realestate.R;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class HouseUtils{

    private static HouseUtils instance;

    private static ArrayList<House> houses;

    private static ArrayList<House> browse_houses;

    private static ArrayList<House> saved;

    public HouseUtils() {
        if (null == houses){
            houses = new ArrayList<>();
            initHouse();
        }
        if (null == browse_houses){
            browse_houses = new ArrayList<>();
            initBrowseHouse();
        }
    }

    private void initBrowseHouse() {
    }

    private void initHouse() {
    }

    public static HouseUtils getInstance() {
        if (null != instance){
            return instance;
        }else {
            instance = new HouseUtils();
            return instance;
        }
    }

    public static House getHouseById(int house_index){
        return browse_houses.get(house_index);
    }

    public static ArrayList<House> getHouses() {
        return houses;
    }

    public static void setHouses(ArrayList<House> houses) {
        HouseUtils.houses = houses;
    }

    public static ArrayList<House> getBrowse_houses() {
        return browse_houses;
    }

    public static void setBrowse_houses(ArrayList<House> browse_houses) {
        HouseUtils.browse_houses = browse_houses;
    }

    public static ArrayList<House> getSaved() {
        return browse_houses
                .stream()
                .filter(house -> house.isSaved())
                .collect(Collectors.toCollection(ArrayList::new));
    }



}
