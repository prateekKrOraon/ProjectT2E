package com.think2exam.projectt2e.modals;

public class PrestigiousCollegeModel {

    private int name;
    private int icon;
    private int catId;

    public int getName() {
        return name;
    }

    public int getIcon() {
        return icon;
    }

    public int getCatId() {
        return catId;
    }

    public PrestigiousCollegeModel(int name, int icon, int catId) {
        this.name = name;
        this.icon = icon;
        this.catId = catId;
    }
}