package com.louisngatale.realestate.RecyclerViews;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.louisngatale.realestate.Models.House;
import com.louisngatale.realestate.R;
import com.louisngatale.realestate.Screens.ItemView.ItemViewActivity;

import java.util.ArrayList;

public class BrowseRecyclerViewAdapter extends RecyclerView.Adapter<BrowseRecyclerViewAdapter.ViewHolder> {

    ArrayList<House> house = new ArrayList<>();
    private Context mContext;

    public BrowseRecyclerViewAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.browse_houses_list_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
       /* holder.house_name.setText(house.get(position).getHouse_name());
        holder.house_description.setText(house.get(position).getHouse_description());
        holder.house_price.setText(house.get(position).getHouse_price());
        holder.house_image.setImageResource(house.get(position).getHouse_image());

        if (house.get(position).isSaved()) {
            holder.wishlist.setImageResource(R.drawable.tab_saved_orange);
        } else {
            holder.wishlist.setImageResource(R.drawable.tab_saved_white);
        }


//        TODO: Global click listener function
//        TODO: Add item to wish list
        holder.wishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (house.get(position).isSaved()) {
                    house.get(position).setSaved(false);
                    holder.wishlist.setImageResource(R.drawable.tab_saved_white);
                    Toast.makeText(mContext, "Item removed", Toast.LENGTH_SHORT).show();
                } else {
                    house.get(position).setSaved(true);
                    holder.wishlist.setImageResource(R.drawable.tab_saved_orange);
                    Toast.makeText(mContext, "Item added", Toast.LENGTH_SHORT).show();
                }
            }
        });

//                TODO: ItemView click listener
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itemView = new Intent(mContext, ItemViewActivity.class);
                itemView.putExtra("id", position);
                mContext.startActivity(itemView);
            }
        });*/

    }

    @Override
    public int getItemCount() {
        return house.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView house_image,wishlist;
        private TextView house_name, house_description, house_price;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            house_image = itemView.findViewById(R.id.browse_house_image);
            house_description = itemView.findViewById(R.id.browse_house_description);
            house_price = itemView.findViewById(R.id.browse_house_price);
            house_name =  itemView.findViewById(R.id.browse_house_name);
            wishlist = itemView.findViewById(R.id.add_wishlist);
        }
    }

    public void setHouse(ArrayList<House> house) {
        this.house = house;
    }
}
