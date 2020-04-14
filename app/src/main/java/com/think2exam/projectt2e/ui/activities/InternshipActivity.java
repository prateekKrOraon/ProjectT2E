package com.think2exam.projectt2e.ui.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
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
    private AppCompatEditText clgNameEdit;
    private AppCompatButton applyBtn;
    private ProgressDialog progressDialog;
    private User user = User.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internship);
        Toolbar toolbar = findViewById(R.id.internship_toolbar);
        setSupportActionBar(toolbar);
        try {
            getSupportActionBar().setTitle(getString(R.string.apply_internship));
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }catch (Exception e){
            e.printStackTrace();
        }

        firstNameEdit = findViewById(R.id.internship_first_name_edit);
        lastNameEdit = findViewById(R.id.internship_last_name_edit);
        phoneEdit = findViewById(R.id.internship_phone_no_edit);
        emailEdit = findViewById(R.id.internship_email_edit);
        clgNameEdit = findViewById(R.id.internship_college_name_edit);
        applyBtn = findViewById(R.id.internship_apply_btn);

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
                String clgName = "";
                try {
                    firstName = firstNameEdit.getText().toString();
                    lastName = lastNameEdit.getText().toString();
                    phoneNo = phoneEdit.getText().toString();
                    email = emailEdit.getText().toString();
                    clgName = clgNameEdit.getText().toString();
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

                    //TODO: attach link to url in DBOperations.applyForInternship()
                    ApplyInternship apply = new ApplyInternship();
                    apply.execute(firstName,lastName,phoneNo,email,clgName);
                    setProgressDialog();
                }
            }
        });

    }

    private void setProgressDialog(){
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Apply for Internship");
        progressDialog.setMessage("Requesting...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }


    private class ApplyInternship extends AsyncTask<String,Void,Void>{
        JSONObject object;
        @Override
        protected Void doInBackground(String... strings) {
            DBOperations dbOperations = DBOperations.getInstance();
            object = dbOperations.applyForInternship(strings[0]+" "+strings[1],strings[2],strings[3],strings[4]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.dismiss();
            try {
                if(object!=null && !object.getBoolean(ERROR)){
                    Toast.makeText(getApplicationContext(),object.getString(MESSAGE),Toast.LENGTH_LONG).show();
                    finish();
                }else if(object!=null && object.getBoolean(ERROR)) {
                    Toast.makeText(getApplicationContext(),object.getString(MESSAGE),Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(InternshipActivity.this, "OOPS! Some Error Occurred, may be your Internet connection down", Toast.LENGTH_SHORT).show();
                }
            }catch (JSONException ex){
                ex.printStackTrace();
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();

    }
}
