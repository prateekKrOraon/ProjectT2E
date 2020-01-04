package com.think2exam.projectt2e.modals;

public class FeaturedCollegeModel {

    public int logo;
    public String name;
    public String rank;
    public String location;

    public FeaturedCollegeModel(int logo, String name, String rank, String location) {
        this.logo = logo;
        this.name = name;
        this.rank = rank;
        this.location = location;
    }

    public int getLogo() {
        return logo;
    }

    public String getName() {
        return name;
    }

    public String getRank() {
        return rank;
    }

    public String getLocation() {
        return location;
    }
}