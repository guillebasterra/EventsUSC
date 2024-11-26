package com.example.eventsusc;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.core.app.ActivityScenario;

import org.junit.Test;

public class CreateAccountNavBlackTest {
    @Test
    public void testNavigationToCreateAccountScreen() {
        // Launch MainActivity
        ActivityScenario.launch(MainActivity.class);

        // Click the "Create New Account" button
        onView(withId(R.id.new_account_button)).perform(click());

        // Verify the activity change
        intended(hasComponent(AccountCreatedActivity.class.getName()));
    }

}
