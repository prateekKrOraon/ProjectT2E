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
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.think2exam.projectt2e.MainActivity;
import com.think2exam.projectt2e.R;
import com.think2exam.projectt2e.modals.FeaturedCollegeModel;
import com.think2exam.projectt2e.ui.activities.CollegeInfoActivity;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.RecyclerView;

import static com.think2exam.projectt2e.Constants.ID;
import static com.think2exam.projectt2e.Constants.TABLE_ID;

public class FeaturedCollegeAdapter extends RecyclerView.Adapter<FeaturedCollegeAdapter.TopCollegeSliderViewHolder> {



    public ArrayList<FeaturedCollegeModel> featuredCollegeModels;
    public Context mainActivityContext;
    public static class TopCollegeSliderViewHolder extends RecyclerView.ViewHolder{
        public LinearLayout item;
        public CoordinatorLayout progress;
        public LinearLayout explore_btn;
        public TextView college_name;
        public TextView location,rank;
        public ImageView logo;

        public TopCollegeSliderViewHolder(@NonNull View itemView){
            super(itemView);
            item = itemView.findViewById(R.id.item_layout_fl);
            progress = itemView.findViewById(R.id.progress_bar_layout_fl);
            explore_btn = itemView.findViewById(R.id.explore_btn_fl);
            college_name = itemView.findViewById(R.id.college_name_fl);
            location = itemView.findViewById(R.id.location_fl);
            rank = itemView.findViewById(R.id.rank_fl);
            logo = itemView.findViewById(R.id.logo_fl);
        }
    }

    public FeaturedCollegeAdapter(ArrayList<FeaturedCollegeModel> featuredCollegeModels, Context context)
    {
        this.featuredCollegeModels = featuredCollegeModels;
        mainActivityContext = context;

    }

    @NonNull
    @Override
    public TopCollegeSliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.featured_college_layout,parent,false);
        return new TopCollegeSliderViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull TopCollegeSliderViewHolder holder, final int position) {

        DisplayMetrics displayMetrics = new DisplayMetrics();
        MainActivity.activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        float size = displayMetrics.widthPixels;
        ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
        layoutParams.width=(int)size*4/5;


        if(featuredCollegeModels.size()==0){
            holder.progress.setVisibility(View.VISIBLE);
            holder.item.setVisibility(View.GONE);
        }else {
            holder.progress.setVisibility(View.GONE);
            holder.item.setVisibility(View.VISIBLE);
            setItemLayout(holder,position);
        }
    }

    public void setItemLayout(TopCollegeSliderViewHolder holder, final int position){
        holder.college_name.setSingleLine(true);
        holder.college_name.setSelected(true);
        holder.college_name.setText(featuredCollegeModels.get(position).getName());
        holder.location.setText(featuredCollegeModels.get(position).getLocation());
        holder.rank.setText(featuredCollegeModels.get(position).getRank());
        Glide.with(mainActivityContext)
                .load(mainActivityContext.getDrawable(featuredCollegeModels.get(position).getLogo()))
                .into(holder.logo);


        holder.explore_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mainActivityContext, CollegeInfoActivity.class);
                intent.putExtra(ID,featuredCollegeModels.get(position).getId());
                intent.putExtra(TABLE_ID,featuredCollegeModels.get(position).getCatId());
                mainActivityContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return featuredCollegeModels.size()==0?5:featuredCollegeModels.size();
    }



}