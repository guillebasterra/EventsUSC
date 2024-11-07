package com.example.eventsusc;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class CommentActivity extends AppCompatActivity {

    private LinearLayout commentsContainer;
    private EditText newCommentInput;
    private Button addCommentButton;
    private String eventName;
    private String eventDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        // Get event details passed from MapsViewActivity
        eventName = getIntent().getStringExtra("EVENT_NAME");
        eventDescription = getIntent().getStringExtra("EVENT_DESCRIPTION");

        // Initialize UI elements
        commentsContainer = findViewById(R.id.comments_container);
        newCommentInput = findViewById(R.id.new_comment_input);
        addCommentButton = findViewById(R.id.add_comment_button);

        // Display event name and description at the top
        addEventDetailsToView();

        // Add comment button functionality
        addCommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCommentLocally();
            }
        });
    }

    private void addEventDetailsToView() {
        // Create a TextView for the event name
        TextView eventNameTextView = new TextView(this);
        eventNameTextView.setText("Event: " + eventName);
        eventNameTextView.setPadding(8, 8, 8, 4);
        eventNameTextView.setTextSize(20);
        eventNameTextView.setGravity(Gravity.CENTER);

        // Create a TextView for the event description
        TextView eventDescriptionTextView = new TextView(this);
        eventDescriptionTextView.setText("Description: " + eventDescription);
        eventDescriptionTextView.setPadding(8, 4, 8, 12);
        eventDescriptionTextView.setTextSize(16);
        eventDescriptionTextView.setGravity(Gravity.CENTER);

        // Add these TextViews to the top of the container
        commentsContainer.addView(eventNameTextView);
        commentsContainer.addView(eventDescriptionTextView);
    }

    private void addCommentLocally() {
        // Get the comment text from the EditText
        String commentText = newCommentInput.getText().toString().trim();

        if (commentText.isEmpty()) {
            Toast.makeText(this, "Please enter a comment", Toast.LENGTH_SHORT).show();
            return; // Exit early if no comment text
        }

        // Create a new TextView for the comment
        TextView commentTextView = new TextView(this);
        commentTextView.setText("Anonymous: " + commentText);
        commentTextView.setPadding(8, 8, 8, 8);
        commentTextView.setTextSize(16);
        commentTextView.setGravity(Gravity.START);

        // Optional: Create a TextView for the timestamp
        TextView timestampTextView = new TextView(this);
        String formattedTimestamp = android.text.format.DateFormat.format("MM/dd/yyyy hh:mm:ss", System.currentTimeMillis()).toString();
        timestampTextView.setText(formattedTimestamp);
        timestampTextView.setPadding(8, 4, 8, 12);
        timestampTextView.setTextSize(12);
        timestampTextView.setGravity(Gravity.END);

        // Add the TextViews to the container
        commentsContainer.addView(commentTextView);
        commentsContainer.addView(timestampTextView);

        // Clear the input field
        newCommentInput.setText("");
    }
}
