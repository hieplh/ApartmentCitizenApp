package com.example.apartmentcitizen.home.transaction.receipt;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class HouseObject implements Serializable {

    @SerializedName("houseId")
    private int houseId;

    @SerializedName("block")
    private BlockObject block;

    @SerializedName("floor")
    private String floor;

    @SerializedName("houseName")
    private String houseName;

    @SerializedName("description")
    private String description;

    @SerializedName("area")
    private float area;

    @SerializedName("ownerId")
    private int ownerId;

    @SerializedName("profileImage")
    private String profileImage;

    @SerializedName("coverImage")
    private String coverImage;

    @SerializedName("displayMember")
    private boolean displayMember;

    @SerializedName("type")
    private TypeObject type;

    @SerializedName("status")
    private int status;

    @SerializedName("waterMeter")
    private int waterMeter;

    @SerializedName("currentMoney")
    private long currentMoney;

    public int getHouseId() {
        return houseId;
    }

    public void setHouseId(int houseId) {
        this.houseId = houseId;
    }

    public BlockObject getBlock() {
        return block;
    }

    public void setBlock(BlockObject block) {
        this.block = block;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getHouseName() {
        return houseName;
    }

    public void setHouseName(String houseName) {
        this.houseName = houseName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getArea() {
        return area;
    }

    public void setArea(float area) {
        this.area = area;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public boolean isDisplayMember() {
        return displayMember;
    }

    public void setDisplayMember(boolean displayMember) {
        this.displayMember = displayMember;
    }

    public TypeObject getType() {
        return type;
    }

    public void setType(TypeObject type) {
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getWaterMeter() {
        return waterMeter;
    }

    public void setWaterMeter(int waterMeter) {
        this.waterMeter = waterMeter;
    }

    public long getCurrentMoney() {
        return currentMoney;
    }

    public void setCurrentMoney(long currentMoney) {
        this.currentMoney = currentMoney;
    }
}
