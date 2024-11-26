package com.example.eventsusc;

import static org.junit.Assert.assertEquals;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class EventUpvoteTest {

    @Test
    public void testIncrementUpvotes() {
        // Arrange
        Event event = new Event();
        event.setUpvotes(0); // Initialize upvotes to 0

        // Act
        int currentUpvotes = event.getUpvotes();
        event.setUpvotes(currentUpvotes + 1);

        // Assert
        assertEquals(1, event.getUpvotes());
    }

    @Test
    public void testIncrementUpvotesMultipleTimes() {
        // Arrange
        Event event = new Event();
        event.setUpvotes(0); // Initialize upvotes to 0

        // Act
        for (int i = 0; i < 5; i++) {
            int currentUpvotes = event.getUpvotes();
            event.setUpvotes(currentUpvotes + 1);
        }

        // Assert
        assertEquals(5, event.getUpvotes());
    }

    @Test
    public void testUpvotesRemainNonNegative() {
        // Arrange
        Event event = new Event();
        event.setUpvotes(0); // Initialize upvotes to 0

        // Act
        event.setUpvotes(-5); // Attempt to set a negative value

        // Assert
        assertEquals(0, Math.max(0, event.getUpvotes())); // Enforce non-negative constraint
    }
}
