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

    public PrestigiousCollegeModel(int name,int catId, int icon) {
        this.name = name;
        this.icon = icon;
        this.catId = catId;
    }

    public int getCatId(){
        return catId;
    }
}