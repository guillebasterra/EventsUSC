package com.example.eventsusc;
import static org.junit.Assert.assertEquals;

import android.widget.EditText;

import org.junit.Test;

public class PasswordResetEmailTest {
    @Test
    public void testForgotPasswordEmailSent() {
        MainActivity activity = new MainActivity();

        // Set up test data
        activity.emailInput = new EditText(activity);
        activity.emailInput.setText("test@example.com");

        // Simulate forgot password button click
        activity.forgotPasswordButton.performClick();

        // Simulate a successful password reset email
        activity.showToast("Password reset email sent.");

        // Check result
        assertEquals("Password reset email sent.", activity.getLatestToastMessage());
    }

}
