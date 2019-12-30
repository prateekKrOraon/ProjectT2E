package com.think2exam.projectt2e.ui.home.top_college;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.think2exam.projectt2e.R;
import com.think2exam.projectt2e.ui.activities.CollegeInfoActivity;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TopCollegeSliderAdapter extends RecyclerView.Adapter<TopCollegeSliderAdapter.TopCollegeSliderViewHolder> {



    public ArrayList<String> TopCollegeArrayList;
    public Context mainActivityContext;
    public static class TopCollegeSliderViewHolder extends RecyclerView.ViewHolder{
        public LinearLayout explore_btn;
        public TextView college_name;

        public TopCollegeSliderViewHolder(@NonNull View itemView){
            super(itemView);
            explore_btn = itemView.findViewById(R.id.top_college_explore_btn);
            college_name = itemView.findViewById(R.id.college_name_);
        }
    }

    public TopCollegeSliderAdapter(ArrayList<String> TopCollegeArrayList, Context context)
    {
        this.TopCollegeArrayList = TopCollegeArrayList;
        mainActivityContext = context;

    }

    @NonNull
    @Override
    public TopCollegeSliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.top_colleges_slider_layout,parent,false);
        return new TopCollegeSliderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopCollegeSliderViewHolder holder, int position) {

        holder.college_name.setSingleLine(true);
        holder.college_name.setSelected(true);
        holder.explore_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mainActivityContext, CollegeInfoActivity.class);
                mainActivityContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return TopCollegeArrayList.size();
    }



}
