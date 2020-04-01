package com.think2exam.projectt2e.modals;

public class CollegeListModel {

    public int id;
    public String name;
    public String location;
    public int CatId;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public int getCatId() {
        return CatId;
    }

    public CollegeListModel(int id, String name, String location, int catId) {
        this.id = id;
        this.name = name;
        this.location = location;
        CatId = catId;
    }
}
