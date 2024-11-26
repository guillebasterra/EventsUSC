package com.example.eventsusc;
import static org.junit.Assert.assertEquals;
import android.widget.EditText;

import org.junit.Test;

public class PartialLoginValidationTest {
    @Test
    public void testEmailEmptyValidation() {
        MainActivity activity = new MainActivity();

        // Set up test data
        activity.emailInput = new EditText(activity);
        activity.passwordInput = new EditText(activity);
        activity.emailInput.setText("");
        activity.passwordInput.setText("password123");

        // Simulate login button click
        activity.loginButton.performClick();

        // Check result
        assertEquals("Please enter both email and password.", activity.getLatestToastMessage());
    }
}
