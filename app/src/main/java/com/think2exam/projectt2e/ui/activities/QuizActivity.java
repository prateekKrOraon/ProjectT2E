package com.think2exam.projectt2e.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.think2exam.projectt2e.R;

public class QuizActivity extends AppCompatActivity {

    private int i;
    RelativeLayout layout;
    AppCompatTextView option;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        layout = findViewById(R.id.option_1_btn);
        option = findViewById(R.id.option_1);

        Toolbar toolbar = findViewById(R.id.toolbar_quiz);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Quiz");

        i=0;
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(i==0){
                    layout.setBackground(getResources().getDrawable(R.drawable.quiz_answer_correct));
                    option.setTextColor(getResources().getColor(R.color.answer_correct));
                }else{
                    v.setBackground(getResources().getDrawable(R.drawable.quiz_answer_wrong));
                    option.setTextColor(getResources().getColor(R.color.answer_wrong));
                    i = -1;
                }
                i++;
            }
        });
    }
}
