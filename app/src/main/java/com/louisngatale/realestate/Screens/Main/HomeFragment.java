package com.louisngatale.realestate.Screens.Main;

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
import com.louisngatale.realestate.Utils.HouseUtils;

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

//        TODO: Load browser list items from database

        recyclerView.setAdapter(categoryRecyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));


        browse.setAdapter(browseRecyclerViewAdapter);
        browse.setLayoutManager(new LinearLayoutManager(getActivity()){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });

        HouseUtils.getInstance();
        browseRecyclerViewAdapter.setHouse(HouseUtils.getBrowse_houses());
        HouseUtils.getInstance();
        categoryRecyclerViewAdapter.setHouse(HouseUtils.getHouses());

    }
}
