package com.think2exam.projectt2e.utilities;

import android.util.Log;

import com.think2exam.projectt2e.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;

import static com.think2exam.projectt2e.Constants.CORRECT_ANS;
import static com.think2exam.projectt2e.Constants.EMAIL_ID;
import static com.think2exam.projectt2e.Constants.FIRST_NAME;
import static com.think2exam.projectt2e.Constants.ID;
import static com.think2exam.projectt2e.Constants.IMAGE;
import static com.think2exam.projectt2e.Constants.LAST_NAME;
import static com.think2exam.projectt2e.Constants.LIMIT;
import static com.think2exam.projectt2e.Constants.NO_ANS;
import static com.think2exam.projectt2e.Constants.PASSWORD;
import static com.think2exam.projectt2e.Constants.PHONE_NO;
import static com.think2exam.projectt2e.Constants.QUERY;
import static com.think2exam.projectt2e.Constants.QUERY2;
import static com.think2exam.projectt2e.Constants.QUERY_TYPE;
import static com.think2exam.projectt2e.Constants.QUIZ_API_URL;
import static com.think2exam.projectt2e.Constants.QUIZ_CATEGORY_ID;
import static com.think2exam.projectt2e.Constants.QUIZ_PARA_ID;
import static com.think2exam.projectt2e.Constants.QUIZ_SUBJECT_ID;
import static com.think2exam.projectt2e.Constants.SEARCH_KEY;
import static com.think2exam.projectt2e.Constants.START_ID;
import static com.think2exam.projectt2e.Constants.TABLE_ID;
import static com.think2exam.projectt2e.Constants.TOTAL_MATCHES;
import static com.think2exam.projectt2e.Constants.TOTAL_POINTS;
import static com.think2exam.projectt2e.Constants.WINS;
import static com.think2exam.projectt2e.Constants.WRONG_ANS;

public class DBOperations {

    private static final String TAG = DBOperations.class.getSimpleName();
    private static DBOperations db = null;

    public static DBOperations getInstance(){
        if (db == null){
            db = new DBOperations();
        }

        return db;
    }

