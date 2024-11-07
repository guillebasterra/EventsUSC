package com.example.eventsusc;

public class Comment {
    private String userName;
    private String text;
    private long timestamp;

    // Default constructor required for calls to DataSnapshot.getValue(Comment.class)
    public Comment() {}

    public Comment(String userName, String text, long timestamp) {
        this.userName = userName;
        this.text = text;
        this.timestamp = timestamp;
    }

    // Getter for userName
    public String getUserName() {
        return userName;
    }

    // Getter for text
    public String getText() {
        return text;
    }

    // Getter for timestamp
    public long getTimestamp() {
        return timestamp;
    }

    // Optional: Method to format timestamp for display
    public String getFormattedTimestamp() {
        java.text.DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(null);
        return dateFormat.format(new java.util.Date(timestamp));
    }
}
