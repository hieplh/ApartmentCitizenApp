package com.example.apartmentcitizen.network;

import com.example.apartmentcitizen.image.LoadImage;

import retrofit2.Call;
import retrofit2.http.GET;

public interface LoadImageService {
    @GET("postImages/imagesall")
    Call<LoadImage> getPathImage();
}
