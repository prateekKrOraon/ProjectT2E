package com.think2exam.projectt2e.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.think2exam.projectt2e.R;

public class CourseDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);
        ImageButton backBtn = findViewById(R.id.course_info_back_btn);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        AppCompatImageView imageView = findViewById(R.id.course_info_col_image);
        imageView.setImageDrawable(
                getResources()
                        .getDrawable(R.drawable.col_logo_default)
        );

        AppCompatTextView collegeName = findViewById(R.id.course_info_col_name);
        collegeName.setText("National Institute of Technology Sikkim");

        AppCompatTextView collegeLocation = findViewById(R.id.course_info_location);
        collegeLocation.setText("Ravangla, South Sikkim");

    }
}
