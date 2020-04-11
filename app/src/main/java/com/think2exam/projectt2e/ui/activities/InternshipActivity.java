package com.think2exam.projectt2e.ui.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.think2exam.projectt2e.R;
import com.think2exam.projectt2e.utilities.DBOperations;
import com.think2exam.projectt2e.utilities.User;

import org.json.JSONException;
import org.json.JSONObject;

import static com.think2exam.projectt2e.Constants.ERROR;
import static com.think2exam.projectt2e.Constants.MESSAGE;

public class InternshipActivity extends AppCompatActivity {

    boolean emptyField;
    private AppCompatEditText firstNameEdit;
    private AppCompatEditText lastNameEdit;
    private AppCompatEditText phoneEdit;
    private AppCompatEditText emailEdit;
    private AppCompatButton applyBtn;
    private ProgressBar progress;
    private User user = User.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internship);

        firstNameEdit = findViewById(R.id.internship_first_name_edit);
        lastNameEdit = findViewById(R.id.internship_last_name_edit);
        phoneEdit = findViewById(R.id.internship_phone_no_edit);
        emailEdit = findViewById(R.id.internship_email_edit);
        applyBtn = findViewById(R.id.internship_apply_btn);
        progress = findViewById(R.id.internship_progress_bar);

        firstNameEdit.setText(user.fname);
        lastNameEdit.setText(user.lname);
        phoneEdit.setText(user.phoneNo);
        emailEdit.setText(user.email);

        applyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = "";
                String lastName = "";
                String phoneNo = "";
                String email = "";
                try {
                    firstName = firstNameEdit.getText().toString();
                    lastName = lastNameEdit.getText().toString();
                    phoneNo = phoneEdit.getText().toString();
                    email = emailEdit.getText().toString();
                }catch (NullPointerException ex){
                    emptyField = true;
                    ex.printStackTrace();
                }

                if (emptyField){
                    AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
                    builder.setMessage("All fields are required").setTitle("Exit quiz?");
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();
                }else{
                    applyBtn.setVisibility(View.GONE);
                    progress.setVisibility(View.VISIBLE);
                    //TODO: attach link to url in DBOperations.applyForInternship()
                    //ApplyInternship apply = new ApplyInternship();
                    //apply.execute(firstName,lastName,phoneNo,email);
                }
            }
        });

    }

    private class ApplyInternship extends AsyncTask<String,Void,Void>{

        @Override
        protected Void doInBackground(String... strings) {
            DBOperations dbOperations = DBOperations.getInstance();
            JSONObject object = dbOperations.applyForInternship(strings[0]+strings[1],strings[2],strings[3]);
            try {
                if(object.getBoolean(ERROR)){
                    Toast.makeText(getApplicationContext(),object.getString(MESSAGE),Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getApplicationContext(),object.getString(MESSAGE),Toast.LENGTH_LONG).show();
                    finish();
                }
            }catch (JSONException ex){
                ex.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progress.setVisibility(View.GONE);
            progress.setVisibility(View.VISIBLE);
        }
    }
}
