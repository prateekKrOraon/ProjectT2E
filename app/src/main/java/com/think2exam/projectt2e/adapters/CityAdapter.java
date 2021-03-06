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
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;
import com.think2exam.projectt2e.MainActivity;
import com.think2exam.projectt2e.R;
import com.think2exam.projectt2e.modals.CityModel;
import com.think2exam.projectt2e.ui.activities.CollegeListActivity;

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
        DisplayMetrics displayMetrics = new DisplayMetrics();
        MainActivity.activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        float size = displayMetrics.widthPixels;
        ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
        layoutParams.width=(int)size*1/3;

        holder.name.setText(context.getResources().getString(CityItems.get(position).getName()));
        Glide.with(context)
                .load(context.getDrawable(CityItems.get(position).getIcon()))
                .into(holder.image);
        holder.materialCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                SharedPreferences pref = context.getApplicationContext().getSharedPreferences("MyPreference", 0); // 0 - for private mode
                int catId = pref.getInt("category_id",-1);

                Intent intent = new Intent(context, CollegeListActivity.class);
                intent.putExtra("which","city");
                intent.putExtra("query",CityItems.get(position).getName());
                intent.putExtra("catId",catId);
                if(catId==R.string.university)
                {
                    intent.putExtra("title",context.getString(R.string.university)+ " in "+context.getString(CityItems.get(position).getName()));

                }
                else
                {
                    intent.putExtra("title",context.getString(catId)+ " Colleges in "+context.getString(CityItems.get(position).getName()));

                }

                context.startActivity(intent);          }
        });
    }

    @Override
    public int getItemCount() {
        return CityItems.size();
    }




}