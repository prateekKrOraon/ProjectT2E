package com.think2exam.projectt2e.modals;

public class CollegeInfoModel {

    int id;
    String clgName,clgLocation,clgDist,clgState,clgCountry,clgPin,clgType;
    String rank;
    String clgDesc,clgYOE;
    String clgUrl;

    public CollegeInfoModel(int id, String clgName, String clgLocation, String clgDist, String clgState, String clgCountry, String clgPin, String clgType, String rank, String clgDesc, String clgYOE, String clgUrl) {
        this.id = id;
        this.clgName = clgName;
        this.clgLocation = clgLocation;
        this.clgDist = clgDist;
        this.clgState = clgState;
        this.clgCountry = clgCountry;
        this.clgPin = clgPin;
        this.clgType = clgType;
        this.rank = rank;
        this.clgDesc = clgDesc;
        this.clgYOE = clgYOE;
        this.clgUrl = clgUrl;
    }

    public int getId() {
        return id;
    }

    public String getClgName() {
        return clgName;
    }

    public String getClgLocation() {
        return clgLocation;
    }

    public String getClgDist() {
        return clgDist;
    }

    public String getClgState() {
        return clgState;
    }

    public String getClgCountry() {
        return clgCountry;
    }

    public String getClgPin() {
        return clgPin;
    }

    public String getClgType() {
        return clgType;
    }

    public String getRank() {
        return rank;
    }

    public String getClgDesc() {
        return clgDesc;
    }

    public String getClgYOE() {
        return clgYOE;
    }

    public String getClgUrl() {
        return clgUrl;
    }
}
