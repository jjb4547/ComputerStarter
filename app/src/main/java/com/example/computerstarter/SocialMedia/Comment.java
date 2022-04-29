package com.example.computerstarter.SocialMedia;

public class Comment {
    public Comment() {
    }

    private String commentBody,commentedProfile;

    public String getCommentBody() {
        return commentBody;
    }

    public void setCommentBody(String commentBody) {
        this.commentBody = commentBody;
    }

    public String getCommentedBy() {
        return commentedBy;
    }

    public void setCommentedBy(String commentedBy) {
        this.commentedBy = commentedBy;
    }

    public long getCommentedAt() {
        return commentedAt;
    }

    public void setCommentedAt(long commentedAt) {
        this.commentedAt = commentedAt;
    }

    private String commentedBy;
    private long commentedAt;

    public String getCommentedProfile() {
        return commentedProfile;
    }

    public void setCommentedProfile(String commentedProfile) {
        this.commentedProfile = commentedProfile;
    }
}
