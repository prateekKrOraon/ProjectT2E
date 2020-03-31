package com.think2exam.projectt2e;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.think2exam.projectt2e.utilities.DBOperations;
import com.think2exam.projectt2e.utilities.User;

import org.json.JSONException;
import org.json.JSONObject;

import static com.think2exam.projectt2e.Constants.*;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        Handler handler = new Handler();

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPreference", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        int catId = pref.getInt("category_id",-1);
        String mobileNo = pref.getString(PHONE_NO,"null");
        if(catId==-1)
        {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent;
                    intent = new Intent(getApplicationContext(),PersonalizeActivity.class);
                    startActivity(intent);
                    onBackPressed();

                }
            },2000);
        }
        else if(!mobileNo.equals("null") && catId != -1) //@prateek  add condition for USER
        {
            ProgressBar progressBar = findViewById(R.id.splash_progress_bar);
            progressBar.setVisibility(View.VISIBLE);
            TextView textView = findViewById(R.id.splash_text);
            textView.setVisibility(View.VISIBLE);

            GetUser getUser = new GetUser();
            getUser.execute(mobileNo);
        } else {


//            ProgressBar progressBar = findViewById(R.id.splash_progress_bar);
//            progressBar.setVisibility(View.VISIBLE);
//            TextView textView = findViewById(R.id.splash_text);
//            textView.setVisibility(View.VISIBLE);
//
//            editor.putString(PHONE_NO,"9931905946");
//            mobileNo = "9931905946";
//            editor.apply();
//            GetUser getUser = new GetUser();
//            getUser.execute(mobileNo);
//
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent;
                    intent = new Intent(getApplicationContext(),LogInActivity.class);
                    startActivity(intent);
                    onBackPressed();

                }
            },5000);
        }

    }

    private class GetUser extends AsyncTask<String,Void,Void>{

        @Override
        protected Void doInBackground(String... strings) {

            DBOperations dbOperations = DBOperations.getInstance();
            JSONObject jsonObject = dbOperations.getUserInfo(strings[0]);

            User user = User.getInstance();
            try {
                user.id = jsonObject.getInt(USER_ID);
                user.name = jsonObject.getString(FIRST_NAME) + " " + jsonObject.getString(LAST_NAME);
                user.phoneNo = jsonObject.getString(PHONE_NO);
                user.email = jsonObject.getString(EMAIL_ID);
                user.totalMatches = String.valueOf(jsonObject.getInt(TOTAL_MATCHES));
                user.totalPoints = String.valueOf(jsonObject.getInt(TOTAL_POINTS));
                user.wins = String.valueOf(jsonObject.getInt(WINS));
                user.correctAns = String.valueOf(jsonObject.getInt(CORRECT_ANS));
                user.wrongAns = String.valueOf(jsonObject.getInt(WRONG_ANS));
                int noAns = (jsonObject.getInt(TOTAL_MATCHES)*10)-jsonObject.getInt(CORRECT_ANS)-jsonObject.getInt(WRONG_ANS);
                user.noAns = String.valueOf(noAns);

                int avg = 0;
                if (jsonObject.getInt(TOTAL_MATCHES) != 0 ){
                    avg = jsonObject.getInt(TOTAL_POINTS)/jsonObject.getInt(TOTAL_MATCHES);
                }
                user.avgPoints = String.valueOf(avg);
            } catch (JSONException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }
    }
}
