package com.think2exam.projectt2e.view_holders;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.think2exam.projectt2e.R;

public class ProfileOptionsViewHolder extends RecyclerView.ViewHolder{

    public AppCompatImageView iconView;
    public AppCompatTextView titleView;
    public LinearLayoutCompat profileOption;

    public ProfileOptionsViewHolder(@NonNull View itemView) {
        super(itemView);
        iconView = itemView.findViewById(R.id.profile_options_icon);
        titleView = itemView.findViewById(R.id.profile_options_title);
        profileOption = itemView.findViewById(R.id.profile_option);
    }
}
