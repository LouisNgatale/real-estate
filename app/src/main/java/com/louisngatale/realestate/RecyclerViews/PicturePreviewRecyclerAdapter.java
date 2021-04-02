package com.louisngatale.realestate.RecyclerViews;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.louisngatale.realestate.R;

import java.util.ArrayList;

public class PicturePreviewRecyclerAdapter extends RecyclerView.Adapter<PicturePreviewRecyclerAdapter.ViewHolder> {

    ArrayList<Bitmap> images = new ArrayList<>();
    Context mContext;

    public PicturePreviewRecyclerAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_preview_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.preview.setImageBitmap(images.get(position));

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                images.remove(position);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView preview;
        ImageButton delete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            preview = itemView.findViewById(R.id.imagePreview);
            delete = itemView.findViewById(R.id.delete);
        }
    }

    public ArrayList<Bitmap> getImages() {
        return images;
    }

    public void setImages(ArrayList<Bitmap> images) {
        this.images = images;
    }

}
