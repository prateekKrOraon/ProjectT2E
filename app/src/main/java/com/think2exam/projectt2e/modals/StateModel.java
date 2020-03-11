package com.think2exam.projectt2e.modals;

public class StateModel {

    private int name;
    private int icon;
    public StateModel(int name, int icon)
    {
        this.name = name;
        this.icon = icon;
    }

    public int getName(){return name;}
    public int getIcon(){return icon;}

}