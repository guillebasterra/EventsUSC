package com.example.eventsusc;

public class Event {
    private String eventId;
    private String eventName;
    private String eventDescription;
    private String eventDateTime;
    private String eventLocation;

    // constructor
    public Event(String eventId, String eventName, String eventDescription, String eventDateTime, String eventLocation) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.eventDescription = eventDescription;
        this.eventDateTime = eventDateTime;
        this.eventLocation = eventLocation;
    }

    // Firebase getters and setters
    public String getEventId() { return eventId; }
    public void setEventId(String eventId) { this.eventId = eventId; }

    public String getEventName() { return eventName; }
    public void setEventName(String eventName) { this.eventName = eventName; }

    public String getEventDescription() { return eventDescription; }
    public void setEventDescription(String eventDescription) { this.eventDescription = eventDescription; }

    public String getEventDateTime() { return eventDateTime; }
    public void setEventDateTime(String eventDateTime) { this.eventDateTime = eventDateTime; }

    public String getEventLocation() { return eventLocation; }
    public void setEventLocation(String eventLocation) { this.eventLocation = eventLocation; }
}