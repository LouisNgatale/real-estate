package com.louisngatale.realestate.Screens.Main;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.louisngatale.realestate.Models.House;
import com.louisngatale.realestate.R;
import com.louisngatale.realestate.RecyclerViews.CategoryRecyclerViewAdapter;

public class HomeFragment extends Fragment {
    CategoryRecyclerViewAdapter categoryRecyclerViewAdapter;

    FirestoreRecyclerAdapter adapter;
    FirebaseFirestore db;

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

//        Create query
        Query query = db
                .collection("houses");

//        Configure the adapter options
        FirestoreRecyclerOptions<House> options =
                new FirestoreRecyclerOptions.Builder<House>()
                .setQuery(query,House.class)
                .build();

//        Configure the adapter object
         adapter =
                new FirestoreRecyclerAdapter<House, HouseHolder>(options) {
                    @NonNull
                    @Override
                    public HouseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.browse_houses_list_item,parent,false);
                        return new HouseHolder(view);
                    }

                    @Override
                    protected void onBindViewHolder(@NonNull HouseHolder holder, int position, @NonNull House model) {
                        holder.house_description.setText(model.getHouseDescription());
                        holder.house_name.setText(model.getHouseType());
                        holder.house_price.setText(model.getHousePrice());
                        Log.d(TAG, "onBindViewHolder: " + model.getHouseType());
                        Log.d(TAG, "onBindViewHolder: " + model.getHousePrice());
                        Log.d(TAG, "onBindViewHolder: " + model.getHouseImages());
                    }

                };


//        TODO: Load category list items from database

//        TODO: Load browser list items from database
        browse.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
        browse.setAdapter(adapter);

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
