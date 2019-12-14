package com.example.apartmentcitizen.home.transaction;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class FullnameObject implements Serializable {

    @SerializedName("firstName")
    private String lastName;

    @SerializedName("lastName")
    private String firstName;


    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public String toString() {
        return lastName + " " + firstName;
    }
}
