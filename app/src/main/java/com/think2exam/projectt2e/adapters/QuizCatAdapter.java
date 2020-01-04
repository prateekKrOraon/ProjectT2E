package com.think2exam.projectt2e.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;
import com.think2exam.projectt2e.R;
import com.think2exam.projectt2e.modals.QuizCategoryModal;
import com.think2exam.projectt2e.ui.activities.CollegeListActivity;
import com.think2exam.projectt2e.ui.activities.QuizActivity;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;



public class QuizCatAdapter extends RecyclerView.Adapter<QuizCatAdapter.QuizCatViewHolder> {


    private ArrayList<QuizCategoryModal> QuizCatItems;
    private Context context;
    public static class QuizCatViewHolder extends RecyclerView.ViewHolder{

        public TextView name;
        public MaterialCardView materialCardView;
        public ImageView image;

        public QuizCatViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.quiz_cat_name);
            materialCardView = itemView.findViewById(R.id.quiz_cat_card_view);
            image = itemView.findViewById(R.id.quiz_cat_image);

        }

    }

    public QuizCatAdapter(ArrayList<QuizCategoryModal> arrayList, Context context)
    {
        this.QuizCatItems = arrayList;
        this.context = context;

    }

    @NonNull
    @Override
    public QuizCatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.quiz_cat_layout,parent,false);
        return new QuizCatViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull final QuizCatViewHolder holder, final int position) {


        holder.name.setText(QuizCatItems.get(position).getName());
        Glide.with(context)
                .load(context.getDrawable(QuizCatItems.get(position).getIcon()))
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
        return QuizCatItems.size();
    }




}