package com.think2exam.projectt2e.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.think2exam.projectt2e.MainActivity;
import com.think2exam.projectt2e.R;
import com.think2exam.projectt2e.modals.FeaturedCollegeModel;
import com.think2exam.projectt2e.modals.QuizCategoryModal;
import com.think2exam.projectt2e.ui.activities.CollegeInfoActivity;
import com.think2exam.projectt2e.ui.activities.QuizSubCategoryActivity;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.RecyclerView;

import static com.think2exam.projectt2e.Constants.ID;
import static com.think2exam.projectt2e.Constants.QUIZ_CATEGORY_ID;
import static com.think2exam.projectt2e.Constants.TABLE_ID;
import static com.think2exam.projectt2e.Constants.TITLE;

public class QuizCatAdapterHome extends RecyclerView.Adapter<QuizCatAdapterHome.QuizCatAdapterHomeVH> {



    public ArrayList<QuizCategoryModal> quizCategoryModals;
    public Context mainActivityContext;
    public static class QuizCatAdapterHomeVH extends RecyclerView.ViewHolder{
        public LinearLayout item;
        public ProgressBar progress;
        public ImageView image;
        public TextView name;

        public QuizCatAdapterHomeVH(@NonNull View itemView){
            super(itemView);
            item = itemView.findViewById(R.id.item_layout_quiz_home);
            progress = itemView.findViewById(R.id.progress_quiz_home);
            image = itemView.findViewById(R.id.quiz_cat_image_home);
            name = itemView.findViewById(R.id.quiz_cat_name_home);

        }
    }

    public QuizCatAdapterHome(ArrayList<QuizCategoryModal> featuredCollegeModels, Context context)
    {
        this.quizCategoryModals = featuredCollegeModels;
        mainActivityContext = context;

    }

    @NonNull
    @Override
    public QuizCatAdapterHomeVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.featured_quiz_item,parent,false);
        return new QuizCatAdapterHomeVH(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull QuizCatAdapterHomeVH holder, final int position) {

        DisplayMetrics displayMetrics = new DisplayMetrics();
        MainActivity.activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        float size = displayMetrics.widthPixels;
        ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
        layoutParams.width=(int)size*1/3;
        layoutParams.height=(int)size*1/3;


        if(quizCategoryModals.size()==0){
            holder.progress.setVisibility(View.VISIBLE);
            holder.item.setVisibility(View.GONE);
        }else {
            holder.progress.setVisibility(View.GONE);
            holder.item.setVisibility(View.VISIBLE);
            setItemLayout(holder,position);
        }
    }

    public void setItemLayout(QuizCatAdapterHomeVH holder, final int position){
        holder.name.setText(quizCategoryModals.get(position).name);
        Glide.with(mainActivityContext)
                .load(mainActivityContext.getDrawable(quizCategoryModals.get(position).icon))
                .into(holder.image);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mainActivityContext, QuizSubCategoryActivity.class);
                intent.putExtra(QUIZ_CATEGORY_ID, quizCategoryModals.get(position).id);
                intent.putExtra(TITLE,quizCategoryModals.get(position).name);
                mainActivityContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return quizCategoryModals.size()==0?5:quizCategoryModals.size();
    }



}