package com.example.apartmentcitizen.home.transaction;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TransactionObject implements Serializable {

    @SerializedName("transactionId")
    private String id;

    @SerializedName("house")
    private HouseObject house;

    @SerializedName("amount")
    private long amount;

    @SerializedName("title")
    private String title;

    @SerializedName("status")
    private int status;

    @SerializedName("transactor")
    private int transactorId;

    @SerializedName("createdDate")
    private String createdDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public HouseObject getHouse() {
        return house;
    }

    public void setHouse(HouseObject house) {
        this.house = house;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getTransactorId() {
        return transactorId;
    }

    public void setTransactorId(int transactorId) {
        this.transactorId = transactorId;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }


}
