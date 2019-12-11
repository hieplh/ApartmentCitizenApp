package com.example.apartmentcitizen.login;

import com.google.gson.annotations.SerializedName;

public class BlockLogin {
    @SerializedName("blockId")
    private int blockId;

    @SerializedName("blockName")
    private String blockName;

    public int getBlockId() {
        return blockId;
    }

    public void setBlockId(int blockId) {
        this.blockId = blockId;
    }

    public String getBlockName() {
        return blockName;
    }

    public void setBlockName(String blockName) {
        this.blockName = blockName;
    }
}
