package com.example.apartmentcitizen.home.dashboard.newsfeed;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PostDTO implements Serializable {

    @SerializedName("postId")
    private int postId;

    @SerializedName("body")
    private String desc;

    @SerializedName("createdDate")
    private String createdDate;

    @SerializedName("user")
    private UserDTO user;

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }
}

