package com.example.sharpshooter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class WelcomeActivity extends AppCompatActivity
{
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        // Check if already logged in
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if( user != null )
        {
            // Start main activity
            Intent main_intent = new Intent(WelcomeActivity.this, MainActivity.class);
            startActivity(main_intent);

            // Close all previous intents
            finishAffinity();
        }

        // Get buttons
        Button btn_login = (Button) findViewById(R.id.btn_login);
        Button btn_register = (Button) findViewById(R.id.btn_register);

        // Button click -> Open new Intent
        btn_login.setOnClickListener(view -> {
            Intent intent_login = new Intent(WelcomeActivity.this, LoginActivity.class);
            startActivity(intent_login);
        });

        btn_register.setOnClickListener(view -> {
            Intent intent_login = new Intent(WelcomeActivity.this, RegisterActivity.class);
            startActivity(intent_login);
        });
    }
}
