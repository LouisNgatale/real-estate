package com.louisngatale.realestate.Screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.louisngatale.realestate.R;

public class LoginActivity extends AppCompatActivity {
    Button register,login;
    EditText email,password;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        register = findViewById(R.id.register_link);
        login = findViewById(R.id.login);
        email = findViewById(R.id.email_value);
        password = findViewById(R.id.password_value);
        login.setOnClickListener(v -> {
            mAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                    .addOnCompleteListener(task -> finish());
        });

        register.setOnClickListener(v -> {
            Intent register = new Intent(LoginActivity.this,RegisterActivity.class);
            startActivity(register);
            finish();
        });
    }
}