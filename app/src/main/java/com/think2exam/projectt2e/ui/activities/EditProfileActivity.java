package com.think2exam.projectt2e.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.think2exam.projectt2e.MainActivity;
import com.think2exam.projectt2e.R;
import com.think2exam.projectt2e.modals.UserModel;
import com.think2exam.projectt2e.utilities.DBOperations;
import com.think2exam.projectt2e.utilities.User;

import org.json.JSONException;
import org.json.JSONObject;

import static com.think2exam.projectt2e.Constants.CORRECT_ANS;
import static com.think2exam.projectt2e.Constants.EMAIL_ID;
import static com.think2exam.projectt2e.Constants.ERROR;
import static com.think2exam.projectt2e.Constants.ERRORS;
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

public class EditProfileActivity extends AppCompatActivity {

    AppCompatButton saveButton;
    String fname,lname,email;
    EditProfileHandler editProfileHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        try {
            getSupportActionBar().setTitle("Edit Profile");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }catch (Exception e){
            e.printStackTrace();
        }


        saveButton = findViewById(R.id.save_profile_changes);
        final AppCompatEditText fName = findViewById(R.id.edit_profile_fname);
        final AppCompatEditText lName = findViewById(R.id.edit_profile_lname);
        final AppCompatEditText Email = findViewById(R.id.edit_profile_email);
        fName.setText(getIntent().getStringExtra(FIRST_NAME));
        lName.setText(getIntent().getStringExtra(LAST_NAME));
        Email.setText(getIntent().getStringExtra(EMAIL_ID));
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 fname = fName.getText().toString().trim();
                 lname = lName.getText().toString().trim();
                 email = Email.getText().toString().trim();
                if(isValid(fname,lname,email)){
                       editProfileHandler = new EditProfileHandler();
                       editProfileHandler.execute();
                }else {
                    Toast.makeText(EditProfileActivity.this, "enter valid details", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public boolean isValid(String fname,String lname,String email){
        if(fname.length()!=0 && lname.length()!=0 && email.length()!=0 && email.contains("@gmail.com")){
            return true;
        }
        return false;
    }

    public class EditProfileHandler extends AsyncTask<Void,Void,Void>
    {

        RelativeLayout requesting;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            requesting = findViewById(R.id.request_layout);
            requesting.setVisibility(View.VISIBLE);
            saveButton.setEnabled(false);

        }

        JSONObject jsonObject;
        @Override
        protected Void doInBackground(Void... strings) {
            User user = User.getInstance();
            DBOperations dbOperations = DBOperations.getInstance();
            jsonObject = dbOperations.updateProfile(fname,lname,email,user.phoneNo);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            requesting.setVisibility(View.GONE);
            saveButton.setEnabled(true);
            super.onPostExecute(aVoid);
            try {
                if(jsonObject!=null && !jsonObject.getBoolean(ERROR)){
                    saveUserToSharedPref();
                    Toast.makeText(EditProfileActivity.this, "Updated successfully", Toast.LENGTH_SHORT).show();
                    MainActivity.getInstance().finish();
                    startActivity(new Intent(EditProfileActivity.this,MainActivity.class));
                    finish();
                }else{
                    Toast.makeText(EditProfileActivity.this, "Try again", Toast.LENGTH_SHORT).show();
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
                fname,
                lname,
                user.phoneNo,
                email,
                user.image,
                Integer.parseInt(user.totalMatches),
                Integer.parseInt(user.totalPoints),
                Integer.parseInt(user.wins),
                Integer.parseInt(user.correctAns),
                Integer.parseInt(user.wrongAns),
                Integer.parseInt(user.noAns));

        return userModel;
    }

    public void saveUserToSharedPref(){
        UserModel userModel;
        userModel = convertToUserModel();
        SharedPreferences Prefs= getSharedPreferences("user",MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = Prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(userModel);
        prefsEditor.putString("user_details", json);
        prefsEditor.apply();
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(editProfileHandler!=null)
        {
            editProfileHandler.cancel(true);
        }

    }
}
