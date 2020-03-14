package com.think2exam.projectt2e;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        Handler handler = new Handler();

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPreference", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        int catId = pref.getInt("category_id",-1);
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
        else if(false) //@prateek  add condition for USER
        {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent;
                    intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                    onBackPressed();

                }
            },5000);
        }
        else
        {
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
}
