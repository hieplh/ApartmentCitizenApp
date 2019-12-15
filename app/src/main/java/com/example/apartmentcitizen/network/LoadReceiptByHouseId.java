package com.example.apartmentcitizen.network;

import com.example.apartmentcitizen.home.transaction.receipt.ReceiptObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface LoadReceiptByHouseId {

    @GET("receipts/houses/{id}")
    Call<List<ReceiptObject>> getReceiptByHouseId(@Path("id") int id);
}
