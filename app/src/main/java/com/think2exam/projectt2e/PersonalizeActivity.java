package com.think2exam.projectt2e;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;

import com.think2exam.projectt2e.adapters.PersonalizeAdapter;
import com.think2exam.projectt2e.modals.PersonalizeModel;

import java.util.ArrayList;

public class PersonalizeActivity extends AppCompatActivity {


    public static Activity persActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personalize);
        overridePendingTransition(R.anim.enter, R.anim.exit);

        //set arraylist
        ArrayList<PersonalizeModel> arrayList = new ArrayList<>();
        arrayList.add(new PersonalizeModel(R.string.engineering,R.drawable.engineering));
        arrayList.add(new PersonalizeModel(R.string.management,R.drawable.management));
        arrayList.add(new PersonalizeModel(R.string.agriculture,R.drawable.agriculture));
        arrayList.add(new PersonalizeModel(R.string.medical_and_dental,R.drawable.medical));
        arrayList.add(new PersonalizeModel(R.string.pharmacy,R.drawable.pharmacy));
        arrayList.add(new PersonalizeModel(R.string.nursing_and_paramedical,R.drawable.nurse));
        arrayList.add(new PersonalizeModel(R.string.education,R.drawable.education));
        arrayList.add(new PersonalizeModel(R.string.university,R.drawable.graduate));

        //set recycler view

        RecyclerView recyclerView = findViewById(R.id.recycler_view_personalize);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,3);
        recyclerView.setAdapter(new PersonalizeAdapter(arrayList,this));
        recyclerView.setLayoutManager(gridLayoutManager);

        persActivity = this;


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
