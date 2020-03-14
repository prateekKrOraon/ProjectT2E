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
import com.think2exam.projectt2e.utility.HttpHandler2;
import com.think2exam.projectt2e.utility.InfoQuerySelector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.think2exam.projectt2e.Constants.ID;
import static com.think2exam.projectt2e.Constants.TABLE_ID;

public class CollegeInfoActivity extends AppCompatActivity {

    private String table;
    private int id;
    CollegeInfoModel collegeInfoModel=null;
    ArrayList<CoursesOfferedModal> courses;
    ArrayList<String> images;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(collegeInfoModel == null){
            setContentView(R.layout.loading);
            try {
                new GetCollegeInfo().execute();

            }catch (Exception e){}
        }else{
            setContentView(R.layout.activity_college_info);
        }

        //have to use imageURI

        table = getIntent().getStringExtra(TABLE_ID);
        id = getIntent().getIntExtra(ID,-1);




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
                "Type", table,
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

    public void showRatingDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.college_rating_layout);
        dialog.setCancelable(true);
        WindowManager.LayoutParams wm = new WindowManager.LayoutParams();

        try {
            wm.copyFrom(dialog.getWindow().getAttributes());
            wm.width = WindowManager.LayoutParams.MATCH_PARENT;
            wm.height = WindowManager.LayoutParams.WRAP_CONTENT;

        } catch (NullPointerException e) {
            System.out.println(e.toString());
        }

        AppCompatButton cancel = dialog.findViewById(R.id.cancel_rating);
        AppCompatButton send = dialog.findViewById(R.id.send_rating);
        final RatingBar ratingBar = dialog.findViewById(R.id.rating_bar);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float rating = ratingBar.getRating();
                Toast.makeText(CollegeInfoActivity.this, rating + " Sent", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.getWindow().setAttributes(wm);
    }

    private void setLayout()
    {
        setContentView(R.layout.activity_college_info);
        setImageSliderLayout();


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

        AppCompatImageButton ratingBtn = findViewById(R.id.college_info_rating_btn);

        ratingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRatingDialog();
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

                    String url="";
                    url+= collegeInfoModel.getClgUrl();
                    Uri gmmIntentUri = Uri.parse(""+url);
                    if(!url.equals(""))
                    {
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

            JSONObject jsonObject = dbOperations.getCollegeInfo(String.valueOf(id),table);
//            HttpHandler2 sh = new HttpHandler2();
//            // Making a request to url and getting response
//
//            int carId;
//            if (table.equals(getApplicationContext().getString(R.string.engineering)))
//                carId = R.string.engineering;
//            else if (table.equals(getApplicationContext().getString(R.string.agriculture)))
//                carId = R.string.agriculture;
//            else if (table.equals(getApplicationContext().getString(R.string.education)))
//                carId = R.string.education;
//            else if (table.equals(getApplicationContext().getString(R.string.university)))
//                carId = R.string.university;
//            else if (table.equals(getApplicationContext().getString(R.string.management)))
//                carId = R.string.management;
//            else if (table.equals(getApplicationContext().getString(R.string.medical_and_dental)))
//                carId = R.string.medical_and_dental;
//            else if (table.equals(getApplicationContext().getString(R.string.nursing_and_paramedical)))
//                carId = R.string.nursing_and_paramedical;
//            else
//                carId = R.string.pharmacy;
//
//            InfoQuerySelector infoQuerySelector = new InfoQuerySelector();
//            String req_url = infoQuerySelector.setreqURL(carId);
//
//
//
//            jsonStr = sh.makeServiceCall(req_url,id);
//            if(jsonStr!=null)
//            {
//                try
//                {
//                    final JSONObject jsonObject = new JSONObject(jsonStr);
//                    setColInfoModel(jsonObject);
//
//                }
//                catch (final JSONException e)
//                {
//                    e.printStackTrace();
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            Toast.makeText(getApplicationContext(),""+ e.getMessage(),Toast.LENGTH_LONG).show();
//                        }
//                    });                }
//
//            }

            try {
                setColInfoModel(jsonObject);
            } catch (final JSONException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),""+ e.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    });
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            setLayout();
        }
    }

    // (`id`, `college_name`, `college_location`, `DISTRICT`, `STATE`, `COUNTRY`, `PIN`, `college_type`, `college_rank`, `college_desc`, `BTECH_01`, `BTECH_02`, `BTECH_03`, `BTECH_04`, `BTECH_05`, `BTECH_06`, `BTECH_07`, `BTECH_08`, `BTECH_09`, `BTECH_10`, `MTECH_01`, `MTECH_02`,
    // `MTECH_03`, `MTECH_04`, `MTECH_05`, `year_established`, `college_image1`, `college_image2`, `college_image3`, `college_image4`, `url`)
    private void setColInfoModel(JSONObject jsonObject) throws JSONException {

        //setting basic colInfoModel
        collegeInfoModel =  new CollegeInfoModel(jsonObject.getInt("id"),jsonObject.getString("college_name"),jsonObject.getString("college_location"),jsonObject.getString("DISTRICT"),
                jsonObject.getString("STATE"),jsonObject.getString("COUNTRY"),jsonObject.getString("PIN"),jsonObject.getString("college_type"),
                jsonObject.getInt("college_rank"),jsonObject.getString("college_desc"),jsonObject.getString("year_established"),jsonObject.getString("url"));


        //setting course offered
        courses = new ArrayList<>();
//        for(int i=1;i<=10;i++)
//        {
//
//                if(i!=10 && !jsonObject.getString("BTECH_0"+i).equals(""))
//                {
//                    courses.add(new CoursesOfferedModal("BTECH ("+jsonObject.getString("BTECH_0"+i)+")"));
//                }
//                if(i==10 && !jsonObject.getString("BTECH_"+i).equals(""))
//                {
//                    courses.add(new CoursesOfferedModal("BTECH ("+jsonObject.getString("BTECH_"+i)+")"));
//                }
//
//        }
//
//        for(int i=1;i<=5;i++)
//        {
//                if(!jsonObject.getString("MTECH_0"+i).equals(""))
//                {
//                    courses.add(new CoursesOfferedModal("MTECH ("+jsonObject.getString("MTECH_0"+i)+")"));
//                }
//
//        }

        //setting image;
        images = new ArrayList<>();
        for(int i=1;i<=4;i++)
        {
                if(!jsonObject.getString("college_image"+i).equals(""))
                {
                    images.add(jsonObject.getString("college_image"+i));
                }

        }



    }

}
