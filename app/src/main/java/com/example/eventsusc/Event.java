package com.example.eventsusc;

public class Event {
    private String eventId;
    private String eventName;
    private String eventDescription;
    private String eventDateTime;
    private String eventLocation;
    private double latitude;
    private double longitude;
    private int upvotes;  // New field for upvotes
    private int downvotes;  // New field for downvotes

    // Constructor with latitude and longitude
    public Event(String eventId, String eventName, String eventDescription, String eventDateTime, String eventLocation, double latitude, double longitude) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.eventDescription = eventDescription;
        this.eventDateTime = eventDateTime;
        this.eventLocation = eventLocation;
        this.latitude = latitude;
        this.longitude = longitude;
        this.upvotes = upvotes;
        this.downvotes = downvotes;
    }

    // Default constructor (required for Firebase)
    public Event() {}

    // Getters and setters
    public String getEventId() { return eventId; }
    public void setEventId(String eventId) { this.eventId = eventId; }

    // Getters and setters for upvotes and downvotes
    public int getUpvotes() { return upvotes; }
    public void setUpvotes(int upvotes) { this.upvotes = upvotes; }

    public int getDownvotes() { return downvotes; }
    public void setDownvotes(int downvotes) { this.downvotes = downvotes; }

    public String getEventName() { return eventName; }
    public void setEventName(String eventName) { this.eventName = eventName; }

    public String getEventDescription() { return eventDescription; }
    public void setEventDescription(String eventDescription) { this.eventDescription = eventDescription; }

    public String getEventDateTime() { return eventDateTime; }
    public void setEventDateTime(String eventDateTime) { this.eventDateTime = eventDateTime; }

    public String getEventLocation() { return eventLocation; }
    public void setEventLocation(String eventLocation) { this.eventLocation = eventLocation; }

    public double getLatitude() { return latitude; }
    public void setLatitude(double latitude) { this.latitude = latitude; }

    public double getLongitude() { return longitude; }
    public void setLongitude(double longitude) { this.longitude = longitude; }
}
