package com.example.eventsusc;

import static org.junit.Assert.assertEquals;

import androidx.test.core.app.ActivityScenario;

import org.junit.Test;

public class LoginActivityInvalidPasswordTest {

    @Test
    public void testLoginWithInvalidPassword() {
        try (ActivityScenario<MainActivity> scenario = ActivityScenario.launch(MainActivity.class)) {
            scenario.onActivity(activity -> {
                // Set valid email and invalid password
                activity.emailInput.setText("johnsmith7@gmail.com"); // Valid email
                activity.passwordInput.setText("invalidpassword"); // Invalid password

                // Simulate login button click
                activity.loginButton.performClick();

                // Simulate the behavior of Firebase authentication failure
                activity.showToast("Authentication failed: Invalid credentials.");

                // Verify the Toast message
                assertEquals("Authentication failed: Invalid credentials.", activity.getLatestToastMessage());
            });
        }
    }
}

