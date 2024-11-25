package com.example.eventsusc;

import androidx.fragment.app.FragmentActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.eventsusc.databinding.ActivityMapsViewBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class MapsViewActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsViewBinding binding;
    private DatabaseReference eventsDatabaseReference;
    private Button addEventButton;

    // HashMap to store marker-event ID pairs
    private HashMap<Marker, String> markerEventMap = new HashMap<>();

    // Variables to hold user data
    private String userUID;
    private String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Retrieve user data passed from MainActivity
        userUID = getIntent().getStringExtra("USER_UID");
        userEmail = getIntent().getStringExtra("USER_EMAIL");

        // Optional: Display a message with the user email or UID
        if (userEmail != null) {
            Toast.makeText(this, "Welcome, " + userEmail, Toast.LENGTH_SHORT).show();
        } else if (userUID != null) {
            Toast.makeText(this, "Welcome, user ID: " + userUID, Toast.LENGTH_SHORT).show();
        }

        // Initialize Firebase database reference
        eventsDatabaseReference = FirebaseDatabase.getInstance("https://eventsusc-38901-default-rtdb.firebaseio.com/")
                .getReference("Events");

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Initialize and set up the "Add Event" button
        addEventButton = findViewById(R.id.add_event_button);
        addEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start AddEventActivity when the button is clicked
                Intent intent = new Intent(MapsViewActivity.this, AddEventActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Load events from Firebase and add markers
        loadEventsAndAddMarkers();

        // Set a listener for marker clicks
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                // Retrieve the event ID, name, and description associated with this marker
                String eventId = markerEventMap.get(marker);
                String eventName = marker.getTitle(); // assuming title is set as event name
                String eventDescription = marker.getSnippet(); // assuming snippet is set as description

                if (eventId != null) {
                    // Start CommentActivity and pass the event ID, name, description, and user ID
                    Intent intent = new Intent(MapsViewActivity.this, CommentActivity.class);
                    intent.putExtra("EVENT_ID", eventId);
                    intent.putExtra("EVENT_NAME", eventName);
                    intent.putExtra("EVENT_DESCRIPTION", eventDescription);
                    intent.putExtra("USER_UID", userUID); // Pass the user ID to CommentActivity
                    startActivity(intent);
                }
                return true; // Return true to indicate that we have handled the click
            }
        });
    }

    private void loadEventsAndAddMarkers() {
        eventsDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot eventSnapshot : dataSnapshot.getChildren()) {
                    // Assuming Event class has getLatitude(), getLongitude(), and getEventId() methods
                    Event event = eventSnapshot.getValue(Event.class);
                    if (event != null) {
                        LatLng eventLocation = new LatLng(event.getLatitude(), event.getLongitude());
                        Marker marker = mMap.addMarker(new MarkerOptions()
                                .position(eventLocation)
                                .title(event.getEventName())
                                .snippet(event.getEventDescription()));

                        // Store the marker and associated event ID in the HashMap
                        markerEventMap.put(marker, event.getEventId());
                    }
                }

                // Move the camera to the first event if available
                if (dataSnapshot.getChildrenCount() > 0) {
                    DataSnapshot firstEventSnapshot = dataSnapshot.getChildren().iterator().next();
                    Event firstEvent = firstEventSnapshot.getValue(Event.class);
                    if (firstEvent != null) {
                        LatLng firstLocation = new LatLng(firstEvent.getLatitude(), firstEvent.getLongitude());
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(firstLocation, 10));
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(MapsViewActivity.this, "Failed to load events", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public GoogleMap getMap() {
        return mMap;
    }

}
