package com.example.apartmentcitizen.home.dashboard.newsfeed;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CommentDTO implements Serializable {

    @SerializedName("commentId")
    private long commentId;

    @SerializedName("user")
    private UserDTO user;

    @SerializedName("createdDate")
    private String createdDate;

    @SerializedName("detail")
    private String detail;

    @SerializedName("post")
    private PostDTO post;

    public CommentDTO(long commentId, UserDTO user, String createdDate, String detail, PostDTO post) {
        this.commentId = commentId;
        this.user = user;
        this.createdDate = createdDate;
        this.detail = detail;
        this.post = post;
    }

    public CommentDTO() {
    }

    public long getCommentId() {
        return commentId;
    }

    public void setCommentId(long commentId) {
        this.commentId = commentId;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public PostDTO getPost() {
        return post;
    }

    public void setPost(PostDTO post) {
        this.post = post;
    }
}
