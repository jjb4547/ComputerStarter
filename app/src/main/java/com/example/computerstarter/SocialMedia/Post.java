package com.example.computerstarter.SocialMedia;

public class Post {
    private String postId, postImage, postedBy, postDescription,tag,profile;
    private boolean isDefault;
    private int postLike,postComment;

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }

    public Post() {
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getPostImage() {
        return postImage;
    }

    public void setPostImage(String postImage) {
        this.postImage = postImage;
    }

    public String getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(String postedBy) {
        this.postedBy = postedBy;
    }

    public String getPostDescription() {
        return postDescription;
    }

    public void setPostDescription(String postDescription) {
        this.postDescription = postDescription;
    }

    public long getPostedAt() {
        return postedAt;
    }

    public void setPostedAt(long postedAt) {
        this.postedAt = postedAt;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Post(String postId, String postImage, String postedBy, String postDescription, long postedAt, String tag, boolean isDefault, String profile) {
        this.postId = postId;
        this.postImage = postImage;
        this.postedBy = postedBy;
        this.postDescription = postDescription;
        this.postedAt = postedAt;
        this.tag = tag;
        this.isDefault = isDefault;
        this.profile = profile;
    }

    private long postedAt;

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public int getPostLike() {
        return postLike;
    }

    public void setPostLike(int postLike) {
        this.postLike = postLike;
    }

    public int getPostComment() {
        return postComment;
    }

    public void setPostComment(int postComment) {
        this.postComment = postComment;
    }
}
