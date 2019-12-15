package com.example.apartmentcitizen.home.transaction.receipt.receiptdetail;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ServiceCatObject implements Serializable {

    @SerializedName("utilityServiceCategoryId")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("createdDate")
    private String createdDate;

    @SerializedName("lastModifiedDate")
    private String lastModified;

    @SerializedName("status")
    private int status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModified() {
        return lastModified;
    }

    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