    private String setParameters(HashMap<String,String> params){
        StringBuilder sbParams = new StringBuilder();
        int i = 0;
        for (String key : params.keySet()){
            try {
                if (i != 0) {
                    sbParams.append("&");
                }
                sbParams.append(key).append("=")
                        .append(URLEncoder.encode(params.get(key), "UTF-8"));

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            i++;
        }

        return sbParams.toString();
    }

    private String execute(String urlStr,String params){

        final String REQUEST_METHOD = "POST";
        final int READ_TIMEOUT = 15000;
        final int CONNECTION_TIMEOUT = 15000;

        String result = "";
        String inputLine;

        HttpURLConnection connection = null;

        try {
            URL url = new URL(urlStr);

            connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod(REQUEST_METHOD);
            connection.setConnectTimeout(CONNECTION_TIMEOUT);
            connection.setReadTimeout(READ_TIMEOUT);

            connection.connect();

            if (!params.equals("")){
                DataOutputStream outStream = new DataOutputStream(connection.getOutputStream());
                outStream.writeBytes(params);
                outStream.flush();
                outStream.close();
            }


            InputStreamReader streamReader = new InputStreamReader(connection.getInputStream());
            BufferedReader reader = new BufferedReader(streamReader);
            StringBuilder stringBuilder = new StringBuilder();

            while ((inputLine = reader.readLine()) != null){
                stringBuilder.append(inputLine);
            }

            reader.close();
            streamReader.close();

            result = stringBuilder.toString();

        } catch (MalformedURLException e) {

            Log.e(TAG, "MalformedURLException: " + e.getMessage());

        } catch (ProtocolException e) {

            Log.e(TAG, "ProtocolException: " + e.getMessage());

        } catch (IOException e) {

            Log.e(TAG, "IOException: " + e.getMessage());

        } catch (Exception e) {

            Log.e(TAG, "Exception: " + e.getMessage());

        }finally {

            if (connection != null){
                connection.disconnect();
            }

        }

        return result;
    }

    private JSONArray convertToJSONArray(String str){
        JSONArray jsonArray = null;
        if(!str.isEmpty()){
            try {
                jsonArray = new JSONArray(str);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return jsonArray;
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
    public JSONArray getQuestions(String catId, String subId) {
        String url = QUIZ_API_URL + "getQuestions.php";

        HashMap<String,String> map = new HashMap<>();
        map.put(QUIZ_CATEGORY_ID,catId);
        map.put(QUIZ_SUBJECT_ID,subId);
        String paramStr = setParameters(map);
        String result = execute(url,paramStr);
        System.out.println(result);
        return convertToJSONArray(result);
    }

    public JSONArray getCategories(){
        String url = QUIZ_API_URL + "getCategories.php";
        String result = execute(url,"");
        return convertToJSONArray(result);
    }

    public JSONArray getSubjects(String catId){
        String url = QUIZ_API_URL + "getSubCategories.php";
        HashMap<String,String> map = new HashMap<>();
        map.put(QUIZ_CATEGORY_ID,catId);
        String paramStr = setParameters(map);
        String result = execute(url,paramStr);
        return convertToJSONArray(result);
    }

    public JSONObject getCollegeInfo(int id, int catId)
    {
        String url = QUIZ_API_URL + "getCollegeInfo.php";
        String tableId = "";

        switch (catId) {
            case R.string.engineering:
                tableId = "engineering_colleges";
                break;
            case R.string.agriculture:
                tableId = "agriculture_colleges";
                break;
            case R.string.management:
                tableId = "mba_colleges";
                break;
            case R.string.medical_and_dental:
                tableId = "medical_and_dental_colleges";
                break;
            case R.string.pharmacy:
                tableId = "pharmacy_colleges";
                break;
            case R.string.nursing_and_paramedical:
                tableId = "nursing_and_paramedical_colleges";
                break;
            case R.string.education:
                tableId = "education";
                break;
            default:
                tableId = "university";
                break;
        }

        HashMap<String,String> map = new HashMap<>();
        map.put(ID,""+id);
        map.put(TABLE_ID,tableId);
        String paramStr = setParameters(map);
        String result = execute(url,paramStr);
        System.out.println(" "+tableId+" "+id);
        System.out.println(result);
        return convertToJSONObject(result);

    }

    public JSONArray getColleges(String queryType,String query,String query2,int catId, String keyword,int startId){

        String tableId = "";

        switch (catId) {
            case R.string.engineering:
                tableId = "engineering_colleges";
                break;
            case R.string.agriculture:
                tableId = "agriculture_colleges";
                break;
            case R.string.management:
                tableId = "mba_colleges";
                break;
            case R.string.medical_and_dental:
                tableId = "medical_and_dental_colleges";
                break;
            case R.string.pharmacy:
                tableId = "pharmacy_colleges";
                break;
            case R.string.nursing_and_paramedical:
                tableId = "nursing_and_paramedical_colleges";
                break;
            case R.string.education:
                tableId = "education";
                break;
            default:
                tableId = "university";
                break;
        }

        System.out.println(queryType + " " + query+ " " +query2+ " " + tableId + " " + keyword+ " "+startId);

        String url = QUIZ_API_URL + "getColleges.php";
        HashMap<String,String> map = new HashMap<>();
        map.put(QUERY_TYPE,queryType);
        map.put(QUERY,query);
        map.put(QUERY2,query2);
        map.put(TABLE_ID,tableId);
        map.put(SEARCH_KEY,keyword);
        map.put(START_ID,""+startId);
        String paramStr = setParameters(map);
        String result = execute(url,paramStr);
        System.out.println(result);
        return convertToJSONArray(result);
    }



    public JSONObject createUser(String mobile,String Fname,String Lname,String email,String image,String password)
    {
        String url = QUIZ_API_URL + "createUser.php";
        HashMap<String,String> map = new HashMap<>();
        map.put(PHONE_NO,mobile);
        map.put(FIRST_NAME,Fname);
        map.put(LAST_NAME,Lname);
        map.put(EMAIL_ID,email);
        map.put(IMAGE,image);
        map.put(PASSWORD,password);

        String paramStr = setParameters(map);
        String result = execute(url,paramStr);
        System.out.println(result);
        return convertToJSONObject(result);
    }

    public JSONObject checkUser(String mobile)
    {
        String url = QUIZ_API_URL + "checkUser.php";
        HashMap<String,String> map = new HashMap<>();
        map.put(PHONE_NO,mobile);
        String paramStr = setParameters(map);
        String result = execute(url,paramStr);
        System.out.println(result);

        return convertToJSONObject(result);
    }

    public JSONObject getUserDetails(String mobile,String password)
    {
        String url = QUIZ_API_URL + "getUserDetails.php";
        HashMap<String,String> map = new HashMap<>();
        map.put(PHONE_NO,mobile);
        map.put(PASSWORD,password);
        String paramStr = setParameters(map);
        String result = execute(url,paramStr);
        System.out.println(result);
        return convertToJSONObject(result);
    }

    public JSONObject updatePassword(String mobile,String password)
    {
        String url = QUIZ_API_URL + "updatePassword.php";
        HashMap<String,String> map = new HashMap<>();
        map.put(PHONE_NO,mobile);
        map.put(PASSWORD,password);
        String paramStr = setParameters(map);
        String result = execute(url,paramStr);
        System.out.println(result);
        return convertToJSONObject(result);
    }

    public JSONObject updateProfile(String fname,String lname,String email,String phoneNo)
    {
        String url = QUIZ_API_URL + "updateProfile.php";
        HashMap<String,String> map = new HashMap<>();
        map.put(FIRST_NAME,fname);
        map.put(LAST_NAME,lname);
        map.put(EMAIL_ID,email);
        map.put(PHONE_NO,phoneNo);
        String paramStr = setParameters(map);
        String result = execute(url,paramStr);
        System.out.println(result);
        return convertToJSONObject(result);
    }

    public JSONObject sendQuizResult(){

        String url = QUIZ_API_URL + "updateResult.php";
        User user = User.getInstance();
        HashMap<String,String> map = new HashMap<>();
        map.put(PHONE_NO,user.phoneNo);
        map.put(TOTAL_MATCHES,user.totalMatches);
        map.put(TOTAL_POINTS,user.totalPoints);
        map.put(WINS,user.wins);
        map.put(CORRECT_ANS,user.correctAns);
        map.put(WRONG_ANS,user.wrongAns);
        map.put(NO_ANS,user.noAns);
        String paramStr = setParameters(map);
        String result = execute(url,paramStr);
        System.out.println(result);
        return convertToJSONObject(result);
    }



}
