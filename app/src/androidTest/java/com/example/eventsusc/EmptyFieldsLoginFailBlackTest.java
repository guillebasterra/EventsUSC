package com.example.eventsusc;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import static org.hamcrest.CoreMatchers.not;

import androidx.test.core.app.ActivityScenario;

import org.junit.Test;

public class EmptyFieldsLoginFailBlackTest {
    @Test
    public void testLoginButtonDisabledForEmptyFields() {
        // Launch MainActivity
        ActivityScenario.launch(MainActivity.class);

        // Ensure both fields are empty
        onView(withId(R.id.email_input)).perform(clearText(), closeSoftKeyboard());
        onView(withId(R.id.password_input)).perform(clearText(), closeSoftKeyboard());

        // Check that the login button is disabled
        onView(withId(R.id.login_button)).check(matches(not(isEnabled())));
    }

}
