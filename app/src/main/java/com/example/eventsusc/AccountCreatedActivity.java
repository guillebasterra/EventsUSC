package com.example.eventsusc;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AccountCreatedActivity extends AppCompatActivity {

    DatabaseReference databaseReference;
    TextView accountCreatedMessage;
    TextView userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_created);

        // Initialize UI elements
        accountCreatedMessage = findViewById(R.id.account_created_message);
        userEmail = findViewById(R.id.user_email);

        // Get the UID from the intent
        String userUid = getIntent().getStringExtra("USER_UID");

        // Initialize Firebase Database reference
        databaseReference = FirebaseDatabase.getInstance("https://eventsusc-38901-default-rtdb.firebaseio.com/")
                .getReference("Users").child(userUid);

        // Retrieve the user data from Firebase
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // Assuming User object has "email" and "displayName" fields
                    User user = snapshot.getValue(User.class);
                    if (user != null) {
                        accountCreatedMessage.setText("Account created!");
                        userEmail.setText("Email: " + user.getEmail());
                    }
                } else {
                    Toast.makeText(AccountCreatedActivity.this, "User data not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AccountCreatedActivity.this, "Failed to load user data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }


        });
    }
}
