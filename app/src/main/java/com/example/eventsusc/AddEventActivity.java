package com.example.eventsusc;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddEventActivity extends AppCompatActivity {

    private EditText eventNameInput, eventDescriptionInput, eventDateTimeInput, eventLocationInput, eventLatitudeInput, eventLongitudeInput;
    private Button addEventButton, cancelButton;

    private DatabaseReference eventsDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_event); // Set to XML file for "add event"

        // Initialize Firebase reference for events
        eventsDatabaseReference = FirebaseDatabase.getInstance("https://eventsusc-38901-default-rtdb.firebaseio.com/")
                .getReference("Events");

        // Initialize UI elements
        eventNameInput = findViewById(R.id.event_name_input);
        eventDescriptionInput = findViewById(R.id.event_description_input);
        eventDateTimeInput = findViewById(R.id.event_date_time_input);
        eventLocationInput = findViewById(R.id.event_location_input);
        eventLatitudeInput = findViewById(R.id.event_latitude_input);
        eventLongitudeInput = findViewById(R.id.event_longitude_input);
        addEventButton = findViewById(R.id.add_event_button);
        cancelButton = findViewById(R.id.cancel_button);

        // "Add Event" button click listener
        addEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addEventToFirebase();
            }
        });

        // "Cancel" button click listener
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Clear input fields
                eventNameInput.setText("");
                eventDescriptionInput.setText("");
                eventDateTimeInput.setText("");
                eventLocationInput.setText("");
                eventLatitudeInput.setText("");
                eventLongitudeInput.setText("");

                // Show a toast message
                Toast.makeText(AddEventActivity.this, "Event creation canceled", Toast.LENGTH_SHORT).show();

                // Navigate back to MapsViewActivity
                Intent intent = new Intent(AddEventActivity.this, MapsViewActivity.class);
                startActivity(intent);

                // Optional: Finish the AddEventActivity to prevent going back to it
                finish();
            }
        });
    }

    private void addEventToFirebase() {
        // Get input values
        String eventName = eventNameInput.getText().toString().trim();
        String eventDescription = eventDescriptionInput.getText().toString().trim();
        String eventDateTime = eventDateTimeInput.getText().toString().trim();
        String eventLocation = eventLocationInput.getText().toString().trim();
        String latitudeStr = eventLatitudeInput.getText().toString().trim();
        String longitudeStr = eventLongitudeInput.getText().toString().trim();

        // Validate inputs (cannot leave empty)
        if (TextUtils.isEmpty(eventName) || TextUtils.isEmpty(eventDescription) ||
                TextUtils.isEmpty(eventDateTime) || TextUtils.isEmpty(eventLocation) ||
                TextUtils.isEmpty(latitudeStr) || TextUtils.isEmpty(longitudeStr)) {
            Toast.makeText(this, "Please fill in all fields.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Parse latitude and longitude
        double latitude;
        double longitude;
        try {
            latitude = Double.parseDouble(latitudeStr);
            longitude = Double.parseDouble(longitudeStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid latitude or longitude format", Toast.LENGTH_SHORT).show();
            return;
        }

        // Generate unique event ID and create event object
        String eventId = eventsDatabaseReference.push().getKey();
        Event event = new Event(eventId, eventName, eventDescription, eventDateTime, eventLocation, latitude, longitude);

        // Store event in Firebase
        if (eventId != null) {
            eventsDatabaseReference.child(eventId).setValue(event)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(AddEventActivity.this, "Event added successfully!", Toast.LENGTH_SHORT).show();
                            // Clear input fields after submission
                            eventNameInput.setText("");
                            eventDescriptionInput.setText("");
                            eventDateTimeInput.setText("");
                            eventLocationInput.setText("");
                            eventLatitudeInput.setText("");
                            eventLongitudeInput.setText("");
                        } else {
                            Toast.makeText(AddEventActivity.this, "Failed to add event: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}
