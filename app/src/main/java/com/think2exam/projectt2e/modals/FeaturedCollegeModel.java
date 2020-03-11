package com.think2exam.projectt2e.modals;

public class FeaturedCollegeModel {

    public int logo;
    public int id;
    public String name;
    public String rank;
    public String location;

    public int getLogo() {
        return logo;
    }

    public int getId() {
        return id;
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

    public FeaturedCollegeModel(int logo, int id, String name, String rank, String location) {
        this.logo = logo;
        this.id = id;
        this.name = name;
        this.rank = rank;
        this.location = location;
    }
}