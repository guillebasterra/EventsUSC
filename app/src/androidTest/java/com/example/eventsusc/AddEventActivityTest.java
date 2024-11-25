package com.example.eventsusc;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class AddEventActivityTest {

    @Before
    public void setUp() {
        Intents.init(); // Initialize Intents for navigation testing
    }

    @After
    public void tearDown() {
        Intents.release(); // Release Intents after tests
    }

    @Test
    public void testAddEventWithValidData() {
        try (ActivityScenario<AddEventActivity> scenario = ActivityScenario.launch(AddEventActivity.class)) {
            // Enter valid event details
            onView(withId(R.id.event_name_input))
                    .perform(typeText("Test Event"), closeSoftKeyboard());
            onView(withId(R.id.event_description_input))
                    .perform(typeText("This is a test event."), closeSoftKeyboard());
            onView(withId(R.id.event_date_time_input))
                    .perform(typeText("12/12/2024, 10:00 AM"), closeSoftKeyboard());
            onView(withId(R.id.event_location_input))
                    .perform(typeText("Test Location"), closeSoftKeyboard());
            onView(withId(R.id.event_latitude_input))
                    .perform(typeText("34.0522"), closeSoftKeyboard());
            onView(withId(R.id.event_longitude_input))
                    .perform(typeText("-118.2437"), closeSoftKeyboard());

            // Click the Add Event button
            onView(withId(R.id.add_event_button)).perform(click());

            // Verify that the event was successfully added
            Espresso.onView(withText("Event added successfully!"))
                    .inRoot(new ToastMatcher())
                    .check(matches(isDisplayed()));
        }
    }

    @Test
    public void testAddEventWithMissingFields() {
        try (ActivityScenario<AddEventActivity> scenario = ActivityScenario.launch(AddEventActivity.class)) {
            // Leave event details blank and click Add Event button
            onView(withId(R.id.event_name_input))
                    .perform(typeText(""), closeSoftKeyboard());
            onView(withId(R.id.event_description_input))
                    .perform(typeText(""), closeSoftKeyboard());
            onView(withId(R.id.event_date_time_input))
                    .perform(typeText(""), closeSoftKeyboard());
            onView(withId(R.id.event_location_input))
                    .perform(typeText(""), closeSoftKeyboard());
            onView(withId(R.id.event_latitude_input))
                    .perform(typeText(""), closeSoftKeyboard());
            onView(withId(R.id.event_longitude_input))
                    .perform(typeText(""), closeSoftKeyboard());

            // Click the Add Event button
            onView(withId(R.id.add_event_button)).perform(click());

            // Verify that an error toast is displayed
            Espresso.onView(withText("Please fill in all fields."))
                    .inRoot(new ToastMatcher())
                    .check(matches(isDisplayed()));
        }
    }

    @Test
    public void testCancelAddEvent() {
        try (ActivityScenario<AddEventActivity> scenario = ActivityScenario.launch(AddEventActivity.class)) {
            // Click the Cancel button
            onView(withId(R.id.cancel_button)).perform(click());

            // Verify that the user is navigated back to the previous screen (e.g., MapsViewActivity)
            Espresso.onView(withId(R.id.add_event_button)) // This is from MapsViewActivity
                    .check(matches(isDisplayed()));
        }
    }
}
