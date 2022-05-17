package com.example.sharpshooter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sharpshooter.template.UserTemplate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RegisterActivity extends AppCompatActivity
{
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth;
    private EditText name_input;
    private EditText email_input;
    private EditText password_input;
    private EditText password_repeat_input;
    private TextView error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_register);

        // Authentication setup
        mAuth = FirebaseAuth.getInstance();

        name_input = (EditText) findViewById(R.id.name);
        email_input = (EditText) findViewById(R.id.email);
        password_input = (EditText) findViewById(R.id.password);
        password_repeat_input = (EditText) findViewById(R.id.password_repeat);
        error = (TextView) findViewById(R.id.tv_error);

        error.setVisibility(View.INVISIBLE);

        Button btn_register = (Button) findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get data from input fields
                String name = name_input.getText().toString();
                String email = email_input.getText().toString();
                String password = password_input.getText().toString();
                String password_repeat = password_repeat_input.getText().toString();

                // Check if all input fields are filled
                if (name.isEmpty() || email.isEmpty() || password.isEmpty() || password_repeat.isEmpty())
                    return;

                // Check if email is valid
                if (!email.contains("@"))
                {
                    showError(R.string.login_failed_auth_email_invalid);
                    return;
                }

                // Check if password is at least 8 characters long
                if (password.length() < 8)
                {
                    showError(R.string.register_failed_auth_password_length);
                    return;
                }

                // Check if password and password repeat matches
                if (!password.equals(password_repeat))
                {
                    showError(R.string.register_failed_auth_password_missmatch);
                    return;
                }

                registerUser(name, email, password);
            }
        });
    }

    private void registerUser(String name, String email, String password)
    {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Get User
                    FirebaseUser user = mAuth.getCurrentUser();


                    UserTemplate userTemplate = new UserTemplate(0,0,0,0,0,0,0,0);

                    db.collection("users").document(Objects.requireNonNull(mAuth.getUid()))
                            .set(userTemplate).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    System.out.println("Success");
                                }
                            });

                    /*
                    db.collection("users").document(Objects.requireNonNull(mAuth.getUid())).collection("games").document().set(userTemplate).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            System.out.println("65a4sd35a4s3d465as465d45");
                        }
                    });
                    */


                    // Start main activity
                    Intent main_intent = new Intent(RegisterActivity.this, MainActivity.class);

                    // TODO -> Initialise Firebase Structure

                    startActivity(main_intent);

                    // Close all previous intents
                    finishAffinity();
                } else {
                    // Reset fields and show error
                    email_input.setText("");

                    if (task.getException().getMessage().contains("email"))
                    {
                        showError(R.string.register_failed_auth_email_used);
                    }
                    else
                    {
                        showError(R.string.register_failed_auth_unknown_error);
                        name_input.setText("");
                        password_input.setText("");
                        password_repeat_input.setText("");
                    }
                }
            }
        });
    }

    private void showError(int path)
    {
        error.setText(path);
        error.setVisibility(View.VISIBLE);
    }
}
