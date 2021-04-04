package com.louisngatale.realestate.Screens.Main;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.louisngatale.realestate.Models.House;
import com.louisngatale.realestate.R;
import com.louisngatale.realestate.RecyclerViews.BrowseRecyclerViewAdapter;
import com.louisngatale.realestate.RecyclerViews.CategoryRecyclerViewAdapter;
import com.louisngatale.realestate.Screens.ItemView.ItemViewActivity;
import com.louisngatale.realestate.Services.Firestore;

public class HomeFragment extends Fragment {
    CategoryRecyclerViewAdapter categoryRecyclerViewAdapter;
    BrowseRecyclerViewAdapter adapter;
    FirebaseFirestore db;
    Firestore firestore;
    RecyclerView recyclerView, browse;
    private String TAG ="Home";

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
/*
        categoryRecyclerViewAdapter = new CategoryRecyclerViewAdapter();
        browseRecyclerViewAdapter = new BrowseRecyclerViewAdapter(getContext());*/

        db = FirebaseFirestore.getInstance();
        firestore = new Firestore();

//        Create query
        Query query =
                firestore.getAllHouses();


//        Configure the adapter options
        FirestoreRecyclerOptions<House> options =
                new FirestoreRecyclerOptions.Builder<House>()
                .setQuery(query,House.class)
                .build();

        adapter = new BrowseRecyclerViewAdapter(options,getContext());

//        TODO: Load category list items from database

//        TODO: Load browser list items from database
        browse.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
        browse.setAdapter(adapter);

        adapter.setOnItemClickListener(new BrowseRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                String id = documentSnapshot.getId();
                Intent viewItem = new Intent(getContext(), ItemViewActivity.class);
                viewItem.putExtra("Id", id);
                startActivity(viewItem);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    private static class HouseHolder extends RecyclerView.ViewHolder {
        private   ImageView house_image,wishlist;
        private   TextView house_name, house_description, house_price;
        public HouseHolder(View view) {
            super(view);
            house_image = itemView.findViewById(R.id.browse_house_image);
            house_description = itemView.findViewById(R.id.browse_house_description);
            house_price = itemView.findViewById(R.id.browse_house_price);
            house_name =  itemView.findViewById(R.id.browse_house_name);
            wishlist = itemView.findViewById(R.id.add_wishlist);
        }
    }
}