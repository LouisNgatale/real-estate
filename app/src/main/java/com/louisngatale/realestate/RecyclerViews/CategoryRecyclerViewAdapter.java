package com.louisngatale.realestate.RecyclerViews;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.louisngatale.realestate.Models.House;
import com.louisngatale.realestate.R;

import java.util.ArrayList;

public class CategoryRecyclerViewAdapter extends RecyclerView.Adapter<CategoryRecyclerViewAdapter.ViewHolder> {

    private static final String TAG = "Categories";
    private ArrayList<House> house = new ArrayList<>();
    Context mContext;

    public CategoryRecyclerViewAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView house_image,wishlist;
        private TextView house_name, house_description, house_price;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            house_image = itemView.findViewById(R.id.category_1_house_image);
            house_name = itemView.findViewById(R.id.category_1_house_name);
            house_description = itemView.findViewById(R.id.category_1_house_description);
            house_price = itemView.findViewById(R.id.category_1_house_price);
        }
    }

    @NonNull
    @Override
    public CategoryRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.categories_list_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.house_name.setText(house.get(position).getHouseType());
        holder.house_description.setText(house.get(position).getHouseDescription());
        holder.house_price.setText(house.get(position).getHousePrice());
        try{
            Glide.with(mContext)
                    .load(house.get(position).getHouseImages().get(0))
                    .into(holder.house_image);
        }catch (Exception e){
            Log.d(TAG, "onBindViewHolder: " + e);
        }

    }

    @Override
    public int getItemCount() {
        return house.size();
    }

    public void setHouse(ArrayList<House> house) {
        this.house = house;
    }
}
