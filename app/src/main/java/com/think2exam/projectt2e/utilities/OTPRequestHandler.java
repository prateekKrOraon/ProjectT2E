package com.think2exam.projectt2e.utilities;

import com.think2exam.projectt2e.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

public class OTPRequestHandler {

    String otp="";
    public OTPRequestHandler(){}

    public String getOTP()
    {
        return otp;
    }
    public JSONObject sendRequest(String phone)
    {
        Random random = new Random();
        int first = random.nextInt(10);
        int second = random.nextInt(10);
        int third = random.nextInt(10);
        int fourth = random.nextInt(10);
        String otp = first+""+ second+""+ third +""+ fourth;
        this.otp = otp;
        String msg = otp+" is the OTP for your Think2Exam app login, valid for 5 mins.\n\nYou may reach us at Think2Exam.com for assistance";


        String apiKey = "apiKey="+ Constants.OTP_API_KEY;
        String message = "&message="+ msg;
        String sender = "&sender="+ "TXTLCL";
        String number = "&numbers="+phone;

        try {
            HttpURLConnection conn = (HttpURLConnection)new URL(Constants.OTP_API).openConnection();
            String data = apiKey + number + message + sender;
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(15000);
            conn.setReadTimeout(15000);
            conn.setRequestProperty("Content-Length",Integer.toString(data.length()));
            conn.getOutputStream().write(data.getBytes("UTF-8"));
            final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            final StringBuffer stringBuffer = new StringBuffer();
            String line;
            while ((line = rd.readLine()) != null)
            {
                stringBuffer.append(line);
            }
            rd.close();
            return convertToJSONObject(stringBuffer.toString());

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private JSONObject convertToJSONObject(String str)
    {
        JSONObject jsonObject = null;
        if(!str.isEmpty())
        {
            try {
                jsonObject = new JSONObject(str);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return jsonObject;
    }

}
