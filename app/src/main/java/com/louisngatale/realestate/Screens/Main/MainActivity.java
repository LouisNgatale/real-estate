I package com.louisngatale.realestate.Screens.Main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.louisngatale.realestate.R;
import com.louisngatale.realestate.Screens.LoginActivity;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager()
            .beginTransaction()
            .replace(
                R.id.fragment_container,
                new HomeFragment())
            .commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @SuppressLint("NonConstantResourceId")
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = new HomeFragment();

            switch (item.getItemId()){
                case R.id.nav_home:
                    selectedFragment = new HomeFragment();
                    break;
                case R.id.nav_search:
                    selectedFragment = new SearchFragment();
                    break;
                case R.id.nav_saved:
                    selectedFragment = new WishListFragment();
                    break;
                case R.id.nav_profile:
                    selectedFragment = new ProfileFragment();
                    break;
                case R.id.nav_dashboard:
                    if (mAuth.getCurrentUser() == null) {
                        Intent login = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(login);
                    }else {
                        selectedFragment = new DashboardFragment();
                    }
//                        selectedFragment = new HomeFragment();
                    break;
            }

//            assert selectedFragment != null;
            getSupportFragmentManager().beginTransaction().replace(
                    R.id.fragment_container,
                    selectedFragment
                    ).commit();
            return true;
        }
    };
}