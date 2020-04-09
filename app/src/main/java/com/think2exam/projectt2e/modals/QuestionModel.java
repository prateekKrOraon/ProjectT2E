package com.think2exam.projectt2e.modals;

public class QuestionModel {

    public String question;
    public String option1;
    public String option2;
    public String option3;
    public String option4;
    public String option5;
    public int answerKey;
    public int paraId;
    public String paragraph;
    public String description;

    public QuestionModel(String question, String option1, String option2, String option3, String option4, String option5, int answerKey, String description,int paraId,String paragraph) {
        this.question = question;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.option5 = option5;
        this.answerKey = answerKey;
        this.description = description;
        this.paraId = paraId;
        this.paragraph = paragraph;
    }

}
