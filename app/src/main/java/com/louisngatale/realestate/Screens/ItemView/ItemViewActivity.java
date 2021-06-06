package com.louisngatale.realestate.Screens.ItemView;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.louisngatale.realestate.Models.House;
import com.louisngatale.realestate.R;
import com.louisngatale.realestate.Services.Firestore;
import com.louisngatale.realestate.Utils.HouseUtils;

import java.util.ArrayList;
import java.util.List;

public class ItemViewActivity extends AppCompatActivity {
    ImageView house_image,itemView_call,itemView_message;
    TextView house_type,house_price, house_address, house_bed_count,
        house_bath_count, house_size, house_description, agent_name,
        agent_position;
    ImageSlider slider;
    DocumentReference docRef;
    ShimmerFrameLayout shimmer_itemView_house_type,
            shimmer_itemView_price,shimmer_itemView_address,
            shimmer_itemView_bed_count,shimmer_itemView_bath_count,
            shimmer_itemView_size_count,shimmer_itemView_description;
    Firestore firestore;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    List<SlideModel> images;
    House incomingHouse;

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
                    incomingHouse = documentSnapshot.toObject(House.class);
                    if (null != incomingHouse){
                        setLayout(incomingHouse);
                    }
                });
            }
        }else {
            finish();
        }
    }

    @SuppressLint("SetTextI18n")
    private void setLayout(House incomingHouse) {
        initiateViews();
        stopShimmers();
        
        //  House_image.setImageResource(incomingHouse.getHouse_image());
        house_price.setText(incomingHouse.getHousePrice() + " Tsh");
        house_description.setText(incomingHouse.getHouseDescription());
        house_type.setText(incomingHouse.getHouseType());
        house_address.setText(incomingHouse.getAddress());
        house_bed_count.setText(incomingHouse.getBedCount());
        house_bath_count.setText(incomingHouse.getBathCount());
        house_size.setText(incomingHouse.getHouseSize() + " sqr meters");

        images = new ArrayList<>();

        incomingHouse
                .getHouseImages()
                .forEach(image -> {
                    images.add(new SlideModel(image,incomingHouse.getHouseDescription()));
                });

        slider.setImageList(images,true);
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
        slider = findViewById(R.id.image_slider);

        itemView_message = findViewById(R.id.itemView_message);
        itemView_call = findViewById(R.id.itemView_call);

        // Open phone manager app to call the owner of the app
        itemView_call.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + incomingHouse.getPhone_number()));// Initiates the Intent
            startActivity(intent);
        });

        // Open message app with user's number to send message
        itemView_message.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // This ensures only SMS apps respond
            intent.setData(Uri.parse("smsto:" + incomingHouse.getPhone_number()));
            intent.putExtra("sms_body", "Hello! How are you doing");
            startActivity(intent);
        });

        //  Set visibility
        //  house_image.setVisibility(View.VISIBLE);
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