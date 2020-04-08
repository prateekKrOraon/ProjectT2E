package com.think2exam.projectt2e.ui.activities;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.icu.lang.UCharacter;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
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

import static com.think2exam.projectt2e.Constants.BACHELOR_DEGREE;
import static com.think2exam.projectt2e.Constants.BACHELOR_DEGREE_COURSE_01;
import static com.think2exam.projectt2e.Constants.BACHELOR_DEGREE_COURSE_02;
import static com.think2exam.projectt2e.Constants.BACHELOR_DEGREE_COURSE_03;
import static com.think2exam.projectt2e.Constants.BACHELOR_DEGREE_COURSE_04;
import static com.think2exam.projectt2e.Constants.BACHELOR_DEGREE_COURSE_05;
import static com.think2exam.projectt2e.Constants.BTECH_01;
import static com.think2exam.projectt2e.Constants.BTECH_02;
import static com.think2exam.projectt2e.Constants.BTECH_03;
import static com.think2exam.projectt2e.Constants.BTECH_04;
import static com.think2exam.projectt2e.Constants.BTECH_05;
import static com.think2exam.projectt2e.Constants.BTECH_06;
import static com.think2exam.projectt2e.Constants.BTECH_07;
import static com.think2exam.projectt2e.Constants.BTECH_08;
import static com.think2exam.projectt2e.Constants.BTECH_09;
import static com.think2exam.projectt2e.Constants.BTECH_10;
import static com.think2exam.projectt2e.Constants.COLLEGE_DESC;
import static com.think2exam.projectt2e.Constants.COLLEGE_IMAGE1;
import static com.think2exam.projectt2e.Constants.COLLEGE_IMAGE2;
import static com.think2exam.projectt2e.Constants.COLLEGE_IMAGE3;
import static com.think2exam.projectt2e.Constants.COLLEGE_IMAGE4;
import static com.think2exam.projectt2e.Constants.COLLEGE_LOCATION;
import static com.think2exam.projectt2e.Constants.COLLEGE_NAME;
import static com.think2exam.projectt2e.Constants.COLLEGE_RANK;
import static com.think2exam.projectt2e.Constants.COLLEGE_TYPE;
import static com.think2exam.projectt2e.Constants.COUNTRY;
import static com.think2exam.projectt2e.Constants.DISTRICT;
import static com.think2exam.projectt2e.Constants.ENGINEERING_COLLEGE_CI_URL;
import static com.think2exam.projectt2e.Constants.ID;
import static com.think2exam.projectt2e.Constants.MANAGEMENT_COLLEGE_CI_URL;
import static com.think2exam.projectt2e.Constants.MASTER_DEGREE;
import static com.think2exam.projectt2e.Constants.MASTER_DEGREE_COURSE_01;
import static com.think2exam.projectt2e.Constants.MASTER_DEGREE_COURSE_02;
import static com.think2exam.projectt2e.Constants.MTECH_01;
import static com.think2exam.projectt2e.Constants.MTECH_02;
import static com.think2exam.projectt2e.Constants.MTECH_03;
import static com.think2exam.projectt2e.Constants.MTECH_04;
import static com.think2exam.projectt2e.Constants.MTECH_05;
import static com.think2exam.projectt2e.Constants.OTHER_COLLEGE_CI_URL;
import static com.think2exam.projectt2e.Constants.PIN;
import static com.think2exam.projectt2e.Constants.STATE;
import static com.think2exam.projectt2e.Constants.TABLE_ID;
import static com.think2exam.projectt2e.Constants.URL;
import static com.think2exam.projectt2e.Constants.YEAR_ESTABLISHED;

public class CollegeInfoActivity extends AppCompatActivity {

    private int catId;
    private int id;
    CollegeInfoModel collegeInfoModel=null;
    ArrayList<CoursesOfferedModal> bcourses;
    ArrayList<CoursesOfferedModal> mcourses;
    RecyclerView coursesOfferedRV;
    TextView bachelor, master;
    CoursesOfferedAdapter adapter;
    ArrayList<String>  images = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        catId = getIntent().getIntExtra(TABLE_ID,-1);
        id = getIntent().getIntExtra(ID,-1);

