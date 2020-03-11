package com.think2exam.projectt2e.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;
import com.think2exam.projectt2e.R;
import com.think2exam.projectt2e.modals.CollegeInfoModel;
import com.think2exam.projectt2e.modals.CollegeListModel;
import com.think2exam.projectt2e.ui.activities.CollegeInfoActivity;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;



public class CollegeListAdapter extends RecyclerView.Adapter<CollegeListAdapter.CollegeListViewHolder> {


    private ArrayList<CollegeListModel> CollegeListItems;
    private Context context;
    public static class CollegeListViewHolder extends RecyclerView.ViewHolder{

        public TextView name;
        public MaterialCardView materialCardView;
        public TextView location;
        public ImageView locIcon;

        public CollegeListViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.college_name);
            materialCardView = itemView.findViewById(R.id.college_card_view);
            location = itemView.findViewById(R.id.college_location_name);
            locIcon = itemView.findViewById(R.id.college_location_icon);
        }

    }

    public CollegeListAdapter(ArrayList<CollegeListModel> arrayList, Context context)
    {
        this.CollegeListItems = arrayList;
        this.context = context;

    }

    @NonNull
    @Override
    public CollegeListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.college_list_item,parent,false);
        return new CollegeListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CollegeListViewHolder holder, final int position) {


        holder.name.setText(CollegeListItems.get(position).getName());
        Glide.with(context)
                .load(context.getResources().getDrawable(R.drawable.location_color_primary_24dp))
                .into(holder.locIcon);
        holder.location.setText(CollegeListItems.get(position).getLocation());
        holder.materialCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, CollegeInfoActivity.class);
                intent.putExtra("id",CollegeListItems.get(position).getId());
                intent.putExtra("tableName",CollegeListItems.get(position).getTableName());
                context.startActivity(intent);
            }
        });



    }

    @Override
    public int getItemCount() {
        return CollegeListItems.size();
    }




}
