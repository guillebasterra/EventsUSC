package com.example.eventsusc;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CommentActivity extends AppCompatActivity {

    private LinearLayout commentsContainer;
    private EditText newCommentInput;
    private Button addCommentButton;
    private DatabaseReference commentsDatabaseReference;
    private DatabaseReference eventsDatabaseReference;
    private String eventId;
    private String eventName;
    private String eventDescription;
    private double eventLatitude;
    private double eventLongitude;

    private int upvotes;
    private int downvotes;

    private TextView voteCountText;
    private Button upvoteButton;
    private Button downvoteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        // Get the event details passed from MapsViewActivity
        eventId = getIntent().getStringExtra("EVENT_ID");
        eventName = getIntent().getStringExtra("EVENT_NAME");
        eventDescription = getIntent().getStringExtra("EVENT_DESCRIPTION");

        // Set the event name and description in the TextViews
        TextView eventNameTextView = findViewById(R.id.event_name_text);
        TextView eventDescriptionTextView = findViewById(R.id.event_description_text);
        eventNameTextView.setText("Event: " + eventName);
        eventDescriptionTextView.setText("Description: " + eventDescription);

        // Initialize Delete Event button
        Button deleteEventButton = findViewById(R.id.delete_event_button);
        deleteEventButton.setOnClickListener(v -> confirmAndDeleteEvent());

        // Initialize the comments container and comment input
        commentsContainer = findViewById(R.id.comments_container);
        newCommentInput = findViewById(R.id.new_comment_input);
        addCommentButton = findViewById(R.id.add_comment_button);

        // Initialize the upvote and downvote components
        voteCountText = findViewById(R.id.vote_count_text);
        upvoteButton = findViewById(R.id.upvote_button);
        downvoteButton = findViewById(R.id.downvote_button);

        // Set up Firebase references
        commentsDatabaseReference = FirebaseDatabase.getInstance("https://eventsusc-38901-default-rtdb.firebaseio.com/")
                .getReference("Events").child(eventId).child("Comments");
        eventsDatabaseReference = FirebaseDatabase.getInstance("https://eventsusc-38901-default-rtdb.firebaseio.com/")
                .getReference("Events").child(eventId);

        // Load event data and comments
        loadEventData();
        loadComments();

        // Add comment functionality
        addCommentButton.setOnClickListener(v -> addCommentToFirebase());

        // Voting functionality
        upvoteButton.setOnClickListener(v -> {
            upvotes++;
            updateVoteCount();
            eventsDatabaseReference.child("upvotes").setValue(upvotes);
        });

        downvoteButton.setOnClickListener(v -> {
            downvotes++;
            updateVoteCount();
            eventsDatabaseReference.child("downvotes").setValue(downvotes);
        });

        // Back and Get Directions buttons
        findViewById(R.id.back_button).setOnClickListener(v -> finish());
        findViewById(R.id.get_directions_button).setOnClickListener(v -> retrieveEventLocationAndOpenMaps());
    }

    private void confirmAndDeleteEvent() {
        new AlertDialog.Builder(this)
                .setTitle("Delete Event")
                .setMessage("Are you sure you want to delete this event?")
                .setPositiveButton("Yes", (dialog, which) -> deleteEventFromFirebase())
                .setNegativeButton("No", null)
                .show();
    }

    private void deleteEventFromFirebase() {
        eventsDatabaseReference.removeValue()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(CommentActivity.this, "Event deleted successfully.", Toast.LENGTH_SHORT).show();
                        finish(); // Go back to the previous screen
                    } else {
                        Toast.makeText(CommentActivity.this, "Failed to delete event: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void loadEventData() {
        eventsDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Event event = dataSnapshot.getValue(Event.class);
                if (event != null) {
                    upvotes = event.getUpvotes();
                    downvotes = event.getDownvotes();
                    updateVoteCount();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(CommentActivity.this, "Failed to load event data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateVoteCount() {
        voteCountText.setText(upvotes + " upvotes | " + downvotes + " downvotes");
    }

    private void loadComments() {
        commentsDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                commentsContainer.removeAllViews();
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
        TextView commentTextView = new TextView(this);
        commentTextView.setText(String.format("%s: %s", comment.getUserName(), comment.getText()));
        commentTextView.setPadding(8, 8, 8, 8);
        commentTextView.setTextSize(16);

        TextView timestampTextView = new TextView(this);
        timestampTextView.setText(comment.getFormattedTimestamp());
        timestampTextView.setPadding(8, 4, 8, 12);
        timestampTextView.setTextSize(12);

        commentsContainer.addView(commentTextView);
        commentsContainer.addView(timestampTextView);
    }

    private void addCommentToFirebase() {
        String commentText = newCommentInput.getText().toString().trim();
        if (commentText.isEmpty()) {
            Toast.makeText(this, "Please enter a comment", Toast.LENGTH_SHORT).show();
            return;
        }

        String userName = "Anonymous"; // Replace with actual user name if available
        long timestamp = System.currentTimeMillis();
        Comment newComment = new Comment(userName, commentText, timestamp);

        commentsDatabaseReference.push().setValue(newComment)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(this, "Comment added", Toast.LENGTH_SHORT).show();
                        newCommentInput.setText("");
                    } else {
                        Toast.makeText(this, "Failed to add comment", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void retrieveEventLocationAndOpenMaps() {
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
        String uri = "google.navigation:q=" + eventLatitude + "," + eventLongitude;
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        intent.setPackage("com.google.android.apps.maps");

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Toast.makeText(this, "Google Maps not installed", Toast.LENGTH_SHORT).show();
        }
    }
}
