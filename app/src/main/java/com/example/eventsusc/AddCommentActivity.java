package com.example.eventsusc;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddCommentActivity extends AppCompatActivity {

    private EditText commentTitleInput, commentTextInput;
    private Button addCommentButton, cancelButton;

    private DatabaseReference commentsDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_comment); // Set to xml file for "add comment"

        // Initialize Firebase reference for comments
        commentsDatabaseReference = FirebaseDatabase.getInstance("https://eventsusc-38901-default-rtdb.firebaseio.com/")
                .getReference("Comments");

        // Initialize UI elements
        commentTitleInput = findViewById(R.id.comment_title_input);
        commentTextInput = findViewById(R.id.comment_text_input);
        addCommentButton = findViewById(R.id.add_comment_button);
        cancelButton = findViewById(R.id.cancel_button);

        // "Add Comment" button click listener
        addCommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addCommentToFirebase();
            }
        });

        // "Cancel" button click listener
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Clear input fields
                commentTitleInput.setText("");
                commentTextInput.setText("");
                Toast.makeText(AddCommentActivity.this, "Comment creation canceled", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addCommentToFirebase() {
        // Get input values
        String commentTitle = commentTitleInput.getText().toString().trim();
        String commentText = commentTextInput.getText().toString().trim();

        // Validate inputs (cannot leave empty)
        if (TextUtils.isEmpty(commentTitle) || TextUtils.isEmpty(commentText)) {
            Toast.makeText(this, "Please fill in both fields.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Generate unique comment ID and create comment object
        String commentId = commentsDatabaseReference.push().getKey();
        Comment comment = new Comment(commentId, commentTitle, commentText);

        // Store comment in Firebase
        if (commentId != null) {
            commentsDatabaseReference.child(commentId).setValue(comment)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(AddCommentActivity.this, "Comment added successfully!", Toast.LENGTH_SHORT).show();
                            // Clear input fields after submission
                            commentTitleInput.setText("");
                            commentTextInput.setText("");
                        } else {
                            Toast.makeText(AddCommentActivity.this, "Failed to add comment: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}
