package com.think2exam.projectt2e.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;
import com.think2exam.projectt2e.R;
import com.think2exam.projectt2e.modals.CoursesOfferedModal;
import com.think2exam.projectt2e.modals.StateModel;
import com.think2exam.projectt2e.ui.activities.CollegeListActivity;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;


public class CoursesOfferedAdapter extends RecyclerView.Adapter<CoursesOfferedAdapter.CoursesOfferedVH> {


    private ArrayList<CoursesOfferedModal> coursesOfferedModals;
    private Context context;
    public static class CoursesOfferedVH extends RecyclerView.ViewHolder{

        public TextView name;

        public CoursesOfferedVH(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.course_list_modal_title);


        }

    }

    public CoursesOfferedAdapter(ArrayList<CoursesOfferedModal> arrayList, Context context)
    {
        this.coursesOfferedModals = arrayList;
        this.context = context;

    }

    @NonNull
    @Override
    public CoursesOfferedVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_list_modal,parent,false);
        return new CoursesOfferedVH(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull final CoursesOfferedVH holder, final int position) {


        holder.name.setText(coursesOfferedModals.get(position).getCourseName());

    }

    @Override
    public int getItemCount() {
        return coursesOfferedModals.size();
    }




}