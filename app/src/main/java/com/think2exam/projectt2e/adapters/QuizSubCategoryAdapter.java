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

import static com.think2exam.projectt2e.Constants.QUIZ_CATEGORY_ID;
import static com.think2exam.projectt2e.Constants.QUIZ_SUBJECT_ID;
import static com.think2exam.projectt2e.Constants.QUIZ_SUB_CAT;
import static com.think2exam.projectt2e.Constants.TITLE;

public class QuizSubCategoryAdapter extends RecyclerView.Adapter<QuizSubCategoryViewHolder> {

    Context context;
    ArrayList<QuizSubCategoryModel> list;
    String category;
    private int categoryID;
    public QuizSubCategoryAdapter(Context context, ArrayList<QuizSubCategoryModel> list,int categoryID,String category){
        this.context = context;
        this.list = list;
        this.categoryID = categoryID;
        this.category = category;
    }

    @NonNull
    @Override
    public QuizSubCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.quiz_cat_layout,parent,false);
        return new QuizSubCategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuizSubCategoryViewHolder holder, final int position) {
        holder.name.setText(list.get(position).subCat);
        Glide.with(context)
                .load(context.getDrawable(list.get(position).icon))
                .into(holder.image);
        holder.materialCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, QuizActivity.class);
                intent.putExtra(QUIZ_CATEGORY_ID,categoryID);
                intent.putExtra(QUIZ_SUBJECT_ID,list.get(position).id);
                intent.putExtra(TITLE,category+" | "+list.get(position).subCat);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
