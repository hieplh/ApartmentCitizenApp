package com.example.apartmentcitizen.home.transaction.receipt.receiptdetail;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ServiceObject implements Serializable {

   @SerializedName("utilityServiceId")
    private int id;

   @SerializedName("name")
    private String name;

   @SerializedName("type")
    private int type;

   @SerializedName("status")
    private int status;

   @SerializedName("serviceCategory")
    private ServiceCatObject cat;

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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public ServiceCatObject getCat() {
        return cat;
    }

    public void setCat(ServiceCatObject cat) {
        this.cat = cat;
    }
}
