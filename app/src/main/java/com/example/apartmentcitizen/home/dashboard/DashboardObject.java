package com.example.apartmentcitizen.home.dashboard;

import java.io.Serializable;

public class DashboardObject implements Serializable {
    private int banner;
    private int logo;
    private int title;
    private int desc;

    public DashboardObject() {
    }

    public DashboardObject(int banner, int logo, int title, int desc) {
        this.banner = banner;
        this.logo = logo;
        this.title = title;
        this.desc = desc;
    }

    public int getBanner() {
        return banner;
    }

    public void setBanner(int banner) {
        this.banner = banner;
    }

    public int getLogo() {
        return logo;
    }

    public void setLogo(int logo) {
        this.logo = logo;
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
