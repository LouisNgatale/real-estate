package com.louisngatale.realestate.Screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.louisngatale.realestate.R;

public class LoginActivity extends AppCompatActivity {
    Button register,login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        register = findViewById(R.id.register_link);
        login = findViewById(R.id.login);

        register.setOnClickListener(v -> {
            Intent register = new Intent(LoginActivity.this,RegisterActivity.class);
            startActivity(register);
            finish();
        });
    }
}