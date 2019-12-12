package com.example.apartmentcitizen.login;

import com.google.gson.annotations.SerializedName;

public class HouseLogin {
    @SerializedName("houseId")
    private int houseId;

    @SerializedName("houseName")
    private String houseName;

    @SerializedName("block")
    private BlockLogin block;

    @SerializedName("ownerId")
    private int ownerId;

    @SerializedName("currentMoney")
    private double currentMoney;

    public int getHouseId() {
        return houseId;
    }

    public void setHouseId(int houseId) {
        this.houseId = houseId;
    }

    public String getHouseName() {
        return houseName;
    }

    public void setHouseName(String houseName) {
        this.houseName = houseName;
    }

    public BlockLogin getBlock() {
        return block;
    }

    public void setBlock(BlockLogin block) {
        this.block = block;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public double   getCurrentMoney() {
        return currentMoney;
    }

    public void setCurrentMoney(double currentMoney) {
        this.currentMoney = currentMoney;
    }
}
