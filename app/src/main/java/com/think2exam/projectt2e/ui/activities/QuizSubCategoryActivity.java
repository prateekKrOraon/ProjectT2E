package com.think2exam.projectt2e.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.think2exam.projectt2e.R;
import com.think2exam.projectt2e.adapters.QuizCatAdapter;
import com.think2exam.projectt2e.adapters.QuizSubCategoryAdapter;
import com.think2exam.projectt2e.modals.QuizCategoryModal;
import com.think2exam.projectt2e.modals.QuizSubCategoryModel;
import com.think2exam.projectt2e.utilities.DBOperations;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.think2exam.projectt2e.Constants.ID;
import static com.think2exam.projectt2e.Constants.QUIZ_CATEGORY;
import static com.think2exam.projectt2e.Constants.QUIZ_CATEGORY_DES;
import static com.think2exam.projectt2e.Constants.QUIZ_CATEGORY_ID;
import static com.think2exam.projectt2e.Constants.QUIZ_SUBJECT_ID;
import static com.think2exam.projectt2e.Constants.QUIZ_SUB_CAT;
import static com.think2exam.projectt2e.Constants.STATUS;

public class QuizSubCategoryActivity extends AppCompatActivity {

    private ArrayList<QuizSubCategoryModel> subCategories;
    private int catId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        catId = intent.getIntExtra(QUIZ_CATEGORY_ID,0);
        subCategories = new ArrayList<>();
        if (subCategories.isEmpty()){
            setContentView(R.layout.loading);
            new HTTPHandler().execute();
        }else{
            setContentView(R.layout.activity_quiz_sub_category);
        }


    }

    private void initializeLayout(){
        setContentView(R.layout.activity_quiz_sub_category);
        ImageView backBtn = findViewById(R.id.q_s_c_back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        RecyclerView qcRecyclerView = findViewById(R.id.quiz__sub_cat_recycler_view);
        RecyclerView.LayoutManager qcLayoutManager = new GridLayoutManager(this,2);
        QuizSubCategoryAdapter quizSubCategoryAdapter= new QuizSubCategoryAdapter(this,subCategories);

        qcRecyclerView.setHasFixedSize(true);
        qcRecyclerView.setLayoutManager(qcLayoutManager);
        qcRecyclerView.setAdapter(quizSubCategoryAdapter);
    }

    private void setCategories(JSONArray jsonArray){
        if(jsonArray != null){
            try {
                for(int i = 0; i<jsonArray.length(); i++){
                    JSONObject object = jsonArray.getJSONObject(i);
                    subCategories.add(
                            new QuizSubCategoryModel(
                                    Integer.parseInt(object.getString(ID)),
                                    Integer.parseInt(object.getString(QUIZ_CATEGORY_ID)),
                                    object.getString(QUIZ_SUB_CAT),
                                    Integer.parseInt(object.getString(STATUS)),
                                    R.drawable.ic_google_physical_web_black_48dp
                            )
                    );
                }
            }catch (JSONException e){
                e.printStackTrace();
            }
        }
    }

    private class HTTPHandler extends AsyncTask<String,Void,Void> {

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            initializeLayout();
        }

        @Override
        protected Void doInBackground(String... strings) {

            DBOperations dbOperations = DBOperations.getInstance();
            JSONArray jsonArray = dbOperations.getSubjects(String.valueOf(catId));
            setCategories(jsonArray);

            return null;
        }
    }
}
