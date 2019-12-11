package com.example.apartmentcitizen.register;

import com.google.gson.annotations.SerializedName;

public class RoleRegister {
    @SerializedName("id")
    private int roleId;

    public RoleRegister(int roleId) {
        this.roleId = roleId;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }
}
