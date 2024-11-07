package com.example.eventsusc;

import android.os.Bundle;
import android.view.Gravity;
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
    private DatabaseReference commentsDatabaseReference;
    private String eventId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        // Get the event ID passed from MapsViewActivity
        eventId = getIntent().getStringExtra("EVENT_ID");

        // Initialize the LinearLayout container
        commentsContainer = findViewById(R.id.comments_container);

        // Set up Firebase reference to the comments of the specific event
        commentsDatabaseReference = FirebaseDatabase.getInstance("https://eventsusc-38901-default-rtdb.firebaseio.com/")
                .getReference("Events").child(eventId).child("Comments");

        // Load comments from Firebase
        loadComments();
    }

    private void loadComments() {
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
}
