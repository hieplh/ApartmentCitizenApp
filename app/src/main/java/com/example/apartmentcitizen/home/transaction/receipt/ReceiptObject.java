package com.example.apartmentcitizen.home.transaction.receipt;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ReceiptObject implements Serializable {

    @SerializedName("receiptId")
    private int receiptId;

    @SerializedName("title")
    private String title;

    @SerializedName("description")
    private String description;

    @SerializedName("publishDate")
    private String publishDate;

    @SerializedName("paymentDate")
    private String paymentDate;

    @SerializedName("createdDate")
    private String createdDate;

    @SerializedName("lastModifiedDate")
    private String lastModifiedDate;

    @SerializedName("status")
    private int status;

    @SerializedName("isBatch")
    private int isBatch;

    @SerializedName("balanceSheet")
    private long balanceSheet;

    @SerializedName("house")
    private HouseObject house;

    public int getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(int receiptId) {
        this.receiptId = receiptId;
    }

    public String getTitle() {
        return title;
    }

    public void setTittle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(String lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getIsBatch() {
        return isBatch;
    }

    public void setIsBatch(int isBatch) {
        this.isBatch = isBatch;
    }

    public long getBalanceSheet() {
        return balanceSheet;
    }

    public void setBalanceSheet(long balanceSheet) {
        this.balanceSheet = balanceSheet;
    }

    public HouseObject getHouse() {
        return house;
    }

    public void setHouse(HouseObject house) {
        this.house = house;
    }
}

