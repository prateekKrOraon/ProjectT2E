package com.think2exam.projectt2e.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.think2exam.projectt2e.R;
import com.think2exam.projectt2e.adapters.QuizCatAdapter;
import com.think2exam.projectt2e.modals.QuizCategoryModal;

import java.util.ArrayList;

public class QuizCategoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_category);

        ImageView backBtn = findViewById(R.id.q_c_back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        ArrayList<QuizCategoryModal> categoryModelArrayList = new ArrayList<>();

        categoryModelArrayList.add(new QuizCategoryModal("Computer Science",R.drawable.ic_laptop_black_48dp));
        categoryModelArrayList.add(new QuizCategoryModal("Science",R.drawable.ic_earth_black_48dp));
        categoryModelArrayList.add(new QuizCategoryModal("Sports",R.drawable.outline_sports_basketball_black_48));
        categoryModelArrayList.add(new QuizCategoryModal("Technology",R.drawable.ic_google_physical_web_black_48dp));

        RecyclerView qcRecyclerView = findViewById(R.id.quiz_cat_recycler_view);
        RecyclerView.LayoutManager qcLayoutManager = new GridLayoutManager(this,2);
        QuizCatAdapter quizCatAdapter = new QuizCatAdapter(categoryModelArrayList,this);

        qcRecyclerView.setHasFixedSize(true);
        qcRecyclerView.setLayoutManager(qcLayoutManager);
        qcRecyclerView.setAdapter(quizCatAdapter);



    }


}