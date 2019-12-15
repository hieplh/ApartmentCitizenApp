package com.example.apartmentcitizen.home.transaction.receipt;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class BlockObject implements Serializable {

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
