package com.think2exam.projectt2e;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.sax.RootElement;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.JsonReader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.gson.Gson;
import com.mukesh.OnOtpCompletionListener;
import com.mukesh.OtpView;
import com.think2exam.projectt2e.modals.UserModel;
import com.think2exam.projectt2e.utilities.DBOperations;
import com.think2exam.projectt2e.utilities.LogInActivityHelper;
import com.think2exam.projectt2e.utilities.OTPRequestHandler;
import com.think2exam.projectt2e.utilities.User;


import org.json.JSONException;
import org.json.JSONObject;

import static com.think2exam.projectt2e.Constants.*;

public class LogInActivity extends AppCompatActivity {

    boolean isDefault=true;
    String enteredOTP="";
    String actualOTP="";
    String phoneNo="";

    //send otp layout && forgot password
    TextView userExist;
    MaterialButton nextBtn;
    EditText phoneEditText;

    //verify layout
    boolean isResend = false;

    //user profile && update Password
    MaterialButton continueBTN;
   //forgot Password


    //sign in
    MaterialButton signIn;
    boolean isHide=true;

    //for all
    LogInActivityHelper logInActivityHelper;
    RelativeLayout requesting = null;
    SendOTPhandler sendOTPhandler;
    SignInHandler signInHandler;
    UpdatePasswordHandler updatePasswordHandler;
    CreateUserHandler createUserHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setDefaultLayout();
        logInActivityHelper  = new LogInActivityHelper();

    }

    public void setDefaultLayout()
    {
        overridePendingTransition(R.anim.enter, R.anim.exit);
        setContentView(R.layout.activity_log_in);

        final LinearLayout skipBTN = findViewById(R.id.skip_btn);
        skipBTN.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                onBackPressed();

            }
        });

        MaterialCardView signUp = findViewById(R.id.sign_up_card_view);
        signUp.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                setSendOTPLayout();
                isDefault = false;
            }
        });

        MaterialCardView signIn = findViewById(R.id.sign_in_card_view);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSignInLayout();
                isDefault = false;
            }
        });
        TextView Terms = findViewById(R.id.terms);
        TextView Privacy = findViewById(R.id.privacy);
        Terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri gmmIntentUri = Uri.parse(TERMS);
                Intent urlIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                startActivity(urlIntent);
            }
        });
        Privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri gmmIntentUri = Uri.parse(PRIVACY);
                Intent urlIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                startActivity(urlIntent);
            }
        });
    }

    public void setSignInLayout()
    {
        LayoutInflater inflator=getLayoutInflater();
        View view=inflator.inflate(R.layout.log_in_sign_in_layout, null, false);
        view.startAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_in));
        setContentView(view);

        final EditText userPhone = findViewById(R.id.user_phone_edit_text);
        final EditText userPassword = findViewById(R.id.password_edit_text);
        userPassword.setInputType(InputType.TYPE_CLASS_TEXT |
                InputType.TYPE_TEXT_VARIATION_PASSWORD);
        TextView forgotPassword = findViewById(R.id.forgot_password);
        signIn = findViewById(R.id.login_btn);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = userPhone.getText().toString().trim();
                String pass = userPassword.getText().toString().trim();
                if(logInActivityHelper.isValidCredential(phone,pass)) {
                    signInHandler = new SignInHandler();
                    signInHandler.execute(phone,logInActivityHelper.md5(pass));
                }else{
                    Toast.makeText(LogInActivity.this, "enter valid details", Toast.LENGTH_SHORT).show();
                }
            }
        });
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setForgotPasswordLayout();
            }
        });

        final ImageView showHide = findViewById(R.id.show_hide_password);
        showHide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isHide){
                    showHide.setImageDrawable(getDrawable(R.drawable.ic_visibility_black_24dp));
                    userPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    isHide = false;
                }else {
                    showHide.setImageDrawable(getDrawable(R.drawable.ic_visibility_off_black_24dp));
                    userPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    isHide = true;
                }
            }
        });
       requesting = findViewById(R.id.request_layout);


    }


    public void setForgotPasswordLayout()
    {
        LayoutInflater inflator=getLayoutInflater();
        View view=inflator.inflate(R.layout.log_in_forgot_password_layout, null, false);
        view.startAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_in));
        setContentView(view);
        phoneEditText=findViewById(R.id.phone_no_edit_text);
        phoneEditText.setFocusableInTouchMode(true);
        nextBtn = findViewById(R.id.next_btn);
        userExist = findViewById(R.id.user_exist);

        phoneEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().length()==10)
                {
                    enableButton(nextBtn);
                }
                else
                {
                    disableButton(nextBtn);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        disableButton(nextBtn);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneNo = phoneEditText.getText().toString().trim();
                System.out.println(phoneNo);
                sendOTPhandler = new SendOTPhandler();
                sendOTPhandler.execute(true);
                //  setUserProfileLayout();

            }
        });

        requesting = findViewById(R.id.request_layout);

    }

    public void setUpdatePasswordLayout()
    {
        LayoutInflater inflator=getLayoutInflater();
        View view=inflator.inflate(R.layout.log_in_set_new_password_layout, null, false);
        view.startAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_in));
        setContentView(view);

        final EditText password1 = findViewById(R.id.password_edit_text_1);
        final EditText password2 = findViewById(R.id.password_edit_text_2);
        final LinearLayout password1Layout = findViewById(R.id.password_layout_1);
        final LinearLayout password2Layout = findViewById(R.id.password_layout_2);
        continueBTN = findViewById(R.id.continue_btn);
        final TextView passStatus = findViewById(R.id.password_status_text);
        continueBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pass1 = password1.getText().toString().trim();
                String pass2 = password2.getText().toString().trim();
                int flag = logInActivityHelper.checkPassword(pass1,pass2);
                passStatus.setVisibility(View.VISIBLE);

                if(flag==0) {
                    password1Layout.setBackground(getDrawable(R.drawable.mismatched_password_background));
                    passStatus.setText("* password too short");
                    passStatus.setTextColor(getResources().getColor(R.color.red));

                }else if(flag == 1){
                    password1Layout.setBackground(getDrawable(R.drawable.mismatched_password_background));
                    password2Layout.setBackground(getDrawable(R.drawable.mismatched_password_background));
                    passStatus.setText("* password mismatched");
                    passStatus.setTextColor(getResources().getColor(R.color.red));

                }else if(flag == 2) {
                    passStatus.setText("password matched \u2713");
                    passStatus.setTextColor(getResources().getColor(R.color.green));
                    password1Layout.setBackground(getDrawable(R.drawable.matched_password_background));
                    password2Layout.setBackground(getDrawable(R.drawable.matched_password_background));
                    String encrypted_pass = logInActivityHelper.md5(pass1);
                    if (encrypted_pass != null) {
                        updatePasswordHandler = new UpdatePasswordHandler();
                        updatePasswordHandler.execute(phoneNo,encrypted_pass);
                    }
                }
            }
        });


        requesting = findViewById(R.id.request_layout);
    }


    public void setSendOTPLayout()
    {
        LayoutInflater inflator=getLayoutInflater();
        View view=inflator.inflate(R.layout.log_in_send_otp_layout, null, false);
        view.startAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_in));
        setContentView(view);

        phoneEditText=findViewById(R.id.phone_no_edit_text);
        phoneEditText.setFocusableInTouchMode(true);
        nextBtn = findViewById(R.id.next_btn);
        userExist = findViewById(R.id.user_exist);

        phoneEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().length()==10)
                {
                    enableButton(nextBtn);
                }
                else
                {
                    disableButton(nextBtn);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        disableButton(nextBtn);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneNo = phoneEditText.getText().toString().trim();
                System.out.println(phoneNo);
                sendOTPhandler = new SendOTPhandler();
                sendOTPhandler.execute(false);
              //  setUserProfileLayout();

            }
        });

        requesting = findViewById(R.id.request_layout);

    }

    public void setUserProfileLayout()
    {
        LayoutInflater inflator=getLayoutInflater();
        View view=inflator.inflate(R.layout.log_in_set_profile_layout, null, false);
        view.startAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_in));
        setContentView(view);

        final EditText fname = (EditText) findViewById(R.id.fname_edit_text);
        final EditText lname = findViewById(R.id.lname_edit_text);
        final EditText email = findViewById(R.id.email_edit_text);
        final EditText password1 = findViewById(R.id.password_edit_text_1);
        final EditText password2 = findViewById(R.id.password_edit_text_2);
        final LinearLayout password1Layout = findViewById(R.id.password_layout_1);
        final LinearLayout password2Layout = findViewById(R.id.password_layout_2);
        continueBTN = findViewById(R.id.continue_btn);
        final TextView mandetary = findViewById(R.id.mandetary_text);
        final TextView passStatus = findViewById(R.id.password_status_text);

        continueBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(logInActivityHelper.isValid(fname,lname,password1,password2))
                {
                    String pass1 = password1.getText().toString().trim();
                    String pass2 = password2.getText().toString().trim();
                    int flag = logInActivityHelper.checkPassword(pass1,pass2);
                    passStatus.setVisibility(View.VISIBLE);
                    mandetary.setVisibility(View.GONE);

                    if(flag==0) {
                       password1Layout.setBackground(getDrawable(R.drawable.mismatched_password_background));
                       passStatus.setText("* password too short");
                       passStatus.setTextColor(getResources().getColor(R.color.red));

                    }else if(flag == 1){
                        password1Layout.setBackground(getDrawable(R.drawable.mismatched_password_background));
                        password2Layout.setBackground(getDrawable(R.drawable.mismatched_password_background));
                        passStatus.setText("* password mismatched");
                        passStatus.setTextColor(getResources().getColor(R.color.red));

                    }else if(flag == 2) {
                        passStatus.setText("password matched \u2713");
                        passStatus.setTextColor(getResources().getColor(R.color.green));
                        password1Layout.setBackground(getDrawable(R.drawable.matched_password_background));
                        password2Layout.setBackground(getDrawable(R.drawable.matched_password_background));
                        String encrypted_pass = logInActivityHelper.md5(pass1);
                        if (encrypted_pass != null) {
                            createUserHandler = new CreateUserHandler();
                            createUserHandler.execute(phoneNo, fname.getText().toString().trim(), lname.getText().toString().trim(), email.getText().toString().trim(), "",encrypted_pass);
                        }
                    }
                }
                else
                {
                    mandetary.setVisibility(View.VISIBLE);
                }
            }
        });
        requesting = findViewById(R.id.request_layout);
    }



    public void setVerifyOTPLayout(final boolean isForgotPassword)
    {
        LayoutInflater inflator=getLayoutInflater();
        View view=inflator.inflate(R.layout.log_in_verify_otp_layout, null, false);
        view.startAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_in));
        setContentView(view);

        TextView phone_no = findViewById(R.id.phone_no_text);
        phone_no.append(phoneNo);
        final MaterialButton verifyBtn = findViewById(R.id.verify_btn);
        final TextView status = findViewById(R.id.status);
        disableButton(verifyBtn);

        final OtpView otpView;
        otpView = findViewById(R.id.otp_view);
        otpView.setOtpCompletionListener(new OnOtpCompletionListener() {
            @Override public void onOtpCompleted(String ottp) {
                enableButton(verifyBtn);
                enteredOTP = ottp;

            }
        });

        verifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(enteredOTP.equals(actualOTP))
                {
                    otpView.setLineColor(getResources().getColor(R.color.correct_otp_color));
                    status.setText("\u2713 Verified");
                    status.setTextColor(getResources().getColor(R.color.correct_otp_color));
                    verifyBtn.setVisibility(View.GONE);
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if(isForgotPassword){
                                setUpdatePasswordLayout();
                            }else{
                                setUserProfileLayout();
                            }

                        }
                    }, 2000);
                }
                else
                {
                    otpView.setLineColor(getResources().getColor(R.color.loginRed));
                    status.setText("OTP mismatched");
                    status.setTextColor(getResources().getColor(R.color.loginRed));


                }
            }
        });
        final TextView resend = findViewById(R.id.resend);
        TextView timer = findViewById(R.id.timer);
        if(!isResend){

            resend.setEnabled(false);
            resend.setClickable(false);
            resend.setAlpha(.5f);
            MyCountDownTimer myCountDownTimer = new MyCountDownTimer(60000,1000);
            myCountDownTimer.setRef(resend,timer);
            myCountDownTimer.start();

            resend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    resend.setVisibility(View.GONE);
                    isResend = true;
                    sendOTPhandler = new SendOTPhandler();
                    sendOTPhandler.execute();
                }
            });
        }else {
            resend.setVisibility(View.GONE);
            timer.setVisibility(View.GONE);
        }

        requesting = findViewById(R.id.request_layout);

    }

    public class MyCountDownTimer extends CountDownTimer {

        TextView resend,timer;
        public void setRef(TextView resend,TextView timer)
        {
            this.resend = resend;
            this.timer = timer;
        }

        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {

            int progress = (int) (millisUntilFinished/1000);
            timer.setText(" in "+progress+" sec");

        }

        @Override
        public void onFinish() {
            resend.setEnabled(true);
            resend.setClickable(true);
            resend.setAlpha(1f);
            timer.setVisibility(View.GONE);
        }
    }


    private class SignInHandler extends AsyncTask<String,Void,Void> {


        JSONObject checkUserRespond,signInRespond;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            requesting.setVisibility(View.VISIBLE);
            disableButton(signIn);
        }

        @Override
        protected Void doInBackground(String... strings) {

            DBOperations dbOperations = DBOperations.getInstance();
            checkUserRespond = dbOperations.checkUser(strings[0]);
            try {
                if(checkUserRespond!=null && !checkUserRespond.getBoolean(ERROR) && checkUserRespond.getBoolean(FLAG)){
                      signInRespond = dbOperations.getUserDetails(strings[0],strings[1]);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            requesting.setVisibility(View.GONE);
            enableButton(signIn);


            try {
                if(checkUserRespond!=null && !checkUserRespond.getBoolean(ERROR) && checkUserRespond.getBoolean(FLAG))
                {
                    if(signInRespond!=null && !signInRespond.getBoolean(ERROR) && signInRespond.getBoolean(FLAG)){
                        Toast.makeText(LogInActivity.this, "Successfully signed in", Toast.LENGTH_SHORT).show();
                        saveUserToSharedPref(signInRespond);
                        startActivity(new Intent(LogInActivity.this,MainActivity.class));
                        finish();

                    }else if(signInRespond!=null && !signInRespond.getBoolean(ERROR) && !signInRespond.getBoolean(FLAG)){
                        Toast.makeText(LogInActivity.this, "Incorrect Password", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(LogInActivity.this, "OOPS! Some Error Occurred, may be your Internet connection down", Toast.LENGTH_SHORT).show();
                    }
                }else if(checkUserRespond!=null && !checkUserRespond.getBoolean(ERROR) && !checkUserRespond.getBoolean(FLAG)){
                    Toast.makeText(LogInActivity.this, "* User doesn't exist", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(LogInActivity.this, "OOPS! Some Error Occurred, may be your Internet connection down", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    private class SendOTPhandler extends AsyncTask<Boolean, Void, Void> {

        JSONObject respond=null;
        OTPRequestHandler otpRequestHandler;
        JSONObject jsonObject;
        boolean isForgotPassword;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            requesting.setVisibility(View.VISIBLE);
            disableButton(nextBtn);


        }

        @Override
        protected Void doInBackground(Boolean... arg0) {
            // Making a request to url and getting response

            isForgotPassword = arg0[0]; //to decide after verification
            DBOperations dbOperations = DBOperations.getInstance();
            jsonObject = dbOperations.checkUser(phoneNo);
            try {
                if(jsonObject!=null && !jsonObject.getBoolean(ERROR) &&
                        ((!jsonObject.getBoolean(FLAG) && !isForgotPassword)||
                                (jsonObject.getBoolean(FLAG) && isForgotPassword))) //user doestn,t exist
                {
                        otpRequestHandler = new OTPRequestHandler();
                        respond = otpRequestHandler.sendRequest(phoneNo);

                }


            } catch (JSONException e) {
                e.printStackTrace();
            }



            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            System.out.println(respond);
            requesting.setVisibility(View.GONE);
            enableButton(nextBtn);

                try {
                    if(jsonObject!=null && !jsonObject.getBoolean(ERROR) && jsonObject.getBoolean(FLAG) && !isForgotPassword) //user already exist && it is forgot password layout
                    {
                        userExist.setText(getString(R.string.user_exist));
                        userExist.setVisibility(View.VISIBLE);
                    }else if(jsonObject!=null && !jsonObject.getBoolean(ERROR) && !jsonObject.getBoolean(FLAG) && isForgotPassword)//user doesn't exist && it is join now layout
                    {
                            userExist.setText(getString(R.string.user_not_exist));
                            userExist.setVisibility(View.VISIBLE);
                    }else if((jsonObject!=null && !jsonObject.getBoolean(ERROR) && jsonObject.getBoolean(FLAG) && isForgotPassword)
                            ||(jsonObject!=null && !jsonObject.getBoolean(ERROR) && !jsonObject.getBoolean(FLAG) && !isForgotPassword))
                    {
                        if (respond != null && respond.getString(STATUS).equals("success")) {
                            Toast.makeText(LogInActivity.this, "\u2713 Sent", Toast.LENGTH_SHORT).show();
                            setVerifyOTPLayout(isForgotPassword);
                            actualOTP = otpRequestHandler.getOTP();
                        }else if(respond != null && respond.getString(STATUS).equals("failure")){
                            Toast.makeText(LogInActivity.this, ""+respond.getJSONArray(ERRORS).getJSONObject(0).getString(MESSAGE), Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(LogInActivity.this, "OOPS! Some Error Occurred, may be your Internet connection down", Toast.LENGTH_SHORT).show();
                        }
                    } else{
                        Toast.makeText(LogInActivity.this, "OOPS! Some Error Occurred, may be your Internet connection down", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }




        }
    }

    private class CreateUserHandler extends AsyncTask<String, Void, Void>
    {

        JSONObject jsonArray;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            requesting.setVisibility(View.VISIBLE);
            disableButton(continueBTN);
        }

        @Override
        protected Void doInBackground(String... strings) {
            DBOperations dbOperations = DBOperations.getInstance();
            jsonArray = dbOperations.createUser(strings[0],strings[1],strings[2],strings[3],strings[4],strings[5]);

            try {
                if(jsonArray!=null && !jsonArray.getBoolean(ERROR))
                {
                    JSONObject jsonObject = dbOperations.getUserDetails(phoneNo,strings[5]);
                    saveUserToSharedPref(jsonObject);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            requesting.setVisibility(View.GONE);
            enableButton(continueBTN);
            try {
                if(jsonArray!=null && !jsonArray.getBoolean(ERROR))
                {
                    Toast.makeText(LogInActivity.this, "User Created Successfully \u2713", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    finish();
                }
                else
                {
                    Toast.makeText(LogInActivity.this, "OOPS! Some Error Occurred, may be your Internet connection down", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public class UpdatePasswordHandler extends AsyncTask<String,Void,Void>
    {
        JSONObject jsonObject;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            requesting.setVisibility(View.VISIBLE);
            disableButton(continueBTN);
        }

        @Override
        protected Void doInBackground(String... strings) {
            DBOperations dbOperations = DBOperations.getInstance();
            jsonObject = dbOperations.updatePassword(strings[0],strings[1]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            requesting.setVisibility(View.GONE);
            enableButton(continueBTN);
            try {
                if(jsonObject!=null && !jsonObject.getBoolean(ERROR))
                {
                    Toast.makeText(LogInActivity.this, ""+jsonObject.getString(MESSAGE), Toast.LENGTH_SHORT).show();
                    setSignInLayout();
                }
                else if(jsonObject!=null && jsonObject.getBoolean(ERROR)) {
                    Toast.makeText(LogInActivity.this, ""+jsonObject.getString(MESSAGE), Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(LogInActivity.this, "OOPS! Some Error Occurred, may be your Internet connection down", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    public UserModel convertToUserModel(JSONObject jsonObject) throws JSONException {
        UserModel userModel = null;
        if(jsonObject!=null)
        {
            userModel = new UserModel(jsonObject.getInt(ID),
                    jsonObject.getString(FIRST_NAME),
                    jsonObject.getString(LAST_NAME),
                    jsonObject.getString(PHONE_NO),
                    jsonObject.getString(EMAIL_ID),
                    jsonObject.getString(IMAGE),
                    jsonObject.getInt(TOTAL_MATCHES),
                    jsonObject.getInt(TOTAL_POINTS),
                    jsonObject.getInt(WINS),
                    jsonObject.getInt(CORRECT_ANS),
                    jsonObject.getInt(WRONG_ANS),
                    jsonObject.getInt(NO_ANS));
        }
        return userModel;
    }

    public void saveUserToSharedPref(JSONObject jsonObject) throws JSONException {
        UserModel userModel;
        userModel = convertToUserModel(jsonObject);
        SharedPreferences Prefs= getSharedPreferences("user",MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = Prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(userModel);
        prefsEditor.putString("user_details", json);
        prefsEditor.apply();
    }

    public void disableButton(MaterialButton button)
    {
        button.setClickable(false);
        button.setEnabled(false);
        button.setAlpha(0.5f);
    }
    public void enableButton(MaterialButton button)
    {
        button.setClickable(true);
        button.setEnabled(true);
        button.setAlpha(1f);
    }

    @Override
    public void onBackPressed() {

        cencelThread();
        if(!isDefault)
        {
            isDefault=true;
            setDefaultLayout();
        }
        else
            super.onBackPressed();
    }
    public void cencelThread()
    {
        if(sendOTPhandler!=null && !sendOTPhandler.isCancelled())
        {
            sendOTPhandler.cancel(true);

        }
        if(signInHandler!=null && !signInHandler.isCancelled())
        {
            signInHandler.cancel(true);

        }
        if(createUserHandler!=null && !createUserHandler.isCancelled())
        {
            createUserHandler.cancel(true);


        }
        if(updatePasswordHandler!=null && !updatePasswordHandler.isCancelled())
        {
            updatePasswordHandler.cancel(true);


        }


    }

}
