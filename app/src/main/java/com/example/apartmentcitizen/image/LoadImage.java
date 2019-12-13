package com.example.apartmentcitizen.image;

import com.google.gson.annotations.SerializedName;

public class LoadImage {

    public static final String FOLDER_PATH = "src/main/resources/images";

    @SerializedName("path")
    private String[] path;

    public String[] getPath() {
        return path;
    }

    public void setPath(String[] path) {
        this.path = path;
    }

}
