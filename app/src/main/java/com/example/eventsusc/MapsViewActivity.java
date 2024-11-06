package com.example.eventsusc;

import androidx.fragment.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.eventsusc.databinding.ActivityMapsViewBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MapsViewActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsViewBinding binding;
    private DatabaseReference eventsDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize Firebase database reference
        eventsDatabaseReference = FirebaseDatabase.getInstance("https://eventsusc-38901-default-rtdb.firebaseio.com/")
                .getReference("Events");

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Load events from Firebase and add markers
        loadEventsAndAddMarkers();
    }

    private void loadEventsAndAddMarkers() {
        eventsDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot eventSnapshot : dataSnapshot.getChildren()) {
                    // Assuming Event class has getLatitude() and getLongitude() methods
                    Event event = eventSnapshot.getValue(Event.class);
                    if (event != null) {
                        LatLng eventLocation = new LatLng(event.getLatitude(), event.getLongitude());
                        mMap.addMarker(new MarkerOptions()
                                .position(eventLocation)
                                .title(event.getEventName())
                                .snippet(event.getEventDescription()));
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
}
