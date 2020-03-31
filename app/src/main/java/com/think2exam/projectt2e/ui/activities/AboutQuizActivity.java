package com.think2exam.projectt2e.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.card.MaterialCardView;
import com.think2exam.projectt2e.R;
public class AboutQuizActivity extends AppCompatActivity {




    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_quiz);

        ImageView back_btn = findViewById(R.id.quiz_des_back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        RelativeLayout ptp = findViewById(R.id.proceed_play_card_view);
        ptp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),QuizCategoryActivity.class));
                Toast.makeText(AboutQuizActivity.this, "Quiz section", Toast.LENGTH_SHORT).show();
                finish();
            }
        });



    }
}
