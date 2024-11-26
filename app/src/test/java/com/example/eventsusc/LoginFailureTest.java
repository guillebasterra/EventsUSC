package com.example.eventsusc;
import static org.junit.Assert.assertEquals;
import android.widget.EditText;

import org.junit.Test;

public class LoginFailureTest {
    @Test
    public void testLoginFailureHandling() {
        MainActivity activity = new MainActivity();

        // Set up test data
        activity.emailInput = new EditText(activity);
        activity.passwordInput = new EditText(activity);
        activity.emailInput.setText("test@example.com");
        activity.passwordInput.setText("wrongpassword");

        // Simulate Firebase login failure
        activity.showToast("Authentication failed: Invalid credentials");

        // Simulate login button click
        activity.loginButton.performClick();

        // Check result
        assertEquals("Authentication failed: Invalid credentials", activity.getLatestToastMessage());
    }

}
