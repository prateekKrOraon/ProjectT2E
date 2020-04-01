package com.think2exam.projectt2e.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.ContentLoadingProgressBar;

import android.os.Bundle;

import com.think2exam.projectt2e.R;
import com.think2exam.projectt2e.utilities.User;

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

    private final User user = User.getInstance();

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
    private ContentLoadingProgressBar levelXpBar;

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

        level = Integer.parseInt(user.totalPoints)/500;
        if (level != 0){
            levelXP = Integer.parseInt(user.totalPoints)/level;
        }else{
            levelXP = Integer.parseInt(user.totalPoints);
        }
        nextLevelXP = 500;

        totalMatchesView.setText(user.totalMatches);
        totalWinsView.setText(user.wins);
        avgPointsView.setText(user.avgPoints);
        correctAnswersView.setText(user.correctAns);
        wrongAnswersView.setText(user.wrongAns);
        didNotAnswerView.setText(user.noAns);
        totalPointsView.setText(user.totalPoints);
        levelXPView.setText(levelXP+"XP");
        levelXpBar.setMax(500);
        levelXpBar.setProgress(levelXP);
        levelView.setText("Level " + level);
        nextLevelXPView.setText(nextLevelXP+"XP");

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
