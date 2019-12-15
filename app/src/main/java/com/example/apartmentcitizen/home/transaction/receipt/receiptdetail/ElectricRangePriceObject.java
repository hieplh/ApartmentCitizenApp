package com.example.apartmentcitizen.home.transaction.receipt.receiptdetail;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ElectricRangePriceObject implements Serializable {

    @SerializedName("electricId")
    private int eId;

    @SerializedName("companyName")
    private String companyName;

    @SerializedName("from0To50")
    private int range1;

    @SerializedName("from51To100")
    private int range2;

    @SerializedName("from101To200")
    private int range3;

    @SerializedName("from201To300")
    private int range4;

    @SerializedName("from301To400")
    private int range5;

    @SerializedName("moreThan400")
    private int range6;

    @SerializedName("vat")
    private float vat;
}
