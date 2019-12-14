package com.example.apartmentcitizen.home.transaction;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class HouseObject implements Serializable {


    @SerializedName("houseId")
    private String id;

    @SerializedName("houseName")
    private String houseName;

    @SerializedName("ownerId")
    private String ownerId;

    @SerializedName("currentMoney")
    private String currentMoney;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHouseName() {
        return houseName;
    }

    public void setHouseName(String houseName) {
        this.houseName = houseName;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getCurrentMoney() {
        return currentMoney;
    }

    public void setCurrentMoney(String currentMoney) {
        this.currentMoney = currentMoney;
    }
}