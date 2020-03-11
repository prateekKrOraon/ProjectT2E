package com.think2exam.projectt2e.modals;

public class CollegeListModel {

    public int id;
    public String name;
    public String location;
    public String tableName;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public String getTableName() {
        return tableName;
    }

    public CollegeListModel(int id, String name, String location, String tableName) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.tableName = tableName;
    }
}
