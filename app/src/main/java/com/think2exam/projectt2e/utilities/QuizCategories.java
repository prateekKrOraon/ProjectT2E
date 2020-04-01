package com.think2exam.projectt2e.utilities;

import com.think2exam.projectt2e.modals.FeaturedCollegeModel;
import com.think2exam.projectt2e.modals.QuizCategoryModal;

import java.util.ArrayList;

public class QuizCategories {


    public static QuizCategories quizCategories;
    public ArrayList<QuizCategoryModal> quizCategoryModals = new ArrayList<>();
    private QuizCategories() {
    }

    public static QuizCategories getInstance() {
        if (quizCategories == null){
            quizCategories = new QuizCategories();
        }
        return quizCategories;
    }

    public void setQuizCategories(QuizCategoryModal quizCategoryModals) {
        this.quizCategoryModals.add(quizCategoryModals);
    }

    public ArrayList<QuizCategoryModal> getQuizCategories(){
        return quizCategoryModals;
    }

}
