package com.example.apartmentcitizen.network;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CommentService {

    @GET("comments/count/posts/{postId}")
    Call<Integer> countCommentByPostId(@Path("postId") int postId);
}
