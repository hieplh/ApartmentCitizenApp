package com.example.apartmentcitizen.home.transaction;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class HouseObject implements Serializable {


    @SerializedName("houseId")
    private int id;

    @SerializedName("houseName")
    private String houseName;

    @SerializedName("ownerId")
    private int ownerId;

    @SerializedName("currentMoney")
    private long currentMoney;

    public HouseObject(int id) {
        this.id = id;
    }

    public HouseObject(int id, String houseName, int ownerId, long currentMoney) {
        this.id = id;
        this.houseName = houseName;
        this.ownerId = ownerId;
        this.currentMoney = currentMoney;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHouseName() {
        return houseName;
    }

    public void setHouseName(String houseName) {
        this.houseName = houseName;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public long getCurrentMoney() {
        return currentMoney;
    }

    public void setCurrentMoney(long currentMoney) {
        this.currentMoney = currentMoney;
    }
}