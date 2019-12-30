package com.think2exam.projectt2e.view_holders;

import android.view.View;

import androidx.appcompat.widget.AppCompatTextView;

import com.think2exam.projectt2e.R;

public class CoursesOfferedViewHolder{
    public AppCompatTextView title;

    public CoursesOfferedViewHolder(View view){
        title = view.findViewById(R.id.course_list_modal_title);
    }
}