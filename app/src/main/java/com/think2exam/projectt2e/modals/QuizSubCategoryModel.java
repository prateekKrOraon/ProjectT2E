package com.think2exam.projectt2e.modals;

public class QuizSubCategoryModel {

    public int id;
    public int catId;
    public String subCat;
    public int status;
    public int icon;

    public QuizSubCategoryModel(int id, int catId, String subCat, int status,int icon) {
        this.id = id;
        this.catId = catId;
        this.subCat = subCat;
        this.status = status;
        this.icon = icon;
    }
}
