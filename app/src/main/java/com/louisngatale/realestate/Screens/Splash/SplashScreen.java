package com.louisngatale.realestate.Screens.Splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.louisngatale.realestate.R;
import com.louisngatale.realestate.Screens.Main.MainActivity;

public class SplashScreen extends AppCompatActivity {

    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

//        TODO: Splash screen display only once at start up
        handler=new Handler();
        handler.postDelayed(() -> {
            Intent intent=new Intent(SplashScreen.this, MainActivity.class);
            startActivity(intent);
            finish();
        },1000);
    }
}