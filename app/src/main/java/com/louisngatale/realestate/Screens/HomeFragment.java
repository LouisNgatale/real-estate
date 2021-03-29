package com.louisngatale.realestate.Screens;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.louisngatale.realestate.Models.House;
import com.louisngatale.realestate.R;
import com.louisngatale.realestate.RecyclerViews.BrowseRecyclerViewAdapter;
import com.louisngatale.realestate.RecyclerViews.CategoryRecyclerViewAdapter;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    CategoryRecyclerViewAdapter categoryRecyclerViewAdapter;
    BrowseRecyclerViewAdapter browseRecyclerViewAdapter;

    RecyclerView recyclerView, browse;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.categories_recycler_view);
        browse = view.findViewById(R.id.browse_houses_recycler_view);

        categoryRecyclerViewAdapter = new CategoryRecyclerViewAdapter();
        browseRecyclerViewAdapter = new BrowseRecyclerViewAdapter(getContext());

//        TODO: Load category list items from database
        ArrayList<House> houses = new ArrayList<>();
        houses.add(new House("First House",R.drawable.house_1,20000,"This is my first house"));
        houses.add(new House("Second House",R.drawable.house_6,20000,"This is my second house"));
        houses.add(new House("Third House",R.drawable.house_8,20000,"This is my third house"));
        houses.add(new House("Fourth House",R.drawable.house_2,20000,"This is my fourth house"));

//        TODO: Load browser list items from database
        ArrayList<House> browse_houses = new ArrayList<>();
        browse_houses.add(new House("First House",R.drawable.house_1,20000,"This is my first house"));
        browse_houses.add(new House("Second House",R.drawable.house_2,234422,"This is my second house"));
        browse_houses.add(new House("Third House",R.drawable.house_3,234214,"This is my third house"));
        browse_houses.add(new House("Fourth House",R.drawable.house_5,43214214,"This is my fourth house"));
        browse_houses.add(new House("Fourth House",R.drawable.house_6,43214214,"This is my fourth house"));
        browse_houses.add(new House("Fourth House",R.drawable.house_8,43214214,"This is my fourth house"));
        browse_houses.add(new House("Fourth House",R.drawable.house_9,43214214,"This is my fourth house"));

        recyclerView.setAdapter(categoryRecyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));


        browse.setAdapter(browseRecyclerViewAdapter);
        browse.setLayoutManager(new LinearLayoutManager(getActivity()){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });

        browseRecyclerViewAdapter.setHouse(browse_houses);
        categoryRecyclerViewAdapter.setHouse(houses);

    }
}
