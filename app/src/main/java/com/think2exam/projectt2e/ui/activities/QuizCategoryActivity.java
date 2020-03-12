package com.think2exam.projectt2e.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.think2exam.projectt2e.R;
import com.think2exam.projectt2e.adapters.QuizCatAdapter;
import com.think2exam.projectt2e.modals.CategoryModel;
import com.think2exam.projectt2e.modals.QuizCategoryModal;
import com.think2exam.projectt2e.utilities.DBOperations;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.think2exam.projectt2e.Constants.*;

public class QuizCategoryActivity extends AppCompatActivity {

    ArrayList<QuizCategoryModal> categoryModelArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        categoryModelArrayList = new ArrayList<>();

        if (categoryModelArrayList.isEmpty()){
            setContentView(R.layout.loading);
            new HTTPHandler().execute();
        }else{
            setContentView(R.layout.activity_quiz_category);
        }

    }

    private void initializeLayout(){
        setContentView(R.layout.activity_quiz_category);
        ImageView backBtn = findViewById(R.id.q_c_back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        RecyclerView qcRecyclerView = findViewById(R.id.quiz_cat_recycler_view);
        RecyclerView.LayoutManager qcLayoutManager = new GridLayoutManager(this,2);
        QuizCatAdapter quizCatAdapter = new QuizCatAdapter(categoryModelArrayList,this);

        qcRecyclerView.setHasFixedSize(true);
        qcRecyclerView.setLayoutManager(qcLayoutManager);
        qcRecyclerView.setAdapter(quizCatAdapter);
    }

    private void setCategories(JSONArray jsonArray){
        if(jsonArray != null){
            try {
                for(int i = 0; i<jsonArray.length(); i++){
                    JSONObject object = jsonArray.getJSONObject(i);
                    categoryModelArrayList.add(
                            new QuizCategoryModal(
                                    Integer.parseInt(object.getString(ID)),
                                    object.getString(QUIZ_CATEGORY),
                                    object.getString(QUIZ_CATEGORY_DES),
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
            JSONArray jsonArray = dbOperations.getCategories();
            setCategories(jsonArray);

            return null;
        }
    }

}