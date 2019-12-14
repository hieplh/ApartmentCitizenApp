package com.example.apartmentcitizen.network;

import com.example.apartmentcitizen.home.dashboard.newsfeed.PostImageDTO;
import com.example.apartmentcitizen.image.LoadImage;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface PostImageService {
    @Multipart
    @POST("postImages/images")
    Call<ResponseBody> uploadImage(@Part MultipartBody.Part file, @Part("name") RequestBody requestBody);

    @GET("postImages/imagesall")
    Call<LoadImage> getPathImage();

    @GET("postImages")
    Call<List<PostImageDTO>> getAllPostImage();

}
