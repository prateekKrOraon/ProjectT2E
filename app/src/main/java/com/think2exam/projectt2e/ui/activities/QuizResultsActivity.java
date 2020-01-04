package com.think2exam.projectt2e.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;

import com.think2exam.projectt2e.R;

public class QuizResultsActivity extends AppCompatActivity {

    private AppCompatImageView image;
    private AppCompatTextView title;
    private AppCompatTextView subtitle;
    private AppCompatTextView score;
    private AppCompatTextView scoreQuestions;
    private AppCompatButton backToTopics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_results);

        Toolbar toolbar = findViewById(R.id.quiz_result_toolbar);

        try {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Results");
        }catch (NullPointerException ex){
            ex.printStackTrace();
        }

        initialize();
        setListeners();

        Intent intent = getIntent();
        int score = intent.getIntExtra("score",0);

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
        this.score.setText(score);
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

    private void initialize() {
        image = findViewById(R.id.result_image);
        title = findViewById(R.id.result_text_title);
        subtitle = findViewById(R.id.result_text_subtitle);
        score = findViewById(R.id.result_score);
        backToTopics = findViewById(R.id.result_back_to_topic_btn);
        scoreQuestions = findViewById(R.id.result_question_score);
    }

    @Override
    protected void finalize() throws Throwable {
        this.finish();
        super.finalize();
    }
}
