package com.louisngatale.realestate.Screens.ItemView;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.louisngatale.realestate.Models.House;
import com.louisngatale.realestate.R;
import com.louisngatale.realestate.Utils.HouseUtils;

public class ItemViewActivity extends AppCompatActivity {

    ImageView house_image;
    TextView house_name,house_price, house_address, house_bed_count,
        house_bath_count, house_area, house_description, agent_name,
        agent_position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_view);

        setStatusBar();

        retrieveHouse();

    }

    private void retrieveHouse() {
        Intent intent = getIntent();

        if (null != intent){
            int house_index = intent.getIntExtra("id",-1);
            if (house_index != -1){
                HouseUtils.getInstance();
                House incomingHouse = HouseUtils.getHouseById(house_index);
                if (null != incomingHouse){
                    setLayout(incomingHouse);
                }
            }
        }else {
            finish();
        }
    }

    private void setLayout(House incomingHouse) {
        initiateViews();
       /* house_image.setImageResource(incomingHouse.getHouse_image());
        house_price.setText(incomingHouse.getHouse_price());
        house_description.setText(incomingHouse.getHouse_description());*/
    }

    private void initiateViews() {
        house_image = findViewById(R.id.itemView_image);
        house_price = findViewById(R.id.itemView_price);
        house_description = findViewById(R.id.itemView_description);
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