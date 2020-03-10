package com.think2exam.projectt2e.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;

import android.app.Dialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.AppBarLayout;
import com.think2exam.projectt2e.R;
import com.think2exam.projectt2e.adapters.CollegeFacilitiesAdapter;
import com.think2exam.projectt2e.adapters.CoursesOfferedAdapter;
import com.think2exam.projectt2e.adapters.QuickFactsAdapter;
import com.think2exam.projectt2e.modals.CollegeFacilitiesModal;
import com.think2exam.projectt2e.modals.CoursesOfferedModal;
import com.think2exam.projectt2e.modals.QuickFactsModal;

import java.util.ArrayList;

public class CollegeInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_college_info);

        //have to use imageURI


        AppCompatImageView imageView = findViewById(R.id.college_info_col_image);
        imageView.setImageDrawable(
            getResources()
                .getDrawable(R.drawable.nit_sikkim)
        );

        AppCompatTextView collegeName = findViewById(R.id.college_info_col_name);
        collegeName.setText("National Institute of Technology Sikkim");


        AppCompatTextView collegeLocation = findViewById(R.id.college_info_location);
        collegeLocation.setText("Ravangla, South Sikkim");


        initializeQuickFacts();

        AppCompatTextView aboutCollege = findViewById(R.id.college_info_col_about);
        aboutCollege.setText("NIT (National Institute of Technology Sikkim is an institute of National Importance. It was established in the year 2010 as an autonomous institute.");

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

        final NestedScrollView scroller = (NestedScrollView) findViewById(R.id.nested_scroll_view);

        scroller.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                View view = (View) scroller.getChildAt(scroller.getChildCount() - 1);

                int diff = (view.getBottom() - (scroller.getHeight() + scroller
                        .getScrollY()));

                if (diff == 0) {
                   showRatingDialog();
                }
            }
        });


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
        ArrayList<CoursesOfferedModal> courses = new ArrayList<>();

        courses.add(new CoursesOfferedModal("Computer Science and Engineering"));
        courses.add(new CoursesOfferedModal("Electrical and Electronics Engineering"));
        courses.add(new CoursesOfferedModal("Electronics and Communications Engineering"));

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
        quickFactsList.add(new QuickFactsModal(
                "Type",
                "Institute of National Importance",
                R.drawable.outline_home_black_48)
        );
        quickFactsList.add(new QuickFactsModal(
                "Ownership",
                "Govt. of India",
                R.drawable.outline_person_black_48)
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


        //setGridViewHeightBasedOnChildren(quickFacts);

    }

    public void showRatingDialog()
    {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.college_rating_layout);
        dialog.setCancelable(true);
        WindowManager.LayoutParams wm = new WindowManager.LayoutParams();

        try {
            wm.copyFrom(dialog.getWindow().getAttributes());
            wm.width = WindowManager.LayoutParams.MATCH_PARENT;
            wm.height = WindowManager.LayoutParams.WRAP_CONTENT;

        }catch(NullPointerException e){
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
                Toast.makeText(CollegeInfoActivity.this, rating+" Sent", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.getWindow().setAttributes(wm);
    }

}
