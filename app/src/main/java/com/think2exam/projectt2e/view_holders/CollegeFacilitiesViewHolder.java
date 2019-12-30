package com.think2exam.projectt2e.view_holders;

import android.view.View;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.think2exam.projectt2e.R;

public class CollegeFacilitiesViewHolder {

    public AppCompatImageView icon;
    public AppCompatTextView title;

    public CollegeFacilitiesViewHolder(View view){
        icon = view.findViewById(R.id.facilities_icon);
        title = view.findViewById(R.id.facilities_title);
    }
}
