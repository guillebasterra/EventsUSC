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

public class PasswordResetGoodEmailBlackTest {
    @Test
    public void testPasswordResetWithValidEmail() {
        // Launch MainActivity
        ActivityScenario.launch(MainActivity.class);

        // Enter a valid email
        onView(withId(R.id.email_input)).perform(typeText("test@example.com"), closeSoftKeyboard());

        // Click the "Forgot Password" button
        onView(withId(R.id.forgot_password_button)).perform(click());

        // Verify the Toast message
        onView(withText("Password reset email sent."))
                .inRoot(ToastMatcher.isToast())
                .check(matches(isDisplayed()));
    }

}
