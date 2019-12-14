package com.example.apartmentcitizen.network;

import com.example.apartmentcitizen.home.transaction.TransactionObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface LoadTransactionByHouseIdService {
    @GET("transactions/houses/{id}")
    Call<List<TransactionObject>> getTransactionByHouseId(@Path("id") int id);

}
