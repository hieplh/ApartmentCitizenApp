package com.example.apartmentcitizen.network;

import com.example.apartmentcitizen.home.transaction.FullnameObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface LoadFullnameByIdService {

    @GET("users/fullname/{id}")
    Call<FullnameObject> getFullnameById(@Path("id") int id);
}
