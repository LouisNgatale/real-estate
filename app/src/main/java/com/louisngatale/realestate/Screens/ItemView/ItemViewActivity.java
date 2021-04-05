package com.louisngatale.realestate.Screens.ItemView;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.louisngatale.realestate.Models.House;
import com.louisngatale.realestate.R;
import com.louisngatale.realestate.Services.Firestore;
import com.louisngatale.realestate.Utils.HouseUtils;

public class ItemViewActivity extends AppCompatActivity {

    ImageView house_image;
    TextView house_type,house_price, house_address, house_bed_count,
        house_bath_count, house_size, house_description, agent_name,
        agent_position;
    DocumentReference docRef;
    ShimmerFrameLayout shimmer_itemView_house_type,
            shimmer_itemView_price,shimmer_itemView_address,
            shimmer_itemView_bed_count,shimmer_itemView_bath_count,
            shimmer_itemView_size_count,shimmer_itemView_description;
    Firestore firestore;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private String TAG ="Items";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_view);
        initiateShimmers();

        firestore = new Firestore();

        setStatusBar();

        retrieveHouse();

    }

    private void initiateShimmers() {
        shimmer_itemView_address = findViewById(R.id.shimmer_itemView_address);
        shimmer_itemView_bath_count = findViewById(R.id.shimmer_itemView_bath_count);
        shimmer_itemView_bed_count = findViewById(R.id.shimmer_itemView_bed_count);
        shimmer_itemView_description = findViewById(R.id.shimmer_itemView_description);
        shimmer_itemView_house_type = findViewById(R.id.shimmer_itemView_house_type);
        shimmer_itemView_price = findViewById(R.id.shimmer_itemView_price);
        shimmer_itemView_size_count = findViewById(R.id.shimmer_itemView_size_count);
    }

    private void retrieveHouse() {
        Intent intent = getIntent();
        if (null != intent){
            String house_index = intent.getStringExtra("Id");
            if (house_index != null){
                docRef = db.collection("houses").document(house_index);

                docRef.get().addOnSuccessListener(documentSnapshot -> {
                    House incomingHouse = documentSnapshot.toObject(House.class);
                    if (null != incomingHouse){
                        setLayout(incomingHouse);
                    }
                });
            }
        }else {
            finish();
        }
    }

    private void setLayout(House incomingHouse) {
        initiateViews();
        stopShimmers();
        
//        house_image.setImageResource(incomingHouse.getHouse_image());
        house_price.setText(incomingHouse.getHousePrice());
        house_description.setText(incomingHouse.getHouseDescription());
        house_type.setText(incomingHouse.getHouseType());
        house_address.setText(incomingHouse.getAddress());
        house_bed_count.setText(incomingHouse.getBedCount());
        house_bath_count.setText(incomingHouse.getBathCount());
        house_size.setText(incomingHouse.getHouseSize());
    }

    private void stopShimmers() {
        shimmer_itemView_address.setVisibility(View.GONE);
        shimmer_itemView_bath_count.setVisibility(View.GONE);
        shimmer_itemView_bed_count.setVisibility(View.GONE);
        shimmer_itemView_description.setVisibility(View.GONE);
        shimmer_itemView_house_type.setVisibility(View.GONE);
        shimmer_itemView_price.setVisibility(View.GONE);
        shimmer_itemView_size_count.setVisibility(View.GONE);
    }

    private void initiateViews() {
        house_image = findViewById(R.id.itemView_image);
        house_price = findViewById(R.id.itemView_price);
        house_description = findViewById(R.id.itemView_description);
        house_type = findViewById(R.id.itemView_house_type);
        house_address = findViewById(R.id.itemView_address);
        house_bed_count = findViewById(R.id.itemView_bed_count);
        house_bath_count = findViewById(R.id.itemView_bath_count);
        house_size = findViewById(R.id.itemView_size_count);
        
//        Set visibility
        house_image.setVisibility(View.VISIBLE);
        house_price.setVisibility(View.VISIBLE);
        house_type.setVisibility(View.VISIBLE);
        house_bath_count.setVisibility(View.VISIBLE);
        house_bed_count.setVisibility(View.VISIBLE);
        house_address.setVisibility(View.VISIBLE);
        house_description.setVisibility(View.VISIBLE);
        house_size.setVisibility(View.VISIBLE);
    }

    @SuppressLint("ObsoleteSdkInt")
    private void setStatusBar() {
        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true);
        }
        if (Build.VERSION.SDK_INT >= 19) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    public void setWindowFlag(Activity activity, final int bits, boolean on) {
        Window win = this.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }
}