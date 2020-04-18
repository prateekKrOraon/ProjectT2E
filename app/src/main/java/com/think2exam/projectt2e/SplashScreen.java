package com.think2exam.projectt2e;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.gson.Gson;
import com.think2exam.projectt2e.modals.UserModel;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        setContentView(R.layout.activity_splash_screen);


        Handler handler = new Handler();

        SharedPreferences per_pref = getSharedPreferences("MyPreference", 0); // 0 - for private mode
        SharedPreferences user_pref= getSharedPreferences("user",MODE_PRIVATE);
        int categoryId = per_pref.getInt("category_id",-1);

        Gson gson = new Gson();
        String json = user_pref.getString("user_details", null);
        UserModel userModel = gson.fromJson(json, UserModel.class);

        if(categoryId==-1)
        {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent;
                    intent = new Intent(getApplicationContext(),PersonalizeActivity.class);
                    startActivity(intent);
                    onBackPressed();

                }
            },3000);
        }
        else if(userModel!=null) //@prateek  add condition for USER
        {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent;
                    intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                    onBackPressed();

                }
            },3000);
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
            },3000);
        }
    }
}
