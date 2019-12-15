package com.example.apartmentcitizen.network;

import com.example.apartmentcitizen.home.transaction.receipt.receiptdetail.ElectricRangePriceObject;

import retrofit2.Call;
import retrofit2.http.GET;

public interface LoadElectricRangePrice {

    @GET("electricPrices")
    Call<ElectricRangePriceObject> getElectricRangePrice();

}
