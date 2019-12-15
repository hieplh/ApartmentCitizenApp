package com.example.apartmentcitizen.network;

import com.example.apartmentcitizen.home.dashboard.newsfeed.LikeDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface LoadLikeByPostIdService {
    @GET("likes/posts/{id}")
    Call<List<LikeDTO>> getLikeByPostId(@Path("id") int id);

    @GET("likes")
    Call<List<LikeDTO>> getAllLike();

}
