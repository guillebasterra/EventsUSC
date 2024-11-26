package com.example.eventsusc;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MapsViewActivityTest {

    @Before
    public void setUp() {
        Intents.init(); // Initialize intents for navigation testing
    }

    @After
    public void tearDown() {
        Intents.release(); // Release intents after tests
    }

    @Test
    public void testAddEventButtonNavigatesToAddEventActivity() {
        try (ActivityScenario<MapsViewActivity> scenario = ActivityScenario.launch(MapsViewActivity.class)) {
            // Perform a click on the "Add Event" button
            onView(withId(R.id.add_event_button)).perform(click());

            // Verify that the Intent to AddEventActivity was triggered
            intended(hasComponent(AddEventActivity.class.getName()));
        }
    }
}
