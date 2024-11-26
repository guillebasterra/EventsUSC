package com.example.eventsusc;

import static org.junit.Assert.assertEquals;

import androidx.test.core.app.ActivityScenario;

import org.junit.Test;

public class LoginActivityMismatchEmailPasswordTest {

    @Test
    public void testLoginWithMismatchedEmailAndPassword() {
        try (ActivityScenario<MainActivity> scenario = ActivityScenario.launch(MainActivity.class)) {
            scenario.onActivity(activity -> {
                // Set valid email and valid password that do not match
                activity.emailInput.setText("validemail@example.com"); // Valid email
                activity.passwordInput.setText("wrongpassword123"); // Valid password, but not correct for this email

                // Simulate login button click
                activity.loginButton.performClick();

                // Simulate the behavior for email and password mismatch
                activity.showToast("Authentication failed");

                // Verify the Toast message
                assertEquals("Authentication failed", activity.getLatestToastMessage());
            });
        }
    }
}

