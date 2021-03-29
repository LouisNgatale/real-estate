package com.louisngatale.realestate.Utils;

import com.louisngatale.realestate.Models.House;
import com.louisngatale.realestate.R;

import java.util.ArrayList;

public class HouseUtils{

    private static HouseUtils instance;

    private static ArrayList<House> houses;
    private static ArrayList<House> browse_houses;


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
        browse_houses.add(new House("First House",R.drawable.house_1,20000,"This is my first house"));
        browse_houses.add(new House("Second House",R.drawable.house_2,234422,"This is my second house"));
        browse_houses.add(new House("Third House",R.drawable.house_3,234214,"This is my third house"));
        browse_houses.add(new House("Fourth House",R.drawable.house_5,43214214,"This is my fourth house"));
        browse_houses.add(new House("Fourth House",R.drawable.house_6,43214214,"This is my fourth house"));
        browse_houses.add(new House("Fourth House",R.drawable.house_8,43214214,"This is my fourth house"));
        browse_houses.add(new House("Fourth House",R.drawable.house_9,43214214,"This is my fourth house"));
    }

    private void initHouse() {
//        TODO: Initiate data call
        houses.add(new House("First House", R.drawable.house_1,20000,"This is my first house"));
        houses.add(new House("Second House",R.drawable.house_6,20000,"This is my second house"));
        houses.add(new House("Third House",R.drawable.house_8,20000,"This is my third house"));
        houses.add(new House("Fourth House",R.drawable.house_2,20000,"This is my fourth house"));
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
}
