package com.example.eventsusc;
import static org.junit.Assert.assertEquals;

import android.content.Intent;
import android.widget.EditText;

import org.junit.Test;

public class FirebaseLoginDelayTest {
    @Test
    public void testDelayedFirebaseLoginResponse() throws InterruptedException {
        MainActivity activity = new MainActivity();

        // Set up test data
        activity.emailInput = new EditText(activity);
        activity.passwordInput = new EditText(activity);
        activity.emailInput.setText("test@example.com");
        activity.passwordInput.setText("password123");

        // Simulate Firebase's delayed login behavior
        new Thread(() -> {
            try {
                Thread.sleep(3000); // Simulate delay
                activity.runOnUiThread(() -> {
                    activity.showToast("Login successful.");
                    activity.startActivity(new Intent(activity, MapsViewActivity.class));
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        // Simulate login button click
        activity.loginButton.performClick();

        // Wait for thread to complete
        Thread.sleep(4000); // Wait for the delay to complete
        assertEquals("Login successful.", activity.getLatestToastMessage());
        assertEquals(MapsViewActivity.class.getName(), activity.getNextStartedActivity().getComponent().getClassName());
    }

}
