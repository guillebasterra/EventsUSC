package com.example.eventsusc;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import android.content.Intent;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.junit.Test;

public class CreateAccountButtonNavTest {
    @Test
    public void testNewAccountButtonNavigation() {
        // Set up MainActivity and simulate button click
        MainActivity activity = new MainActivity();

        // Simulate a user UID that will be passed via intent
        String testUid = "123456";

        // Simulate setting up the intent
        Intent intent = new Intent(activity, AccountCreatedActivity.class);
        intent.putExtra("USER_UID", testUid);

        activity.startActivity(intent);

        // Validate that the correct activity is started
        Intent startedIntent = activity.getNextStartedActivity();
        assertNotNull(startedIntent);
        assertEquals(AccountCreatedActivity.class.getName(), startedIntent.getComponent().getClassName());

        // Check that the USER_UID extra is correctly passed
        assertEquals(testUid, startedIntent.getStringExtra("USER_UID"));

        // Simulate AccountCreatedActivity behavior
        AccountCreatedActivity accountCreatedActivity = new AccountCreatedActivity();
        accountCreatedActivity.onCreate(null); // Manually call onCreate for testing

        // Mock Firebase and simulate database interaction
        accountCreatedActivity.databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(testUid);

        accountCreatedActivity.databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    User mockUser = new User("test@example.com", "Test User");
                    snapshot.getValue(mockUser.getClass());

                    assertEquals("Account created!", accountCreatedActivity.accountCreatedMessage.getText().toString());
                    assertEquals("Email: test@example.com", accountCreatedActivity.userEmail.getText().toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                fail("Firebase data retrieval should not be canceled during test.");
            }
        });
    }


}
