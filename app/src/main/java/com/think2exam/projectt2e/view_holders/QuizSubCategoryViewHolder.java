package com.think2exam.projectt2e.view_holders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.think2exam.projectt2e.R;

public class QuizSubCategoryViewHolder extends RecyclerView.ViewHolder {
    public TextView name;
    public MaterialCardView materialCardView;
    public ImageView image;

    public QuizSubCategoryViewHolder(@NonNull View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.quiz_cat_name);
        materialCardView = itemView.findViewById(R.id.quiz_cat_card_view);
        image = itemView.findViewById(R.id.quiz_cat_image);

    }
}
