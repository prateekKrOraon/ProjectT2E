package com.think2exam.projectt2e.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.think2exam.projectt2e.R;
import com.think2exam.projectt2e.modals.UserModel;
import com.think2exam.projectt2e.utilities.DBOperations;
import com.think2exam.projectt2e.utilities.User;

import org.json.JSONException;
import org.json.JSONObject;

import static com.think2exam.projectt2e.Constants.CORRECT_ANS;
import static com.think2exam.projectt2e.Constants.EMAIL_ID;
import static com.think2exam.projectt2e.Constants.ERROR;
import static com.think2exam.projectt2e.Constants.FIRST_NAME;
import static com.think2exam.projectt2e.Constants.ID;
import static com.think2exam.projectt2e.Constants.IMAGE;
import static com.think2exam.projectt2e.Constants.LAST_NAME;
import static com.think2exam.projectt2e.Constants.NO_ANS;
import static com.think2exam.projectt2e.Constants.PHONE_NO;
import static com.think2exam.projectt2e.Constants.TOTAL_MATCHES;
import static com.think2exam.projectt2e.Constants.TOTAL_POINTS;
import static com.think2exam.projectt2e.Constants.WINS;
import static com.think2exam.projectt2e.Constants.WRONG_ANS;

public class QuizResultsActivity extends AppCompatActivity {

    private AppCompatImageView image;
    private AppCompatTextView title;
    private AppCompatTextView subtitle;
    private AppCompatTextView scoreTextView;
    private AppCompatTextView scoreQuestions;
    private AppCompatButton backToTopics;
    private AppCompatButton tryAgain;

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
        tryAgain = findViewById(R.id.result_try_again);

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
        tryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.loading);
                SendResult send = new SendResult();
                send.execute();
            }
        });
    }

    @Override
    protected void finalize() throws Throwable {
        this.finish();
        super.finalize();
    }

    private class SendResult extends AsyncTask<String,Void,Void>{

        JSONObject jsonObject;
        @Override
        protected Void doInBackground(String... strings) {

            DBOperations dbOperations = DBOperations.getInstance();
            jsonObject = dbOperations.sendQuizResult();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            savePointsToSharedPref();
            try {
                initialize();

                if(jsonObject!=null && jsonObject.getBoolean(ERROR)){
                    Toast.makeText(QuizResultsActivity.this, "unable to send result to server", Toast.LENGTH_SHORT).show();
                    tryAgain.setVisibility(View.VISIBLE);
                    backToTopics.setVisibility(View.GONE);
                }else{
                    Toast.makeText(QuizResultsActivity.this, "sent", Toast.LENGTH_SHORT).show();
                    tryAgain.setVisibility(View.GONE);
                    backToTopics.setVisibility(View.VISIBLE);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public UserModel convertToUserModel() {
        UserModel userModel = null;
        User user = User.getInstance();
            userModel = new UserModel(user.id,
                    user.fname,
                    user.lname,
                    user.phoneNo,
                    user.email,
                    user.image,
                    Integer.parseInt(user.totalMatches),
                    Integer.parseInt(user.totalPoints),
                    Integer.parseInt(user.wins),
                    Integer.parseInt(user.correctAns),
                    Integer.parseInt(user.wrongAns),
                    Integer.parseInt(user.noAns));

        return userModel;
    }

    public void savePointsToSharedPref(){
        UserModel userModel;
        userModel = convertToUserModel();
        SharedPreferences Prefs= getSharedPreferences("user",MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = Prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(userModel);
        prefsEditor.putString("user_details", json);
        prefsEditor.apply();
    }

}
