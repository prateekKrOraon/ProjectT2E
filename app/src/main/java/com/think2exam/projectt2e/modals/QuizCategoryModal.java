package com.think2exam.projectt2e.modals;

public class QuizCategoryModal {

    public String name;
    public int icon;
    public int id;
    public String description;

    public QuizCategoryModal(int id,String name,String description, int icon) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.icon = icon;
    }

}