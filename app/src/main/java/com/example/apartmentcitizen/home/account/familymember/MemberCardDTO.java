package com.example.apartmentcitizen.home.account.familymember;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class MemberCardDTO implements Serializable {

    @SerializedName("userId")
    public int id;
    @SerializedName("firstName")
    public String firstName;
    @SerializedName("lastName")
    public String lastName;
    @SerializedName("familyLevel")
    public String relation;
    @SerializedName("email")
    public String email;
    @SerializedName("phoneNo")
    public String phoneNo;
    @SerializedName("dateOfBirth")
    public String birthday;
    @SerializedName("profileImage")
    public String urlImg;

    public MemberCardDTO() {
    }

    public MemberCardDTO(int id, String firstName, String lastName, String relation, String email, String phoneNo, String birthday, String urlImg) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.relation = relation;
        this.email = email;
        this.phoneNo = phoneNo;
        this.birthday = birthday;
        this.urlImg = urlImg;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getUrlImg() {
        return urlImg;
    }

    public void setUrlImg(String urlImg) {
        this.urlImg = urlImg;
    }
}
