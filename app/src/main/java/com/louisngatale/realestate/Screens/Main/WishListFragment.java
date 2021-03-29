package com.louisngatale.realestate.Screens.Main;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.louisngatale.realestate.R;
import com.louisngatale.realestate.RecyclerViews.WishListRecyclerViewAdapter;
import com.louisngatale.realestate.Utils.HouseUtils;

public class WishListFragment extends Fragment {

    WishListRecyclerViewAdapter adapter;
    RecyclerView recyclerView;
    TextView emptyPlaceholder,item_counter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_wishlist, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new WishListRecyclerViewAdapter(getContext());
        recyclerView = view.findViewById(R.id.wishList_recycler_view);
        emptyPlaceholder = view.findViewById(R.id.emptyPlaceholder);
        item_counter = view.findViewById(R.id.wish_list_item_count);

        HouseUtils.getInstance();
        adapter.setHouses(HouseUtils.getBrowse_houses());

        HouseUtils.getInstance();

        if (HouseUtils.getSaved().size() > 0){
            emptyPlaceholder.setVisibility(View.GONE);

            String item_count = String.valueOf(HouseUtils.getSaved().size());
            item_counter.setText(item_count);

            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            HouseUtils.getInstance();
            adapter.setHouses(HouseUtils.getSaved());
        }


    }
}