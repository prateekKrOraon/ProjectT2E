package com.think2exam.projectt2e.utilities;

import com.think2exam.projectt2e.modals.QuestionModel;

import java.util.ArrayList;

public class Questions {

    private static Questions obj = null;
    private ArrayList<QuestionModel> questions;

    public Questions(){

    }

    public static Questions getInstance(){
        if (obj == null){
            obj = new Questions();
        }

        return obj;
    }

    public void setInstance(ArrayList<QuestionModel> list){
        obj.questions = list;
    }

    public ArrayList<QuestionModel> getQuestions(){
        return questions;
    }
}
