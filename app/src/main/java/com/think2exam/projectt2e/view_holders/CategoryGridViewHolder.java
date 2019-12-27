package com.think2exam.projectt2e.view_holders;

import android.view.View;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.think2exam.projectt2e.R;

public class CategoryGridViewHolder {

    public AppCompatImageView iconView;
    public AppCompatTextView titleView;

    public CategoryGridViewHolder(View view){
        iconView = view.findViewById(R.id.category_icon);
        titleView = view.findViewById(R.id.category_title);
    }

}
