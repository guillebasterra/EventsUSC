package com.example.eventsusc;

import android.content.ComponentName;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.matcher.ViewMatchers;
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
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class LoginActivityTest {

    @Before
    public void setUp() {
        Intents.init(); // Initialize Intents for navigation testing
    }

    @After
    public void tearDown() {
        Intents.release(); // Release Intents after tests
    }

    @Test
    public void testLoginWithValidCredentials() {
        try (ActivityScenario<MainActivity> scenario = ActivityScenario.launch(MainActivity.class)) {
            // Enter valid credentials
            onView(withId(R.id.email_input))
                    .perform(typeText("johnsmith7@gmail.com"), closeSoftKeyboard());
            onView(withId(R.id.password_input))
                    .perform(typeText("Johnsmith7"), closeSoftKeyboard());

            // Click the login button
            onView(withId(R.id.login_button)).perform(click());

            // Wait for the transition to complete
            Thread.sleep(2000);

            // Verify that a view unique to MapsViewActivity (e.g., map) is displayed
            onView(withId(R.id.map))
                    .check(matches(isDisplayed()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



    @Test
    public void testLoginWithInvalidCredentials() {
        try (ActivityScenario<MainActivity> scenario = ActivityScenario.launch(MainActivity.class)) {
            // Input invalid credentials
            onView(withId(R.id.email_input))
                    .perform(typeText("invaliduser@example.com"), closeSoftKeyboard());
            onView(withId(R.id.password_input))
                    .perform(typeText("invalidpassword"), closeSoftKeyboard());

            // Click the login button
            onView(withId(R.id.login_button)).perform(click());

            // Verify that we remain on MainActivity
            scenario.onActivity(activity -> {
                ComponentName componentName = activity.getComponentName();
                assertEquals("com.example.eventsusc.MainActivity", componentName.getClassName());
            });
        }
    }
}