        if(collegeInfoModel==null) {
            setContentView(R.layout.loading);
            try {
                new GetCollegeInfo().execute();

            }catch (Exception e){}
        }else {
            setContentView(R.layout.activity_college_info);
        }






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


    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initializeCoursesOffered() {

        bachelor = findViewById(R.id.bachelor);
        master = findViewById(R.id.master);
        bachelor.setBackgroundTintList(getColorStateList(R.color.colorAccentTrans));
        coursesOfferedRV = findViewById(R.id.offered_course_list);
        setCoursesOfferedAdapter(bcourses);

        bachelor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                master.setBackgroundTintList(null);
                bachelor.setBackgroundTintList(getColorStateList(R.color.colorAccentTrans));
                setCoursesOfferedAdapter(bcourses);

            }
        });
        master.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bachelor.setBackgroundTintList(null);
                master.setBackgroundTintList(getColorStateList(R.color.colorAccentTrans));
                setCoursesOfferedAdapter(mcourses);
            }
        });


    }

    private void setCoursesOfferedAdapter(ArrayList<CoursesOfferedModal> courses){
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        adapter = new CoursesOfferedAdapter(courses,this);
        coursesOfferedRV.setLayoutManager(layoutManager);
        coursesOfferedRV.setAdapter(adapter);
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



    private class GetCollegeInfo extends AsyncTask<Void, Void, Void> {

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

                try {
                    setImages(jsonObject);

                }catch (JSONException e){
                    e.printStackTrace();
                }

                try {
                    setCourses(jsonObject);
                }catch (JSONException e){
                    e.printStackTrace();
                }

            }
            return null;
        }

        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            try {
                if(collegeInfoModel!=null) {
                    setLayout();
                }
            }catch (Exception e){}
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void setLayout()
    {
        setContentView(R.layout.activity_college_info);
        try {
            setImageSliderLayout();
        }catch (Exception e){}

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


    private void setColInfoModel(JSONObject jsonObject) throws JSONException {

        collegeInfoModel =  new CollegeInfoModel(jsonObject.getInt(ID),
                jsonObject.getString(COLLEGE_NAME),
                jsonObject.getString(COLLEGE_LOCATION),
                jsonObject.getString(DISTRICT),
                jsonObject.getString(STATE),
                jsonObject.getString(COUNTRY),
                jsonObject.getString(PIN),
                jsonObject.getString(COLLEGE_TYPE),
                jsonObject.getString(COLLEGE_RANK),
                jsonObject.getString(COLLEGE_DESC),
                jsonObject.getString(YEAR_ESTABLISHED),
                jsonObject.getString(URL));


    }

    private void setImages(JSONObject jsonObject) throws JSONException
    {
        String image_url;
        if(catId == R.string.engineering){
            image_url = ENGINEERING_COLLEGE_CI_URL;
        }else if(catId == R.string.management){
            image_url = MANAGEMENT_COLLEGE_CI_URL;
        }else {
            image_url = OTHER_COLLEGE_CI_URL;
        }
        images.add(image_url+jsonObject.getString(COLLEGE_IMAGE1));
        images.add(image_url+jsonObject.getString(COLLEGE_IMAGE2));
        images.add(image_url+jsonObject.getString(COLLEGE_IMAGE3));
        images.add(image_url+jsonObject.getString(COLLEGE_IMAGE4));


    }

    private void setCourses(JSONObject jsonObject) throws JSONException
    {

        bcourses = new ArrayList<>();
        mcourses = new ArrayList<>();
        if(catId == R.string.engineering)
        {

            if(jsonObject.getString(BTECH_01).trim().length()!=0) {
                bcourses.add(new CoursesOfferedModal(BACHELOR_DEGREE, jsonObject.getString(BTECH_01)));
            }
            if(jsonObject.getString(BTECH_02).trim().length()!=0) {
                bcourses.add(new CoursesOfferedModal(BACHELOR_DEGREE, jsonObject.getString(BTECH_02)));
            }
            if(jsonObject.getString(BTECH_03).trim().length()!=0) {
                bcourses.add(new CoursesOfferedModal(BACHELOR_DEGREE, jsonObject.getString(BTECH_03)));
            }
            if(jsonObject.getString(BTECH_04).trim().length()!=0) {
                bcourses.add(new CoursesOfferedModal(BACHELOR_DEGREE, jsonObject.getString(BTECH_04)));
            }
            if(jsonObject.getString(BTECH_05).trim().length()!=0) {
                bcourses.add(new CoursesOfferedModal(BACHELOR_DEGREE, jsonObject.getString(BTECH_05)));
            }
            if(jsonObject.getString(BTECH_06).trim().length()!=0) {
                bcourses.add(new CoursesOfferedModal(BACHELOR_DEGREE, jsonObject.getString(BTECH_06)));
            }
            if(jsonObject.getString(BTECH_07).trim().length()!=0) {
                bcourses.add(new CoursesOfferedModal(BACHELOR_DEGREE, jsonObject.getString(BTECH_07)));
            }
            if(jsonObject.getString(BTECH_08).trim().length()!=0) {
                bcourses.add(new CoursesOfferedModal(BACHELOR_DEGREE, jsonObject.getString(BTECH_08)));
            }
            if(jsonObject.getString(BTECH_09).trim().length()!=0) {
                bcourses.add(new CoursesOfferedModal(BACHELOR_DEGREE, jsonObject.getString(BTECH_09)));
            }
            if(jsonObject.getString(BTECH_10).trim().length()!=0) {
                bcourses.add(new CoursesOfferedModal(BACHELOR_DEGREE, jsonObject.getString(BTECH_10)));
            }

            if(jsonObject.getString(MTECH_01).trim().length()!=0){
                mcourses.add(new CoursesOfferedModal(MASTER_DEGREE,jsonObject.getString(MTECH_01)));
            }
            if(jsonObject.getString(MTECH_02).trim().length()!=0){
                mcourses.add(new CoursesOfferedModal(MASTER_DEGREE,jsonObject.getString(MTECH_02)));
            }
            if(jsonObject.getString(MTECH_03).trim().length()!=0){
                mcourses.add(new CoursesOfferedModal(MASTER_DEGREE,jsonObject.getString(MTECH_03)));
            }
            if(jsonObject.getString(MTECH_04).trim().length()!=0){
                mcourses.add(new CoursesOfferedModal(MASTER_DEGREE,jsonObject.getString(MTECH_04)));
            }
            if(jsonObject.getString(MTECH_05).trim().length()!=0){
                mcourses.add(new CoursesOfferedModal(MASTER_DEGREE,jsonObject.getString(MTECH_05)));
            }



        }
       else
        {
            if(jsonObject.getString(BACHELOR_DEGREE_COURSE_01).trim().length()!=0) {
                bcourses.add(new CoursesOfferedModal(BACHELOR_DEGREE, jsonObject.getString(BACHELOR_DEGREE_COURSE_01)));
            }
            if(jsonObject.getString(BACHELOR_DEGREE_COURSE_02).trim().length()!=0) {
                bcourses.add(new CoursesOfferedModal(BACHELOR_DEGREE, jsonObject.getString(BACHELOR_DEGREE_COURSE_02)));
            }
            if(jsonObject.getString(BACHELOR_DEGREE_COURSE_03).trim().length()!=0) {
                bcourses.add(new CoursesOfferedModal(BACHELOR_DEGREE, jsonObject.getString(BACHELOR_DEGREE_COURSE_03)));
            }
            if(jsonObject.getString(BACHELOR_DEGREE_COURSE_04).trim().length()!=0) {
                bcourses.add(new CoursesOfferedModal(BACHELOR_DEGREE, jsonObject.getString(BACHELOR_DEGREE_COURSE_04)));
            }
            if(jsonObject.getString(BACHELOR_DEGREE_COURSE_05).trim().length()!=0) {
                bcourses.add(new CoursesOfferedModal(BACHELOR_DEGREE, jsonObject.getString(BACHELOR_DEGREE_COURSE_05)));
            }

            if(jsonObject.getString(MASTER_DEGREE_COURSE_01).trim().length()!=0) {
                mcourses.add(new CoursesOfferedModal(MASTER_DEGREE, jsonObject.getString(MASTER_DEGREE_COURSE_01)));
            }
            if(jsonObject.getString(MASTER_DEGREE_COURSE_02).trim().length()!=0) {
                mcourses.add(new CoursesOfferedModal(MASTER_DEGREE, jsonObject.getString(MASTER_DEGREE_COURSE_02)));
            }

        }



    }

}
