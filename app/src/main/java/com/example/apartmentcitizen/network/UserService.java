package com.example.apartmentcitizen.network;

import com.example.apartmentcitizen.register.Register;
import com.example.apartmentcitizen.register.RegisterActivity;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface UserService {
    @Multipart
    @POST("postImages/images")
    Call<ResponseBody> uploadImage(@Part MultipartBody.Part file, @Part("name") RequestBody requestBody);

    @Headers("Content-Type: application/json")
    @POST("users")
    Call<RegisterActivity.RegisterResponse> createUser(@Body Register register);
}