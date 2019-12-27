package com.think2exam.projectt2e.view_holders;

import android.view.View;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import com.think2exam.projectt2e.R;

public class QuickFactsViewHolder{
    public AppCompatTextView titleView;
    public AppCompatTextView subtitleView;
    public AppCompatImageView iconView;

    public QuickFactsViewHolder(View view){
        titleView = view.findViewById(R.id.quick_facts_title);
        subtitleView = view.findViewById(R.id.quick_facts_subtitle);
        iconView = view.findViewById(R.id.quick_facts_icon);
    }
}