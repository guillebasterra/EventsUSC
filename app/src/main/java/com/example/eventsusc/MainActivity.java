package com.example.eventsusc;

import android.os.Bundle;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    EditText emailInput;
    EditText passwordInput;
    Button loginButton;
    private Button newAccountButton;
    Button forgotPasswordButton; // New button

    private FirebaseAuth mAuth;

    private Intent nextStartedActivity;
    private String latestToastMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Initialize UI elements
        emailInput = findViewById(R.id.email_input);
        passwordInput = findViewById(R.id.password_input);
        loginButton = findViewById(R.id.login_button);
        newAccountButton = findViewById(R.id.new_account_button);
        forgotPasswordButton = findViewById(R.id.forgot_password_button); // Initialize button

        // Set up login button click listener
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailInput.getText().toString().trim();
                String password = passwordInput.getText().toString().trim();

                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                    showToast("Please enter both email and password.");
                    return;
                }

                // Sign in the user with Firebase Authentication
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(MainActivity.this, task -> {
                            if (task.isSuccessful()) {
                                showToast("Login successful");

                                // Proceed to the next activity (e.g., MapsViewActivity)
                                Intent intent = new Intent(MainActivity.this, MapsViewActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(MainActivity.this, "Authentication failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        // Set up create account button click listener
        newAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Code to create a new account
            }
        });

        // Set up forgot password button click listener
        forgotPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailInput.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(MainActivity.this, "Please enter your email to reset password.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Send password reset email
                mAuth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(MainActivity.this, "Password reset email sent.", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(MainActivity.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }

    // Override startActivity to capture the started activity
    @Override
    public void startActivity(Intent intent) {
        nextStartedActivity = intent;
        super.startActivity(intent);
    }

    // Add a getter for the captured activity
    public Intent getNextStartedActivity() {
        return nextStartedActivity;
    }

    // Add a helper to show toast messages and store the latest message
    public void showToast(String message) {
        latestToastMessage = message;
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    // Add a getter for the latest toast message
    public String getLatestToastMessage() {
        return latestToastMessage;
    }
}
