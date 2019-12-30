package com.think2exam.projectt2e.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;
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

        Toolbar toolbar = findViewById(R.id.toolbar_college_info);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Info Screen");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


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
    }

    private void initializeCoursesOffered() {
        ArrayList<CoursesOfferedModal> courses = new ArrayList<>();

        courses.add(new CoursesOfferedModal("Computer Science and Engineering"));
        courses.add(new CoursesOfferedModal("Electrical and Electronics Engineering"));
        courses.add(new CoursesOfferedModal("Electronics and Communications Engineering"));

        CoursesOfferedAdapter adapter = new CoursesOfferedAdapter(this,courses);
        ListView coursesOffered = findViewById(R.id.offered_course_list);
        coursesOffered.setAdapter(adapter);

        setListViewHeightBasedOnChildren(coursesOffered);

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
        quickFacts.setAdapter(quickFactsAdapter);

    }

    //Measures the height of the courses offered listView
    public static void setListViewHeightBasedOnChildren(ListView listView) {

        ListAdapter listAdapter = listView.getAdapter();

        if (listAdapter == null){
            return;
        }

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;

        View view = null;for (int i = 0; i < listAdapter.getCount(); i++) {

            view = listAdapter.getView(i, view, listView);

            if (i == 0) {
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));
            }

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();

        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));

        listView.setLayoutParams(params);
        listView.requestLayout();

    }

}
