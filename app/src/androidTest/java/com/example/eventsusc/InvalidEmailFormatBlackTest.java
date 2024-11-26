package com.example.eventsusc;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.core.app.ActivityScenario;

import org.junit.Test;

public class InvalidEmailFormatBlackTest {
    @Test
    public void testInvalidEmailFormatError() {
        // Launch MainActivity
        ActivityScenario.launch(MainActivity.class);

        // Enter an invalid email format
        onView(withId(R.id.email_input)).perform(typeText("invalid-email"), closeSoftKeyboard());
        onView(withId(R.id.password_input)).perform(typeText("password123"), closeSoftKeyboard());

        // Click the "Login" button
        onView(withId(R.id.login_button)).perform(click());

        // Verify the Toast message
        onView(withText("Invalid email format."))
                .inRoot(ToastMatcher.isToast())
                .check(matches(isDisplayed()));
    }

}
