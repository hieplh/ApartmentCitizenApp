package com.example.apartmentcitizen.home.account.information;

import com.example.apartmentcitizen.register.HouseRegister;
import com.example.apartmentcitizen.register.RoleRegister;
import com.google.gson.annotations.SerializedName;

public class Information {
    @SerializedName("email")
    private String email;

    @SerializedName("phoneNo")
    private String phoneNo;

    @SerializedName("role")
    private RoleRegister role;

    @SerializedName("house")
    private HouseRegister house;

    @SerializedName("dateOfBirth")
    private String dateOfBirth;

    @SerializedName("idNumber")
    private String cifNumber;

    @SerializedName("gender")
    private int gender;

    @SerializedName("job")
    private String job;

    @SerializedName("homeTown")
    private String HomeTown;

    @SerializedName("firstName")
    private String FirstName;

    @SerializedName("lastName")
    private String LastName;

    @SerializedName("createDate")
    private String createDate;

    @SerializedName("lastModified")
    private String lastModified;

    @SerializedName("familyLevel")
    private int familyLevel;

    @SerializedName("status")
    private int status;

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

    public RoleRegister getRole() {
        return role;
    }

    public void setRole(RoleRegister role) {
        this.role = role;
    }

    public HouseRegister getHouse() {
        return house;
    }

    public void setHouse(HouseRegister house) {
        this.house = house;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getCifNumber() {
        return cifNumber;
    }

    public void setCifNumber(String cifNumber) {
        this.cifNumber = cifNumber;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getHomeTown() {
        return HomeTown;
    }

    public void setHomeTown(String homeTown) {
        HomeTown = homeTown;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
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

    public int getFamilyLevel() {
        return familyLevel;
    }

    public void setFamilyLevel(int familyLevel) {
        this.familyLevel = familyLevel;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}
