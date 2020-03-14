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
import com.think2exam.projectt2e.modals.PrestigiousCollegeModel;
import com.think2exam.projectt2e.ui.activities.CollegeListActivity;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;



public class PrestigiousCollegeAdapter extends RecyclerView.Adapter<PrestigiousCollegeAdapter.TopCollegeViewHolder> {


    private ArrayList<PrestigiousCollegeModel> TopCollegeItems;
    private Context context;

    SharedPreferences pref; // 0 - for private mode
    int catId;

    public static class TopCollegeViewHolder extends RecyclerView.ViewHolder{

        public TextView name;
        public MaterialCardView materialCardView;
        public ImageView image;

        public TopCollegeViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.top_college_name);
            materialCardView = itemView.findViewById(R.id.top_college_card_view);
            image = itemView.findViewById(R.id.top_college_icon);

        }

    }

    public PrestigiousCollegeAdapter(ArrayList<PrestigiousCollegeModel> arrayList, Context context)
    {
        this.TopCollegeItems = arrayList;
        this.context = context;
        pref = this.context.getSharedPreferences("MyPreference", 0);
        catId = pref.getInt("category_id", -1);

    }

    @NonNull
    @Override
    public TopCollegeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.top_college_layout,parent,false);
        return new TopCollegeViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull final TopCollegeViewHolder holder, final int position) {


        holder.name.setText(TopCollegeItems.get(position).getName());
        holder.name.setSelected(true);
        holder.name.setSingleLine();
        Glide.with(context)
                .load(context.getDrawable(TopCollegeItems.get(position).getIcon()))
                .into(holder.image);



        holder.materialCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Intent intent = new Intent(context, CollegeListActivity.class);
                intent.putExtra("which","prestigious_college");
                intent.putExtra("query",TopCollegeItems.get(position).getName());
                intent.putExtra("catId",TopCollegeItems.get(position).getCatId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return TopCollegeItems.size();
    }




}