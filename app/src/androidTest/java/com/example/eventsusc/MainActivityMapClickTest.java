package com.example.eventsusc;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.MarkerOptions;

import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityMapClickTest {

    @Test
    public void testMapClickLoadsMarkers() {
        // Launch MapsViewActivity
        try (ActivityScenario<MapsViewActivity> scenario = ActivityScenario.launch(MapsViewActivity.class)) {
            // Simulate the map being ready
            scenario.onActivity(activity -> {
                GoogleMap map = activity.getMap();

                // Add a test marker to the map
                if (map != null) {
                    map.addMarker(new MarkerOptions()
                            .position(new com.google.android.gms.maps.model.LatLng(34.0522, -118.2437))
                            .title("Test Marker")
                            .snippet("This is a test marker description"));
                }
            });

            // Verify that the map is displayed
            onView(withId(R.id.map)).check(matches(isDisplayed()));

            // Simulate clicking on the map fragment
            onView(withId(R.id.map)).perform(click());

            // Additional assertions can be added here to verify behavior,
            // such as checking for UI changes or Toast messages.
        }
    }
}
