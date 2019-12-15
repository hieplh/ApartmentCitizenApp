package com.example.apartmentcitizen.home.transaction.receipt;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TypeObject implements Serializable {

    @SerializedName("typeId")
    private int typeId;

    @SerializedName("name")
    private String name;

    @SerializedName("createDate")
    private String createDate;

    @SerializedName("lastModified")
    private String lastModified;

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getLastModified() {
        return lastModified;
    }

    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }
}
