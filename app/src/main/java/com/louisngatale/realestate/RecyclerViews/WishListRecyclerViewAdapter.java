package com.louisngatale.realestate.RecyclerViews;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.louisngatale.realestate.Models.House;
import com.louisngatale.realestate.R;
import com.louisngatale.realestate.Screens.ItemView.ItemViewActivity;

import java.util.ArrayList;

public class WishListRecyclerViewAdapter extends FirestoreRecyclerAdapter<House, WishListRecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "Items";
    private Context mContext;
    
    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     * @param context
     */
    public WishListRecyclerViewAdapter(@NonNull FirestoreRecyclerOptions<House> options, Context context) {
        super(options);
        this.mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wishlist_item,parent,false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull House model) {
        holder.house_name.setText(model.getHouseType());
        holder.house_price.setText(model.getHousePrice() +" Tsh");
        holder.house_description.setText(model.getHouseDescription());
        holder.house_address.setText(model.getAddress());
        Log.d(TAG, "onBindViewHolder: " + model.getHouseType());

        try{
            Glide.with(mContext)
                    .load(model.getHouseImages().get(0))
                    .into(holder.house_image);
        }catch (Exception e){
            Log.d(TAG, "onBindViewHolder: " + e);
        }
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView house_image;
        TextView house_name,
                 house_description,
                 house_address,
                 house_price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            house_image = itemView.findViewById(R.id.wishList_image);
            house_name =   itemView.findViewById(R.id.wishList_house_name);
            house_description = itemView.findViewById(R.id.wishList_description);
            house_address = itemView.findViewById(R.id.wishList_location);
            house_price = itemView.findViewById(R.id.wishList_price);
        }
    }
}
