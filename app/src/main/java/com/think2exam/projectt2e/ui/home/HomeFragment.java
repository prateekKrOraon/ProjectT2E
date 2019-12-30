package com.think2exam.projectt2e.ui.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.chahinem.pageindicator.PageIndicator;
import com.google.android.material.card.MaterialCardView;
import com.think2exam.projectt2e.MainActivity;
import com.think2exam.projectt2e.R;
import com.think2exam.projectt2e.ui.activities.AboutQuizActivity;
import com.think2exam.projectt2e.ui.activities.CollegeListActivity;
import com.think2exam.projectt2e.ui.home.top_college.SnapHelperOneByOne;
import com.think2exam.projectt2e.ui.home.top_college.TopCollegeSliderAdapter;


import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class HomeFragment extends Fragment {
    public static final String id = "home_fragment";
    private static ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    private static  View parentView;

    ArrayList<String> TopCollegeArrayList;
    private static Context mainActivityContext;

    public HomeFragment() {
    }

    @SuppressLint("ValidFragment")
    public HomeFragment(Context context) {
        mainActivityContext = context;
    }



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        parentView = root;

        //initiating top slider
        initSlider(inflater);

        MaterialCardView categoryIit = root.findViewById(R.id.category_iit);
        MaterialCardView categoryNit = root.findViewById(R.id.category_nit);
        final MaterialCardView categoryUniversity = root.findViewById(R.id.category_university);
        MaterialCardView quiz_cardview = parentView.findViewById(R.id.quiz_cardview);
        final TextView iit_Cn = (TextView)categoryIit.findViewById(R.id.category_cn);
        final TextView nit_Cn = (TextView)categoryNit.findViewById(R.id.category_cn);
        final TextView university_Cn = (TextView)categoryUniversity.findViewById(R.id.category_cn);
        TextView gk_topic = quiz_cardview.findViewById(R.id.quiz_topic);
        LinearLayout iit_explore_btn = (LinearLayout)categoryIit.findViewById(R.id.explore_btn);
        LinearLayout nit_explore_btn = (LinearLayout)categoryNit.findViewById(R.id.explore_btn);
        LinearLayout university_explore_btn = (LinearLayout)categoryUniversity.findViewById(R.id.explore_btn);
        LinearLayout quiz_explore_btn = quiz_cardview.findViewById(R.id.learn_more_btn);
        ImageView iit_back_img = categoryIit.findViewById(R.id.college_image);
        final ImageView nit_back_img = categoryNit.findViewById(R.id.college_image);
        ImageView university_back_img = categoryUniversity.findViewById(R.id.college_image);
        ImageView quiz_back_img = quiz_cardview.findViewById(R.id.quize_image);
        final TextView iit_des = categoryIit.findViewById(R.id.category_des);
        final TextView nit_des = categoryNit.findViewById(R.id.category_des);
        final TextView university_des = categoryUniversity.findViewById(R.id.category_des);
        final TextView quiz_des = quiz_cardview.findViewById(R.id.quiz_des);

        iit_Cn.setText("IIT's");
        nit_Cn.setText("NIT's");
        university_Cn.setText("University's");
        gk_topic.setText("Quiz");

        Glide.with(this)
                .load(R.drawable.university_back)
                .into(university_back_img);
        Glide.with(this)
                .load(R.drawable.iit_back)
                .into(iit_back_img);
        Glide.with(this)
                .load(R.drawable.nit_back)
                .into(nit_back_img);
        Glide.with(this)
                .load(R.drawable.quize)
                .into(quiz_back_img);

        categoryIit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nit_des.setText("");
                university_des.setText("");
                quiz_des.setText("");
                iit_des.setText(getResources().getString(R.string.iit_des));
            }
        });
        categoryNit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iit_des.setText("");
                university_des.setText("");
                quiz_des.setText("");
                nit_des.setText(getResources().getString(R.string.nit_des));
            }
        });
        categoryUniversity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iit_des.setText("");
                nit_des.setText("");
                quiz_des.setText("");
                university_des.setText(getResources().getString(R.string.university_des));
            }
        });
        quiz_cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nit_des.setText("");
                iit_des.setText("");
                university_des.setText("");
                quiz_des.setText(getResources().getString(R.string.quiz_des));
            }
        });


        iit_explore_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mainActivityContext, CollegeListActivity.class);
                intent.putExtra("tag",iit_Cn.getText().toString());
                startActivity(intent);
            }
        });

        nit_explore_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mainActivityContext, CollegeListActivity.class);
                intent.putExtra("tag",nit_Cn.getText().toString());
                startActivity(intent);
            }
        });

        university_explore_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mainActivityContext, CollegeListActivity.class);
                intent.putExtra("tag",university_Cn.getText().toString());
                startActivity(intent);
            }
        });

        quiz_explore_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mainActivityContext, AboutQuizActivity.class);
                startActivity(intent);
            }
        });





        return root;
    }


    private void initSlider(LayoutInflater inflater) {


        RecyclerView tcRecyclerView = parentView.findViewById(R.id.top_college_recycler_view);
        tcRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager tcLayoutManager = new LinearLayoutManager(mainActivityContext);
        ((LinearLayoutManager) tcLayoutManager).setOrientation(LinearLayoutManager.HORIZONTAL);
        LinearSnapHelper linearSnapHelper = new SnapHelperOneByOne();
        linearSnapHelper.attachToRecyclerView(tcRecyclerView);

        TopCollegeArrayList = new ArrayList<>();
        TopCollegeArrayList.add("1");
        TopCollegeArrayList.add("2");
        TopCollegeArrayList.add("3");
        TopCollegeArrayList.add("4");
        TopCollegeArrayList.add("5");

        TopCollegeSliderAdapter topCollegeSliderAdapter = new TopCollegeSliderAdapter(TopCollegeArrayList,mainActivityContext);
        tcRecyclerView.setLayoutManager(tcLayoutManager);
        tcRecyclerView.setAdapter(topCollegeSliderAdapter);

        PageIndicator pageIndicator = parentView.findViewById(R.id.page_indicator);
        pageIndicator.attachTo(tcRecyclerView);


        //Set circle indicator radius




    }
}


