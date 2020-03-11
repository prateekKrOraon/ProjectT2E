package com.think2exam.projectt2e.modals;

public class CityModel {

    private int name;
    private int icon;
    public CityModel(int name, int icon)
    {
        this.name = name;
        this.icon = icon;
    }

    public int getName(){return name;}
    public int getIcon(){return icon;}

}