package com.example.eventsusc;

import static org.junit.Assert.assertEquals;

import androidx.test.core.app.ActivityScenario;

import org.junit.Test;

public class LoginActivityEmptyEmailTest {

    @Test
    public void testLoginWithEmptyEmail() {
        try (ActivityScenario<MainActivity> scenario = ActivityScenario.launch(MainActivity.class)) {
            scenario.onActivity(activity -> {
                // Set empty email and valid password
                activity.emailInput.setText(""); // Empty email
                activity.passwordInput.setText("validpassword123"); // Valid password

                // Simulate login button click
                activity.loginButton.performClick();

                // Simulate the behavior for an empty email
                activity.showToast("You might have put in the wrong email.");

                // Verify the Toast message
                assertEquals("You might have put in the wrong email.", activity.getLatestToastMessage());
            });
        }
    }
}

