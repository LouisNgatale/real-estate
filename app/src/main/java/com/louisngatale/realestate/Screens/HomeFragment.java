package com.louisngatale.realestate.Screens;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.louisngatale.realestate.Models.House;
import com.louisngatale.realestate.R;
import com.louisngatale.realestate.RecyclerViews.CategoryRecyclerViewAdapter;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    CategoryRecyclerViewAdapter categoryRecyclerViewAdapter;

    RecyclerView recyclerView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.categories_recycler_view);
        categoryRecyclerViewAdapter = new CategoryRecyclerViewAdapter();

        ArrayList<House> houses = new ArrayList<>();
        houses.add(new House("First House",20000,"This is my first house"));
        houses.add(new House("Second House",20000,"This is my second house"));
        houses.add(new House("Third House",20000,"This is my third house"));
        houses.add(new House("Fourth House",20000,"This is my fourth house"));

        recyclerView.setAdapter(categoryRecyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));

        categoryRecyclerViewAdapter.setHouse(houses);

    }


}
