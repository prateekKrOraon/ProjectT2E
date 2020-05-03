package com.think2exam.projectt2e.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;

import com.think2exam.projectt2e.R;
import com.think2exam.projectt2e.adapters.CheckAnsAdapter;
import com.think2exam.projectt2e.utilities.Questions;

public class CheckAnswersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_answers);

        Toolbar toolbar = findViewById(R.id.check_ans_toolbar);
        setSupportActionBar(toolbar);
        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }catch (NullPointerException ex){
            ex.printStackTrace();
        }

        Questions questions = Questions.getInstance();
        RecyclerView recyclerView = findViewById(R.id.check_ans_recycler);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        CheckAnsAdapter adapter = new CheckAnsAdapter(questions.getQuestions(),this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
