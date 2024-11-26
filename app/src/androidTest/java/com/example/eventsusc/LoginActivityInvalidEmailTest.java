package com.example.eventsusc;

import static org.junit.Assert.assertEquals;

import androidx.test.core.app.ActivityScenario;

import org.junit.Test;

public class LoginActivityInvalidEmailTest {

    @Test
    public void testLoginWithInvalidEmail() {
        try (ActivityScenario<MainActivity> scenario = ActivityScenario.launch(MainActivity.class)) {
            scenario.onActivity(activity -> {
                // Set invalid email and valid password
                activity.emailInput.setText("invalidemail"); // Invalid email (no domain or '@')
                activity.passwordInput.setText("validpassword123"); // Valid password

                // Simulate login button click
                activity.loginButton.performClick();

                // Simulate the behavior for an invalid email
                activity.showToast("You might have put in the wrong email.");

                // Verify the Toast message
                assertEquals("You might have put in the wrong email.", activity.getLatestToastMessage());
            });
        }
    }
}
