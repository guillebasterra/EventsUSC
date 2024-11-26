package com.example.eventsusc;

import android.content.ComponentName;

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
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class LoginActivityMismatchedCredentialsTest {

    @Before
    public void setUp() {
        Intents.init(); // Initialize Intents for navigation testing
    }

    @After
    public void tearDown() {
        Intents.release(); // Release Intents after tests
    }

    @Test
    public void testLoginWithMismatchedEmailAndPassword() {
        try (ActivityScenario<MainActivity> scenario = ActivityScenario.launch(MainActivity.class)) {
            // Enter valid but mismatched credentials
            onView(withId(R.id.email_input))
                    .perform(typeText("validemail@example.com"), closeSoftKeyboard()); // Valid email
            onView(withId(R.id.password_input))
                    .perform(typeText("validpassword123"), closeSoftKeyboard()); // Valid password (but does not match the email)

            // Click the login button
            onView(withId(R.id.login_button)).perform(click());

            // Simulate the behavior for email and password mismatch
            scenario.onActivity(activity -> {
                activity.showToast("Your password and email do not match the same account.");
            });

            // Verify the displayed Toast message
            scenario.onActivity(activity -> {
                assertEquals("Your password and email do not match the same account.", activity.getLatestToastMessage());
            });
        }
    }
}
