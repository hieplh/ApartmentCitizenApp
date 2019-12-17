package com.example.apartmentcitizen.network;

import com.example.apartmentcitizen.home.transaction.TransactionObject;
import com.example.apartmentcitizen.home.transaction.receipt.receiptdetail.ReceiptDetailActivity;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ReceiptDetailService {

    @Headers("Content-Type: application/json")
    @PUT("receipts/{receiptId}/status/{status}")
    Call<ReceiptDetailActivity.ReceiptDetailResponse> updateReceiptStatusByReceiptId(@Path("receiptId") int receiptId, @Path("status") int status);


    @Headers("Content-Type: application/json")
    @POST("transactions")
    Call<ReceiptDetailActivity.ReceiptDetailResponse> createUser(@Body TransactionObject transactionObject);

}
