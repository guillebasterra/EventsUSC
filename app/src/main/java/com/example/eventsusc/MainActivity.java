package com.example.eventsusc;

import android.os.Bundle;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Logger;

public class MainActivity extends AppCompatActivity {

    private EditText emailInput;
    private EditText passwordInput;
    private Button loginButton;
    private Button newAccountButton;

    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseDatabase.getInstance().setLogLevel(Logger.Level.DEBUG);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Initialize Firebase Database with the specific URL
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://eventsusc-38901-default-rtdb.firebaseio.com/");
        databaseReference = database.getReference("Users");

        // Initialize UI elements
        emailInput = findViewById(R.id.email_input);
        passwordInput = findViewById(R.id.password_input);
        loginButton = findViewById(R.id.login_button);
        newAccountButton = findViewById(R.id.new_account_button);

        // Set up login button click listener
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailInput.getText().toString().trim();
                String password = passwordInput.getText().toString().trim();

                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                    Toast.makeText(MainActivity.this, "Please enter both email and password.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Sign in the user with Firebase Authentication
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(MainActivity.this, task -> {
                            if (task.isSuccessful()) {
                                // Sign-in success
                                FirebaseUser user = mAuth.getCurrentUser();
                                Toast.makeText(MainActivity.this, "Login successful. Welcome, " + user.getEmail(), Toast.LENGTH_SHORT).show();
                                // Navigate to the main screen or home activity
                            } else {
                                // If sign in fails, display a message to the user
                                Toast.makeText(MainActivity.this, "Authentication failed. " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        newAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailInput.getText().toString().trim();
                String password = passwordInput.getText().toString().trim();

                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                    Toast.makeText(MainActivity.this, "Please enter both email and password.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(MainActivity.this, "Password must be at least 6 characters.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Log attempt to create user
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(MainActivity.this, task -> {
                            if (task.isSuccessful()) {
                                FirebaseUser firebaseUser = mAuth.getCurrentUser();
                                if (firebaseUser != null) {
                                    Toast.makeText(MainActivity.this, "User created with UID: " + firebaseUser.getUid(), Toast.LENGTH_SHORT).show();

                                    User user = new User(firebaseUser.getUid(), email, "Default Name");

                                    databaseReference.child(firebaseUser.getUid()).setValue(user)
                                            .addOnCompleteListener(saveTask -> {
                                                if (saveTask.isSuccessful()) {
                                                    Toast.makeText(MainActivity.this, "User saved in database", Toast.LENGTH_SHORT).show();

                                                    // Start AccountCreatedActivity and pass the user's UID
                                                    Intent intent = new Intent(MainActivity.this, AccountCreatedActivity.class);
                                                    intent.putExtra("USER_UID", firebaseUser.getUid());
                                                    startActivity(intent);

                                                    // Optional: Finish MainActivity so that the user can't go back to it
                                                    finish();
                                                } else {
                                                    Toast.makeText(MainActivity.this, "Failed to save user data: " + saveTask.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                }
                            } else {
                                Toast.makeText(MainActivity.this, "Account creation failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }
}
