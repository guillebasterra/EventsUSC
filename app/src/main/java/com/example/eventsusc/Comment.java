package com.example.eventsusc;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Date;

public class Comment {
    private String userName;
    private String text;
    private long timestamp;

    // Default constructor required for Firebase
    public Comment() {}

    public Comment(String userName, String text, long timestamp) {
        this.userName = userName;
        this.text = text;
        this.timestamp = timestamp;
    }

    public String getUserName() {
        return userName;
    }

    public String getText() {
        return text;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getFormattedTimestamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm", Locale.getDefault());
        return sdf.format(new Date(timestamp));
    }
}