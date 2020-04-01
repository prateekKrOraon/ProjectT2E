package com.think2exam.projectt2e.utilities;

import android.widget.EditText;

import com.think2exam.projectt2e.LogInActivity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;



public class LogInActivityHelper {

    int SHORT = 0;
    int MISMATCHED = 1;
    int MATCHED=2;
    public LogInActivityHelper(){}

    public String md5(String pass)
    {
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(pass.getBytes());
            byte[] bytes = md.digest();
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        System.out.println("Hashed password: "+generatedPassword);

        return generatedPassword;
    }

    public int  checkPassword(String pass1,String pass2)
    {
        int ANS=-1;
        if(pass1.length()>=6) {
            if(pass1.equals(pass2)){
                ANS = MATCHED;
            }else{
                ANS = MISMATCHED;
            }
        }else{
            ANS = SHORT;
        }
        return ANS;
    }
    public boolean isValid(EditText fname, EditText lname, EditText password1, EditText password2)
    {
        if(fname.getText().toString().trim().length()==0 || lname.getText().toString().trim().length()==0 || password1.getText().toString().trim().length()==0 || password2.getText().toString().trim().length()==0)
            return false;
        return true;
    }

    public boolean isValidCredential(String phone,String password)
    {
        if(phone.length()==10 && password.length()!=0)
        {
            return true;
        }
        return false;
    }
}
