package com.example.apartmentcitizen.network;

import com.example.apartmentcitizen.login.Login;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface LoginService {

    @GET("users/signin/{email}")
    Call<Login> executeLogin(@Path("email") String email);
}
