package com.think2exam.projectt2e.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.think2exam.projectt2e.R;
import com.think2exam.projectt2e.modals.QuizSubCategoryModel;
import com.think2exam.projectt2e.ui.activities.QuizActivity;
import com.think2exam.projectt2e.view_holders.QuizSubCategoryViewHolder;

import java.util.ArrayList;

public class QuizSubCategoryAdapter extends RecyclerView.Adapter<QuizSubCategoryViewHolder> {

    Context context;
    ArrayList<QuizSubCategoryModel> list;
    public QuizSubCategoryAdapter(Context context, ArrayList<QuizSubCategoryModel> list){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public QuizSubCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.quiz_cat_layout,parent,false);
        return new QuizSubCategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuizSubCategoryViewHolder holder, int position) {
        holder.name.setText(list.get(position).subCat);
        Glide.with(context)
                .load(context.getDrawable(list.get(position).icon))
                .into(holder.image);
        holder.materialCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, QuizActivity.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
