package com.example.sharpshooter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity
{
    private FirebaseAuth mAuth;
    private EditText email_input;
    private EditText password_input;
    private TextView error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_login);

        // Authentication setup
        mAuth = FirebaseAuth.getInstance();

        email_input = (EditText) findViewById(R.id.email);
        password_input = (EditText) findViewById(R.id.password);
        error = (TextView) findViewById(R.id.tv_error);

        error.setVisibility(View.INVISIBLE);

        Button btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(view -> {
            // Get data from input fields
            String email = email_input.getText().toString();
            String password = password_input.getText().toString();

            // Check if both input fields are filled
            if (email.isEmpty() || password.isEmpty())
                return;

            // Check if email contains a '@'
            if (!email.contains("@"))
            {
                showError(R.string.login_failed_auth_email_invalid);
                return;
            }

            loginUser(email, password);
        });

        ImageButton btn_back = (ImageButton) findViewById(R.id.btn_back);
        btn_back.setOnClickListener(v -> finish());
    }

    // Handle the actual login in firebase
    private void loginUser(String email, String password)
    {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, task -> {
            if (task.isSuccessful()) {
                // Get User
                FirebaseUser user = mAuth.getCurrentUser();

                // Start main activity
                Intent main_intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(main_intent);

                // Close all previous intents
                finishAffinity();
            } else {
                // Reset input fields
                email_input.setText("");
                password_input.setText("");

                // Show error
                showError(R.string.login_failed_auth_wrong_password);
            }
        });
    }

    private void showError(int path)
    {
        error.setText(path);
        error.setVisibility(View.VISIBLE);
    }
}
