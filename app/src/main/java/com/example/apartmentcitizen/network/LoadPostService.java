package com.example.apartmentcitizen.network;


import com.example.apartmentcitizen.home.dashboard.newsfeed.PostDTO;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface LoadPostService {
    @GET("posts")
    Call<List<PostDTO>> getPost();

    @GET("posts")
    Call getAllPersonLike();
}
