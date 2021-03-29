package com.louisngatale.realestate.RecyclerViews;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.louisngatale.realestate.Models.House;
import com.louisngatale.realestate.R;
import com.louisngatale.realestate.Screens.ItemView.ItemViewActivity;

import java.util.ArrayList;

public class WishListRecyclerViewAdapter extends RecyclerView.Adapter<WishListRecyclerViewAdapter.ViewHolder> {

    ArrayList<House> houses = new ArrayList<>();
    private Context mContext;

    public WishListRecyclerViewAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wishlist_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.house_image.setImageResource(houses.get(position).getHouse_image());
        holder.house_name.setText(houses.get(position).getHouse_name());
        holder.house_price.setText(houses.get(position).getHouse_price());
        holder.house_description.setText(houses.get(position).getHouse_description());
        holder.house_address.setText(houses.get(position).getAddress());

        //                TODO: ItemView click listener
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itemView = new Intent(mContext, ItemViewActivity.class);
                itemView.putExtra("id", position);
                mContext.startActivity(itemView);
            }
        });
    }

    @Override
    public int getItemCount() {
        return houses.size();
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

    public void setHouses(ArrayList<House> houses) {
        this.houses = houses;
    }
}
