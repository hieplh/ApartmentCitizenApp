package com.example.apartmentcitizen.network;

import com.example.apartmentcitizen.home.transaction.receipt.receiptdetail.ReceiptDetailObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface LoadReceiptDetailByReceiptId {

    @GET("receiptDetails/receipts/{receiptId}")
    Call<List<ReceiptDetailObject>> getReceiptDetailByReceiptId(@Path("receiptId") int id);
}
