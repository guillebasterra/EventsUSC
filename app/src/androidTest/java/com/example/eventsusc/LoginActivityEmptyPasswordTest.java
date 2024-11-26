package com.example.eventsusc;

import static org.junit.Assert.assertEquals;

import androidx.test.core.app.ActivityScenario;

import org.junit.Test;

public class LoginActivityEmptyPasswordTest {

    @Test
    public void testLoginWithEmptyPassword() {
        try (ActivityScenario<MainActivity> scenario = ActivityScenario.launch(MainActivity.class)) {
            scenario.onActivity(activity -> {
                // Set valid email but empty password
                activity.emailInput.setText("johnsmith7@gmail.com");
                activity.passwordInput.setText(""); // Empty password

                // Simulate login button click
                activity.loginButton.performClick();

                // Verify the Toast message
                assertEquals("Please enter both email and password.", activity.getLatestToastMessage());
            });
        }
    }
}
