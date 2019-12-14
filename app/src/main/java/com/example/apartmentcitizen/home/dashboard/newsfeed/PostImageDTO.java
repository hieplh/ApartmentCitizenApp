package com.example.apartmentcitizen.home.dashboard.newsfeed;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PostImageDTO implements Serializable {

    @SerializedName("postImageId")
    private long postImageId;

    @SerializedName("url")
    private String imageUrl;

    @SerializedName("post")
    private PostDTO post;

    public PostImageDTO(long postImageId, String imageUrl, PostDTO post) {
        this.postImageId = postImageId;
        this.imageUrl = imageUrl;
        this.post = post;
    }

    public long getPostImageId() {
        return postImageId;
    }

    public void setPostImageId(long postImageId) {
        this.postImageId = postImageId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public PostDTO getPost() {
        return post;
    }

    public void setPost(PostDTO post) {
        this.post = post;
    }
}
