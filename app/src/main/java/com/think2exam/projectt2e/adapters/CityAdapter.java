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
import com.think2exam.projectt2e.modals.CityModel;
import com.think2exam.projectt2e.modals.QuizCategoryModel;
import com.think2exam.projectt2e.ui.activities.CollegeListActivity;
import com.think2exam.projectt2e.ui.activities.QuizActivity;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;



public class CityAdapter extends RecyclerView.Adapter<CityAdapter.CityViewHolder> {


    private ArrayList<CityModel> CityItems;
    private Context context;
    public static class CityViewHolder extends RecyclerView.ViewHolder{

        public TextView name;
        public MaterialCardView materialCardView;
        public ImageView image;

        public CityViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.city_name);
            materialCardView = itemView.findViewById(R.id.city_card_view);
            image = itemView.findViewById(R.id.city_icon);

        }

    }

    public CityAdapter(ArrayList<CityModel> arrayList, Context context)
    {
        this.CityItems = arrayList;
        this.context = context;

    }

    @NonNull
    @Override
    public CityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.city_layout,parent,false);
        return new CityViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull final CityViewHolder holder, final int position) {


        holder.name.setText(CityItems.get(position).getName());
        Glide.with(context)
                .load(context.getDrawable(CityItems.get(position).getIcon()))
                .into(holder.image);
        holder.materialCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CollegeListActivity.class);
                intent.putExtra("tag",CityItems.get(position).getName());
                context.startActivity(intent);            }
        });
    }

    @Override
    public int getItemCount() {
        return CityItems.size();
    }




}
