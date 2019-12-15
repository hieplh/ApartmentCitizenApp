package com.example.apartmentcitizen.home.transaction.receipt.receiptdetail;

import com.example.apartmentcitizen.home.transaction.receipt.ReceiptObject;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ReceiptDetailObject implements Serializable {

    @SerializedName("receiptDetailId")
    private int id;

    @SerializedName("total")
    private long total;

    @SerializedName("unitPrice")
    private long unitPrice;

    @SerializedName("quantity")
    private int quantity;

    @SerializedName("service")
    private ServiceObject service;

    @SerializedName("fromNumber")
    private long fromNum;

    @SerializedName("toNumber")
    private long toNum;

    @SerializedName("receipt")
    private ReceiptObject receipt;

    @SerializedName("createdDate")
    private String createdDate;

    @SerializedName("lastModifiedDate")
    private String lastModifiedDate;


    public long getFromNum() {
        return fromNum;
    }

    public void setFromNum(long fromNum) {
        this.fromNum = fromNum;
    }

    public long getToNum() {
        return toNum;
    }

    public void setToNum(long toNum) {
        this.toNum = toNum;
    }

    public ReceiptObject getReceipt() {
        return receipt;
    }

    public void setReceipt(ReceiptObject receipt) {
        this.receipt = receipt;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(long unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public ServiceObject getService() {
        return service;
    }

    public void setService(ServiceObject service) {
        this.service = service;
    }
}

