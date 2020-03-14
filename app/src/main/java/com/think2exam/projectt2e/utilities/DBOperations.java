package com.think2exam.projectt2e.utilities;

import android.util.Log;

import com.think2exam.projectt2e.R;
import com.think2exam.projectt2e.utility.ByCityQuery;
import com.think2exam.projectt2e.utility.ByStateQuery;
import com.think2exam.projectt2e.utility.CompleteTableQuery;
import com.think2exam.projectt2e.utility.HttpHandler;
import com.think2exam.projectt2e.utility.PrestigiousCollegeQuery;
import com.think2exam.projectt2e.utility.SearchQuery;

import org.json.JSONArray;
import org.json.JSONException;

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

import static com.think2exam.projectt2e.Constants.*;

public class DBOperations {

    private static final String TAG = HttpHandler.class.getSimpleName();
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

    public JSONArray getQuestions(String catId, String subId, String paraId) {
        String url = QUIZ_API_URL + "getQuestions.php";

        HashMap<String,String> map = new HashMap<>();
        map.put(QUIZ_CATEGORY_ID,catId);
        map.put(QUIZ_SUBJECT_ID,subId);
        map.put(QUIZ_PARA_ID,paraId);

        String paramStr = setParameters(map);
        String result = execute(url,paramStr);

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

    public JSONArray getColleges(String queryType,String query,int catId, String keyword){

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

        System.out.println(queryType + " " + query+ " " + tableId + " " + keyword);

        String url = QUIZ_API_URL + "getColleges.php";
        HashMap<String,String> map = new HashMap<>();
        map.put(QUERY_TYPE,queryType);
        map.put(QUERY,query);
        map.put(TABLE_ID,tableId);
        map.put(SEARCH_KEY,keyword);
        String paramStr = setParameters(map);
        String result = execute(url,paramStr);

        return convertToJSONArray(result);
    }

    public JSONArray getTopColleges(int catId,int limit) {
        String url = QUIZ_API_URL + "getTopColleges.php";

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


        HashMap<String, String> map = new HashMap<>();
        map.put(LIMIT, String.valueOf(limit));
        map.put(TABLE_ID, tableId);
        String paramStr = setParameters(map);
        String result = execute(url, paramStr);

        return convertToJSONArray(result);

    }

}
