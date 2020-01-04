package com.think2exam.projectt2e.modals;

public class CategoryModel {

    private String name;
    private int icon;
    public CategoryModel(String name, int icon)
    {
        this.name = name;
        this.icon = icon;
    }

    public String getName(){return name;}
    public int getIcon(){return icon;}

}
