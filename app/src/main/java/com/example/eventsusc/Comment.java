package com.example.eventsusc;

public class Comment {
    private String commentId;
    private String commentTitle;
    private String commentText;

    // Default constructor (required for Firebase)
    public Comment() {}

    // Constructor
    public Comment(String commentId, String commentTitle, String commentText) {
        this.commentId = commentId;
        this.commentTitle = commentTitle;
        this.commentText = commentText;
    }

    // Getters and setters
    public String getCommentId() { return commentId; }
    public void setCommentId(String commentId) { this.commentId = commentId; }

    public String getCommentTitle() { return commentTitle; }
    public void setCommentTitle(String commentTitle) { this.commentTitle = commentTitle; }

    public String getCommentText() { return commentText; }
    public void setCommentText(String commentText) { this.commentText = commentText; }
}
