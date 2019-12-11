package com.example.apartmentcitizen.register;

import com.google.gson.annotations.SerializedName;

public class HouseRegister {
    @SerializedName("houseId")
    private int houseId;

    public HouseRegister(int houseId) {
        this.houseId = houseId;
    }

    public int getHouseId() {
        return houseId;
    }

    public void setHouseId(int houseId) {
        this.houseId = houseId;
    }
}
