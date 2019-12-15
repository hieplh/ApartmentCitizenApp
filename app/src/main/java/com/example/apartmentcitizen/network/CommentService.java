package com.example.apartmentcitizen.network;

import com.example.apartmentcitizen.home.dashboard.newsfeed.CommentDTO;
import com.example.apartmentcitizen.home.dashboard.newsfeed.CommmentActivity;
import com.example.apartmentcitizen.register.Register;
import com.example.apartmentcitizen.register.RegisterActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface CommentService {

    @GET("comments/count/posts/{postId}")
    Call<Integer> countCommentByPostId(@Path("postId") int postId);

    @GET("comments/posts/{postId}")
    Call<List<CommentDTO>> getCommentByPostId(@Path("postId") int postId);

    @Headers("Content-Type: application/json")
    @POST("comments")
    Call<CommmentActivity.CommentResponse> createComment(@Body CommentDTO commentDTO);

}
