package com.think2exam.projectt2e.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import com.think2exam.projectt2e.R;
import com.think2exam.projectt2e.utilities.DBOperations;

public class QuizResultsActivity extends AppCompatActivity {

    private AppCompatImageView image;
    private AppCompatTextView title;
    private AppCompatTextView subtitle;
    private AppCompatTextView scoreTextView;
    private AppCompatTextView scoreQuestions;
    private AppCompatButton backToTopics;

    private boolean fetch = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!fetch){
            setContentView(R.layout.loading);
            SendResult send = new SendResult();
            send.execute();
        }else{
            initialize();
        }

    }

    private void initialize() {

        setContentView(R.layout.activity_quiz_results);


        Intent intent = getIntent();
        int score = intent.getIntExtra("score",0);


        Toolbar toolbar = findViewById(R.id.quiz_result_toolbar);

        try {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Results");
        }catch (NullPointerException ex){
            ex.printStackTrace();
        }


        image = findViewById(R.id.result_image);
        title = findViewById(R.id.result_text_title);
        subtitle = findViewById(R.id.result_text_subtitle);
        scoreTextView = findViewById(R.id.result_score);
        backToTopics = findViewById(R.id.result_back_to_topic_btn);
        scoreQuestions = findViewById(R.id.result_question_score);

        setListeners();

        displayResult(score);
    }

    private void displayResult(int score) {
        if(score <= 50){
            setResults(
                    getResources().getDrawable(R.drawable.wrong_mark),
                    "Oh, dear...",
                    "You have failed this stage",
                    "+"+score+"XP",
                    (score/10)+"/10",
                    false
            );
        }else{
            setResults(
                    getResources().getDrawable(R.drawable.crown),
                    "Congratulations",
                    "You have passed this stage",
                    "+"+score+"XP",
                    (score/10)+"/10",
                    true
            );
        }
    }

    private void setResults(Drawable image,String title,String subtitle, String score,String questions,boolean win) {
        this.image.setImageDrawable(image);
        this.title.setText(title);
        this.subtitle.setText(subtitle);
        this.scoreTextView.setText(score);
        this.scoreQuestions.setText(questions);
        if(!win){
            this.image.setColorFilter(getResources().getColor(R.color.colorAccent));
        }
    }

    private void setListeners() {
        backToTopics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    finish();
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void finalize() throws Throwable {
        this.finish();
        super.finalize();
    }

    private class SendResult extends AsyncTask<String,Void,Void>{

        @Override
        protected Void doInBackground(String... strings) {

            DBOperations dbOperations = DBOperations.getInstance();
            dbOperations.sendQuizResult();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            initialize();
        }
    }
}
