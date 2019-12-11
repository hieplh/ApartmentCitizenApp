package com.example.apartmentcitizen.home.account;

import java.io.Serializable;

public class AccountObject implements Serializable {
    private int title;
    private int desc;
    private int imgPath;

    public AccountObject(int imgPath, int title, int desc) {
        this.imgPath = imgPath;
        this.title = title;
        this.desc = desc;
    }

    public int getImgPath() {
        return imgPath;
    }

    public void setImgPath(int imgPath) {
        this.imgPath = imgPath;
    }

    public int getTitle() {
        return title;
    }

    public void setTitle(int title) {
        this.title = title;
    }

    public int getDesc() {
        return desc;
    }

    public void setDesc(int desc) {
        this.desc = desc;
    }
}
