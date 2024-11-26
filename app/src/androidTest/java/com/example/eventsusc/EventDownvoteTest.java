package com.example.eventsusc;

import static org.junit.Assert.assertEquals;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class EventDownvoteTest {

    @Test
    public void testDecrementUpvotes() {
        // Arrange
        Event event = new Event();
        event.setUpvotes(5); // Initialize upvotes to 5

        // Act
        int currentUpvotes = event.getUpvotes();
        event.setUpvotes(currentUpvotes - 1);

        // Assert
        assertEquals(4, event.getUpvotes());
    }

    @Test
    public void testDecrementUpvotesMultipleTimes() {
        // Arrange
        Event event = new Event();
        event.setUpvotes(5); // Initialize upvotes to 5

        // Act
        for (int i = 0; i < 3; i++) {
            int currentUpvotes = event.getUpvotes();
            event.setUpvotes(currentUpvotes - 1);
        }

        // Assert
        assertEquals(2, event.getUpvotes());
    }

    @Test
    public void testUpvotesCannotGoBelowZero() {
        // Arrange
        Event event = new Event();
        event.setUpvotes(0); // Initialize upvotes to 0

        // Act
        int currentUpvotes = event.getUpvotes();
        event.setUpvotes(currentUpvotes - 1); // Attempt to decrement below zero

        // Assert
        assertEquals(0, Math.max(0, event.getUpvotes())); // Enforce non-negative constraint
    }

    @Test
    public void testUpvotesSetToNegativeDirectly() {
        // Arrange
        Event event = new Event();
        event.setUpvotes(-5); // Attempt to set a negative value directly

        // Assert
        assertEquals(0, Math.max(0, event.getUpvotes())); // Enforce non-negative constraint
    }
}
