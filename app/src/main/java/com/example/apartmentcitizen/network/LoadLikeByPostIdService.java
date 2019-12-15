package com.example.apartmentcitizen.network;

import com.example.apartmentcitizen.component.PostAdapter;
import com.example.apartmentcitizen.home.dashboard.newsfeed.CommentDTO;
import com.example.apartmentcitizen.home.dashboard.newsfeed.CommmentActivity;
import com.example.apartmentcitizen.home.dashboard.newsfeed.LikeDTO;
import com.example.apartmentcitizen.home.dashboard.newsfeed.NewsfeedActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface LoadLikeByPostIdService {
    @GET("likes/posts/{id}")
    Call<List<LikeDTO>> getLikeByPostId(@Path("id") int id);

    @GET("likes")
    Call<List<LikeDTO>> getAllLike();

    @Headers("Content-Type: application/json")
    @POST("likes")
    Call<PostAdapter.LikeResponse> createLike(@Body LikeDTO likeDTO);

    @Headers("Content-Type: application/json")
    @DELETE("likes/{likeId}")
    Call<String> removeLikeById(@Path("likeId") int id);


}
