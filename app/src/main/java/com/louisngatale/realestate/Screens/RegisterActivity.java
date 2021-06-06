package com.louisngatale.realestate.Screens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.louisngatale.realestate.R;

import java.util.HashMap;
import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {
    Button register,login;
    TextView fullname,email,phone_number,password;
    FirebaseAuth mAuth;
    FirebaseFirestore mFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();

        fullname = findViewById(R.id.fullname_value);
        email = findViewById(R.id.email_value);
        phone_number = findViewById(R.id.phone_number_value);
        password = findViewById(R.id.password);

        register = findViewById(R.id.register);
        login = findViewById(R.id.login_link);


        login.setOnClickListener(v -> {
            Intent login = new Intent(RegisterActivity.this,LoginActivity.class);
            startActivity(login);
            finish();
        });

        register.setOnClickListener(v -> {
            mAuth.createUserWithEmailAndPassword(email.getText().toString(),password.getText().toString())
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        String uid = Objects.requireNonNull(task.getResult().getUser()).getUid();

                        HashMap<String,String> user = new HashMap<>();
                        user.put("fullname",fullname.getText().toString());
                        user.put("email",email.getText().toString());
                        user.put("phone_number",phone_number.getText().toString());

                        mFirestore.collection("users")
                                .document(uid)
                                .set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                finish();
                            }
                        });
                    }
                });
        });
    }
}