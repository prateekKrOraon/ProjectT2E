package com.think2exam.projectt2e.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.card.MaterialCardView;
import com.think2exam.projectt2e.R;
import com.think2exam.projectt2e.adapters.CollegeListAdapter;
import com.think2exam.projectt2e.ui.dialogs.CollegeFilterDialog;

import java.util.ArrayList;

public class CollegeListActivity extends AppCompatActivity {

    private ArrayList<String> CollegeList,CollegeListDup;
    MaterialCardView searchBar;
    ImageView searchicon,crossicon,filtericon;
    TextView title;
    String tag;
    int count=0;

    RecyclerView cRecyclerView;
    RecyclerView.LayoutManager cLayoutManager;
    CollegeListAdapter collegeListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_college_list);


        //setting toolbar
        title = findViewById(R.id.toolbar_title);
        ImageView backBtn = findViewById(R.id.back_btn);
        crossicon = findViewById(R.id.cross_icon_);
        searchBar = findViewById(R.id.search_bar);
        searchicon  = findViewById(R.id.search_icon);
        filtericon = findViewById(R.id.filter_icon);
        tag = getIntent().getStringExtra("tag");
        //set college list
        setCollegeItems();
        count = CollegeList.size();
        title.setText(tag+" ("+count+")");



        final Handler handler = new Handler();
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               onBackPressed();
            }
        });


        //setting Progress bar

        final ProgressBar progressBar = findViewById(R.id.progress_bar);

        new Thread(){

            @Override
            public void run() {
                final int max = 3;
                int current = 0;
                progressBar.setMax(max);
                while (current<max)
                {
                    try{
                        sleep(500);
                    }catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    current=current+1;
                    progressBar.setProgress(current);

                }

                final int finalCurrent = current;
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if(finalCurrent ==max)
                        {
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });


            }
        }.start();



        //set recyclerView with Adapter

        cRecyclerView = findViewById(R.id.college_list_recycler_view);
        cLayoutManager = new LinearLayoutManager(this);
        collegeListAdapter = new CollegeListAdapter(CollegeList,this);
        cRecyclerView.setHasFixedSize(true);
        cRecyclerView.setLayoutManager(cLayoutManager);
        cRecyclerView.setAdapter(collegeListAdapter);


        //search

        final EditText searchBox = searchBar.findViewById(R.id.search_edittext);
        searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                 onChangedText(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //setting cross icon
        crossicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchBox.setText("");
                onChangedText("");
            }
        });

        //setting filter icon
        filtericon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickedFilterIcon();
            }
        });



    }

    public void setCollegeItems()
    {


        CollegeList  = new ArrayList<>();
        CollegeList.add("Indian Institute Of Technology Bombay");
        CollegeList.add("Indian Institute Of Technology Guwahati");
        CollegeList.add("National Institute Of Technology Sikkim");
        CollegeList.add("National Institute Of Technology Sikkim");
        CollegeList.add("National Institute Of Technology Sikkim");
        CollegeList.add("National Institute Of Technology Sikkim");
        CollegeList.add("National Institute Of Technology Sikkim National Institute Of Technology Sikkim Technology Sikkim National Institute Of Technology Sikkim");
        CollegeList.add("National Institute Of Technology Sikkim National Inst");
        CollegeListDup=CollegeList;
    }

    public void onChangedText(CharSequence s)
    {

        if(s.length()!=0)
        {
            crossicon.setVisibility(View.VISIBLE);
            searchicon.setVisibility(View.GONE);
        }
        else
        {
            crossicon.setVisibility(View.GONE);
            searchicon.setVisibility(View.VISIBLE);
        }
        ArrayList<String> arrayList = new ArrayList<>();
         for(String college :CollegeList)
         {
              if(college.toLowerCase().contains(s.toString().toLowerCase()))
              {
                  arrayList.add(college);
              }
         }
         CollegeListDup = null;
         CollegeListDup = arrayList;
        cLayoutManager = new LinearLayoutManager(this);
        collegeListAdapter = new CollegeListAdapter(CollegeListDup,this);
        cRecyclerView.setHasFixedSize(true);
        cRecyclerView.setLayoutManager(cLayoutManager);
        cRecyclerView.setAdapter(collegeListAdapter);

        count = CollegeListDup.size();
        title.setText(tag+" ("+count+")");

    }


   public void onClickedFilterIcon()
   {
       CollegeFilterDialog collegeFilterDialog = new CollegeFilterDialog();
       collegeFilterDialog.show(getSupportFragmentManager(),"filterdialog");
   }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
