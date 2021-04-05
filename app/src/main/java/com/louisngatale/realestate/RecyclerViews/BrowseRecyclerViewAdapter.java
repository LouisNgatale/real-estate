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
import com.google.firebase.firestore.DocumentSnapshot;
import com.louisngatale.realestate.Models.House;
import com.louisngatale.realestate.R;
import com.louisngatale.realestate.Screens.ItemView.ItemViewActivity;

public class BrowseRecyclerViewAdapter extends FirestoreRecyclerAdapter<House, BrowseRecyclerViewAdapter.ViewHolder> {

    private OnItemClickListener listener;

    private final String TAG="Home";
    Context mContext;

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     * @param context
     */
    public BrowseRecyclerViewAdapter(@NonNull FirestoreRecyclerOptions<House> options, Context context) {
        super(options);
        this.mContext = context;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull House model) {
        holder.house_description.setText(model.getHouseDescription());
        holder.house_name.setText(model.getHouseType());
        holder.house_price.setText(model.getHousePrice() +" Tsh");
        Log.d(TAG, "onBindViewHolder: " + position);

        try{
            Glide.with(mContext)
                    .load(model.getHouseImages().get(0))
                    .into(holder.house_image);
        }catch (Exception e){
            Log.d(TAG, "onBindViewHolder: " + e);
        }

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.browse_houses_list_item,parent,false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
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

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int positon = getAdapterPosition();
                    if (positon != RecyclerView.NO_POSITION && listener != null){
                        listener.onItemClick(getSnapshots().getSnapshot(positon), positon);
                    }
                }
            });
        }
    }

    public interface OnItemClickListener{
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }
}
