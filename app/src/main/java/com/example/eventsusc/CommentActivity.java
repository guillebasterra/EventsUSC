package com.example.eventsusc;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CommentActivity extends AppCompatActivity {

    private LinearLayout commentsContainer;
    private DatabaseReference eventsDatabaseReference;
    private String eventId;
    private double eventLatitude;
    private double eventLongitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        // Get the event ID passed from MapsViewActivity
        eventId = getIntent().getStringExtra("EVENT_ID");

        // Initialize the LinearLayout container
        commentsContainer = findViewById(R.id.comments_container);

        // Initialize Firebase reference to the specific event
        eventsDatabaseReference = FirebaseDatabase.getInstance("https://eventsusc-38901-default-rtdb.firebaseio.com/")
                .getReference("Events").child(eventId);

        // Load comments from Firebase
        loadComments();

        // Initialize and set up the Back button
        Button backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Finish CommentActivity and return to the previous activity
            }
        });

        // Initialize and set up the Get Directions button
        Button getDirectionsButton = findViewById(R.id.get_directions_button);
        getDirectionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve the event location and open Google Maps
                retrieveEventLocation();
            }
        });
    }

    private void loadComments() {
        DatabaseReference commentsDatabaseReference = eventsDatabaseReference.child("Comments");
        commentsDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                commentsContainer.removeAllViews(); // Clear existing views

                for (DataSnapshot commentSnapshot : dataSnapshot.getChildren()) {
                    Comment comment = commentSnapshot.getValue(Comment.class);
                    if (comment != null) {
                        addCommentToView(comment);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(CommentActivity.this, "Failed to load comments", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addCommentToView(Comment comment) {
        // Create a new TextView for each comment
        TextView commentTextView = new TextView(this);
        commentTextView.setText(String.format("%s: %s", comment.getUserName(), comment.getText()));
        commentTextView.setPadding(8, 8, 8, 8);
        commentTextView.setTextSize(16);
        commentTextView.setGravity(Gravity.START);

        // Optional: Add timestamp as a separate TextView
        TextView timestampTextView = new TextView(this);
        timestampTextView.setText(comment.getFormattedTimestamp());
        timestampTextView.setPadding(8, 4, 8, 12);
        timestampTextView.setTextSize(12);
        timestampTextView.setGravity(Gravity.END);

        // Add the TextViews to the container
        commentsContainer.addView(commentTextView);
        commentsContainer.addView(timestampTextView);
    }

    private void retrieveEventLocation() {
        eventsDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Event event = dataSnapshot.getValue(Event.class);
                if (event != null) {
                    eventLatitude = event.getLatitude();
                    eventLongitude = event.getLongitude();
                    openGoogleMapsDirections();
                } else {
                    Toast.makeText(CommentActivity.this, "Event location not found.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(CommentActivity.this, "Failed to load event location", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void openGoogleMapsDirections() {
        // Create a URI for the directions to the event's location
        String uri = "google.navigation:q=" + eventLatitude + "," + eventLongitude;
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        intent.setPackage("com.google.android.apps.maps");

        // Check if Google Maps is installed
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Toast.makeText(this, "Google Maps not installed", Toast.LENGTH_SHORT).show();
        }
    }
}
