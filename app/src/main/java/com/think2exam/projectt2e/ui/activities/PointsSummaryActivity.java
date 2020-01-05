package com.think2exam.projectt2e.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.ContentLoadingProgressBar;

import android.os.Bundle;

import com.think2exam.projectt2e.R;

public class PointsSummaryActivity extends AppCompatActivity {

    private AppCompatTextView levelView;
    private AppCompatTextView levelXPView;
    private AppCompatTextView nextLevelXPView;
    private AppCompatTextView totalMatchesView;
    private AppCompatTextView totalWinsView;
    private AppCompatTextView totalPointsView;
    private AppCompatTextView avgPointsView;
    private AppCompatTextView correctAnswersView;
    private AppCompatTextView wrongAnswersView;
    private AppCompatTextView didNotAnswerView;

    ContentLoadingProgressBar levelXpBar;

    private int level;
    private int levelXP;
    private int nextLevelXP;
    private int totalMatches;
    private int totalWins;
    private int totalPoints;
    private float avgPoints;
    private int correctAnswers;
    private int wrongsAnswers;
    private int didNotAnswers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_points_summary);

        Toolbar toolbar = findViewById(R.id.toolbar_summary);
        setSupportActionBar(toolbar);
        try {
            getSupportActionBar().setTitle("Points Summary");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }catch (Exception e){
            e.printStackTrace();
        }



        initialize();

        levelXpBar.setMax(1000);
        levelXpBar.setProgress(240);

    }

    private void initialize() {

        levelView = findViewById(R.id.summary_level);
        levelXPView = findViewById(R.id.level_current_points);
        nextLevelXPView = findViewById(R.id.level_max_points);
        totalMatchesView = findViewById(R.id.summary_total_matches);
        totalWinsView = findViewById(R.id.summary_total_wins);
        totalPointsView = findViewById(R.id.summary_total_points);
        avgPointsView = findViewById(R.id.summary_avg_points);
        correctAnswersView = findViewById(R.id.summary_correct_answers);
        wrongAnswersView = findViewById(R.id.summary_wrong_answers);
        didNotAnswerView = findViewById(R.id.summary_did_not_answer);

        levelXpBar = findViewById(R.id.summary_progress);

        level = 0;
        levelXP = 0;
        nextLevelXP = 0;
        totalPoints = 0;
        totalWins = 0;
        totalMatches = 0;
        avgPoints = 0;
        correctAnswers = 0;
        wrongsAnswers = 0;
        didNotAnswers = 0;

    }
}
