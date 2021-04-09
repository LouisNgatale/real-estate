package com.louisngatale.realestate.Screens.Main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.Query;
import com.louisngatale.realestate.Models.House;
import com.louisngatale.realestate.R;
import com.louisngatale.realestate.RecyclerViews.BrowseRecyclerViewAdapter;
import com.louisngatale.realestate.RecyclerViews.WishListRecyclerViewAdapter;
import com.louisngatale.realestate.Screens.ItemView.ItemViewActivity;
import com.louisngatale.realestate.Services.Firestore;
import com.louisngatale.realestate.Utils.HouseUtils;

import java.util.HashSet;
import java.util.Set;

import static android.content.Context.MODE_PRIVATE;

public class WishListFragment extends Fragment {

    WishListRecyclerViewAdapter adapter;
    RecyclerView recyclerView;
    TextView emptyPlaceholder,item_counter;
    String id;
    SharedPreferences sharedPreferences;
    Firestore firestore;
    private String TAG = "Items";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_wishlist, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.wishList_recycler_view);
        emptyPlaceholder = view.findViewById(R.id.emptyPlaceholder);
        item_counter = view.findViewById(R.id.wish_list_item_count);

        sharedPreferences = getContext().getSharedPreferences("WISHLIST",MODE_PRIVATE);

        firestore = new Firestore();

        @SuppressLint("CommitPrefEdits")
        Set<String> set = sharedPreferences.getStringSet("Wishlist", null);

        // check if set is not empty
        if (set.size() > 0){
            emptyPlaceholder.setVisibility(View.GONE);
            //        Create query
            Query query =
                    firestore.getHouses(set);

            Log.d(TAG, "onViewCreated: " + set);

            //        Configure the adapter options
            FirestoreRecyclerOptions<House> options =
                    new FirestoreRecyclerOptions.Builder<House>()
                            .setQuery(query,House.class)
                            .build();

            String count = String.valueOf(set.size());
            item_counter.setText(count);

            adapter = new WishListRecyclerViewAdapter(options,getContext());
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
            recyclerView.setAdapter(adapter);

            adapter.setOnItemClickListener((documentSnapshot, position) -> {
                String id = documentSnapshot.getId();
                Intent viewItem = new Intent(getContext(), ItemViewActivity.class);
                viewItem.putExtra("Id", id);
                startActivity(viewItem);
            });
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}