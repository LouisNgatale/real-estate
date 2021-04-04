package com.louisngatale.realestate.RecyclerViews;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.louisngatale.realestate.Models.House;
import com.louisngatale.realestate.R;
import com.louisngatale.realestate.Screens.Main.HomeFragment;

public class BrowseRecyclerViewAdapter extends FirestoreRecyclerAdapter<House, BrowseRecyclerViewAdapter.ViewHolder> {

    private final String TAG="Home";

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public BrowseRecyclerViewAdapter(@NonNull FirestoreRecyclerOptions<House> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull House model) {
        holder.house_description.setText(model.getHouseDescription());
        holder.house_name.setText(model.getHouseType());
        holder.house_price.setText(model.getHousePrice());
        Log.d(TAG, "onBindViewHolder: " + model.getHouseType());
        Log.d(TAG, "onBindViewHolder: " + model.getHousePrice());
        Log.d(TAG, "onBindViewHolder: " + model.getHouseImages());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.browse_houses_list_item,parent,false);
        return new ViewHolder(view);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView house_image;
        private final ImageView wishlist;
        private final TextView house_name;
        private final TextView house_description;
        private final TextView house_price;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            house_image = itemView.findViewById(R.id.browse_house_image);
            house_description = itemView.findViewById(R.id.browse_house_description);
            house_price = itemView.findViewById(R.id.browse_house_price);
            house_name =  itemView.findViewById(R.id.browse_house_name);
            wishlist = itemView.findViewById(R.id.add_wishlist);
        }
    }
}
