package com.think2exam.projectt2e.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatTextView;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import com.think2exam.projectt2e.R;
import com.think2exam.projectt2e.adapters.ColImageSliderAdapter;
import com.think2exam.projectt2e.adapters.CollegeFacilitiesAdapter;
import com.think2exam.projectt2e.adapters.CoursesOfferedAdapter;
import com.think2exam.projectt2e.adapters.QuickFactsAdapter;
import com.think2exam.projectt2e.modals.CollegeFacilitiesModal;
import com.think2exam.projectt2e.modals.CollegeInfoModel;
import com.think2exam.projectt2e.modals.CoursesOfferedModal;
import com.think2exam.projectt2e.modals.QuickFactsModal;
import com.think2exam.projectt2e.utilities.DBOperations;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CollegeInfoActivity extends AppCompatActivity {

    private int catId;
    private int id;
    CollegeInfoModel collegeInfoModel=null;
    ArrayList<CoursesOfferedModal> courses;
    ArrayList<String>  images = new ArrayList<>();;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        catId = getIntent().getIntExtra("catId",-1);
        id = getIntent().getIntExtra("id",-1);

        if (collegeInfoModel == null){
            setContentView(R.layout.loading);
            try {
                new GetCollegeInfo().execute();

            }catch (Exception e){
                e.printStackTrace();
            }
        }else{
            setContentView(R.layout.activity_college_info);
        }

        setContentView(R.layout.activity_college_info);

        //have to use imageURI



    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setImageSliderLayout()
    {
        SliderView imageSlider = findViewById(R.id.college_image_slider);
        imageSlider.setSliderAdapter(new ColImageSliderAdapter(this,images));
        imageSlider.startAutoCycle();
        imageSlider.setIndicatorAnimation(IndicatorAnimations.WORM);
        imageSlider.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
    }

    private void initializeCollegeFacilities() {

        ArrayList<CollegeFacilitiesModal> facilities = new ArrayList<>();

        facilities.add(new CollegeFacilitiesModal("Boys Hostel",R.drawable.outline_person_black_48));
        facilities.add(new CollegeFacilitiesModal("Girls Hostel",R.drawable.outline_person_black_48));
        facilities.add(new CollegeFacilitiesModal("Library",R.drawable.outline_school_black_48));
        facilities.add(new CollegeFacilitiesModal("Sports",R.drawable.outline_poll_black_48));
        facilities.add(new CollegeFacilitiesModal("Cafeteria",R.drawable.outline_home_black_48));


        CollegeFacilitiesAdapter adapter = new CollegeFacilitiesAdapter(this,facilities);
        GridView facilitiesProvided = findViewById(R.id.college_info_facilities);
        facilitiesProvided.setAdapter(adapter);
        ViewGroup.LayoutParams params = facilitiesProvided.getLayoutParams();
        params.height = (((facilities.size())/4)*facilitiesProvided.getMinimumHeight());
        if(facilities.size()%4 > 0){
            params.height += facilitiesProvided.getMinimumHeight()+(facilities.size()%4)*10;
        }
        facilitiesProvided.setLayoutParams(params);
        facilitiesProvided.setVerticalScrollBarEnabled(false);
        facilitiesProvided.requestLayout();

    }

    private void initializeCoursesOffered() {

        CoursesOfferedAdapter adapter = new CoursesOfferedAdapter(this,courses);
        ListView coursesOffered = findViewById(R.id.offered_course_list);
        coursesOffered.setAdapter(adapter);

        ViewGroup.LayoutParams params = coursesOffered.getLayoutParams();
        params.height = (courses.size()*coursesOffered.getMinimumHeight());
        coursesOffered.setLayoutParams(params);
        coursesOffered.setVerticalScrollBarEnabled(false);
        coursesOffered.requestLayout();
        //setListViewHeightBasedOnChildren(coursesOffered);

    }

    private void initializeQuickFacts() {

        ArrayList<QuickFactsModal> quickFactsList = new ArrayList<>();

        quickFactsList.add(new QuickFactsModal("Year Established",collegeInfoModel.getClgYOE(),R.drawable.ic_year_established_black_24dp));
        quickFactsList.add(new QuickFactsModal(
                "Type", getString(catId),
                R.drawable.outline_home_black_48)
        );
        quickFactsList.add(new QuickFactsModal(
                "Ownership",
                "Govt. of India",
                R.drawable.outline_person_black_48)
        );

        quickFactsList.add(new QuickFactsModal(
                "Rank",
                ""+collegeInfoModel.getRank(),
                R.drawable.rank_icon)
        );

        QuickFactsAdapter quickFactsAdapter = new QuickFactsAdapter(this,quickFactsList);
        GridView quickFacts = findViewById(R.id.quick_facts);

        ViewGroup.LayoutParams params =  quickFacts.getLayoutParams();

        quickFacts.setAdapter(quickFactsAdapter);


        params.height = (((quickFactsList.size())/2)*quickFacts.getMinimumHeight());
        if(quickFactsList.size()%2 > 0){
            params.height += quickFacts.getMinimumHeight()+(quickFactsList.size()%4)*10;
        }
        quickFacts.setLayoutParams(params);
        quickFacts.setVerticalScrollBarEnabled(false);
        quickFacts.requestLayout();


       // //setGridViewHeightBasedOnChildren(quickFacts);

    }

    private void setLayout()
    {

        try {
            setImageSliderLayout();
        }catch (Exception e){
            e.printStackTrace();
        }

        AppCompatTextView collegeName = findViewById(R.id.college_info_col_name);
        collegeName.setText(collegeInfoModel.getClgName());


        AppCompatTextView collegeLocation = findViewById(R.id.college_info_location);
        collegeLocation.setText(collegeInfoModel.getClgLocation());


        initializeQuickFacts();

        AppCompatTextView aboutCollege = findViewById(R.id.college_info_col_about);
        aboutCollege.setText(collegeInfoModel.getClgDesc());

        initializeCoursesOffered();

        initializeCollegeFacilities();

        AppCompatImageButton backBtn = findViewById(R.id.college_info_back_btn);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        AppCompatButton locBtn  = findViewById(R.id.locate_on_map);
        try {
            locBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String address = collegeInfoModel.getClgName() + " " + collegeInfoModel.getClgLocation();
                    Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + address);
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    startActivity(mapIntent);
                }
            });
        }catch (Exception e){}

        AppCompatButton websiteBtn = findViewById(R.id.visit_web);
        try{

            websiteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String url= collegeInfoModel.getClgUrl();
                    if(!url.equals(null))
                    {
                        Uri gmmIntentUri = Uri.parse("https://"+url);
                        Intent urlIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                        startActivity(urlIntent);
                    }

                }
            });

        }catch (Exception e){}

    }

    private class GetCollegeInfo extends AsyncTask<Void, Void, Void> {
        String jsonStr;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            DBOperations dbOperations = DBOperations.getInstance();
            JSONObject jsonObject = dbOperations.getCollegeInfo(id,catId);
            if(jsonObject!=null)
            {
                try {
                    setColInfoModel(jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            try {
                setLayout();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }


    private void setColInfoModel(JSONObject jsonObject) throws JSONException {

        //setting basic colInfoModel
        collegeInfoModel =  new CollegeInfoModel(jsonObject.getInt("id"),jsonObject.getString("college_name"),jsonObject.getString("college_location"),jsonObject.getString("DISTRICT"),
                jsonObject.getString("STATE"),jsonObject.getString("COUNTRY"),jsonObject.getString("PIN"),jsonObject.getString("college_type"),
                jsonObject.getInt("college_rank"),jsonObject.getString("college_desc"),jsonObject.getString("year_established"),jsonObject.getString("url"));



        //setting image;

        for(int i=1;i<=4;i++)
        {
                if(!jsonObject.getString("college_image"+i).equals(""))
                {
                    images.add(jsonObject.getString("college_image"+i));
                }

        }

    }

    private void setCourses(JSONObject jsonObject, int catId) throws JSONException
    {
        //setting course offered
        courses = new ArrayList<>();
        if(catId == R.string.engineering)
        {
            for(int i=1;i<=10;i++)
            {

                if(i!=10 && !jsonObject.getString("BTECH_0"+i).equals(""))
                {
                    courses.add(new CoursesOfferedModal("BTECH ("+jsonObject.getString("BTECH_0"+i)+")"));
                }
                if(i==10 && !jsonObject.getString("BTECH_"+i).equals(""))
                {
                    courses.add(new CoursesOfferedModal("BTECH ("+jsonObject.getString("BTECH_"+i)+")"));
                }

            }

            for(int i=1;i<=5;i++)
            {
                if(!jsonObject.getString("MTECH_0"+i).equals(""))
                {
                    courses.add(new CoursesOfferedModal("MTECH ("+jsonObject.getString("MTECH_0"+i)+")"));
                }

            }
        }
       else
        {
            for(int i=1;i<=5;i++)
            {

                if(!jsonObject.get("bachelor_degree_course_0"+i).equals(""))
                {
                    courses.add(new CoursesOfferedModal(jsonObject.getString("bachelor_degree_course_0"+i)));
                }


            }

            for(int i=1;i<=2;i++)
            {
                if(!jsonObject.getString("master_degree_course_0"+i).equals(""))
                {
                    courses.add(new CoursesOfferedModal(jsonObject.getString("master_degree_course_0"+i)));
                }

            }
        }

    }

}
