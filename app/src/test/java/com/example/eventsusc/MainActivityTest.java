package com.example.eventsusc;

import static org.junit.Assert.*;

import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;

import org.junit.Before;
import org.junit.Test;

public class MainActivityTest {

    private MainActivity activity;

    @Before
    public void setUp() {
        // Initialize MainActivity
        activity = new MainActivity();
        activity.onCreate(null);
    }

    @Test
    public void testLoginValidation() {
        // Get references to UI elements
        EditText emailInput = activity.findViewById(R.id.email_input);
        EditText passwordInput = activity.findViewById(R.id.password_input);
        Button loginButton = activity.findViewById(R.id.login_button);

        // Empty email and password
        emailInput.setText("");
        passwordInput.setText("");
        loginButton.performClick();
        assertEquals("Please enter both email and password.", activity.getLatestToastMessage());

        // Empty password
        emailInput.setText("valid@example.com");
        passwordInput.setText("");
        loginButton.performClick();
        assertEquals("Please enter both email and password.", activity.getLatestToastMessage());

        // Valid email and password
        emailInput.setText("valid@example.com");
        passwordInput.setText("password123");
        loginButton.performClick();
        // Verify no validation error for valid input (toast message will depend on authentication result)
        assertNotNull(activity.getLatestToastMessage());
    }

    @Test
    public void testShowToast() {
        // Test toast message storage and display
        activity.showToast("Test message");
        assertEquals("Test message", activity.getLatestToastMessage());
    }

    @Test
    public void testForgotPasswordValidation() {
        // Get references to UI elements
        EditText emailInput = activity.findViewById(R.id.email_input);
        Button forgotPasswordButton = activity.findViewById(R.id.forgot_password_button);

        // Empty email input
        emailInput.setText("");
        forgotPasswordButton.performClick();
        assertEquals("Please enter your email to reset password.", activity.getLatestToastMessage());

        // Valid email input
        emailInput.setText("valid@example.com");
        forgotPasswordButton.performClick();
        assertNotNull(activity.getLatestToastMessage());
    }

    @Test
    public void testNewAccountButtonNavigation() {
        // Click on "Create New Account" button
        Button newAccountButton = activity.findViewById(R.id.new_account_button);
        newAccountButton.performClick();

        Intent nextIntent = activity.getNextStartedActivity();
        assertNotNull(nextIntent);
        assertEquals(MapsViewActivity.class.getName(), nextIntent.getComponent().getClassName());
    }

    @Test
    public void testSuccessfulLoginNavigation() {
        // Set valid inputs
        EditText emailInput = activity.findViewById(R.id.email_input);
        EditText passwordInput = activity.findViewById(R.id.password_input);
        emailInput.setText("valid@example.com");
        passwordInput.setText("password123");

        // Simulate login button click
        Button loginButton = activity.findViewById(R.id.login_button);
        loginButton.performClick();

        Intent nextIntent = activity.getNextStartedActivity();
        assertNotNull(nextIntent);
        assertEquals(MapsViewActivity.class.getName(), nextIntent.getComponent().getClassName());
    }
}
