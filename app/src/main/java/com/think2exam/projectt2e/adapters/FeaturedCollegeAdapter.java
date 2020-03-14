package com.think2exam.projectt2e.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.think2exam.projectt2e.R;
import com.think2exam.projectt2e.modals.FeaturedCollegeModel;
import com.think2exam.projectt2e.ui.activities.CollegeInfoActivity;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import static com.think2exam.projectt2e.Constants.ID;
import static com.think2exam.projectt2e.Constants.TABLE_ID;

public class FeaturedCollegeAdapter extends RecyclerView.Adapter<FeaturedCollegeAdapter.TopCollegeSliderViewHolder> {



    public ArrayList<FeaturedCollegeModel> featuredCollegeModels;
    public Context mainActivityContext;
    public static class TopCollegeSliderViewHolder extends RecyclerView.ViewHolder{
        public LinearLayout explore_btn;
        public TextView college_name;
        public TextView location,rank;
        public ImageView logo;

        public TopCollegeSliderViewHolder(@NonNull View itemView){
            super(itemView);
            explore_btn = itemView.findViewById(R.id.top_college_explore_btn);
            college_name = itemView.findViewById(R.id.college_name_);
            location = itemView.findViewById(R.id.location_);
            rank = itemView.findViewById(R.id.rank_);
            logo = itemView.findViewById(R.id.logo_);
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
                SharedPreferences pref = mainActivityContext.getSharedPreferences("MyPreference", 0); // 0 - for private mode
                SharedPreferences.Editor editor = pref.edit();
                int catId = pref.getInt("category_id",-1);
                String tableId;
                switch (catId) {
                    case R.string.engineering:
                        tableId = "engineering_colleges";
                        break;
                    case R.string.agriculture:
                        tableId = "agriculture_colleges";
                        break;
                    case R.string.management:
                        tableId = "mba_colleges";
                        break;
                    case R.string.medical_and_dental:
                        tableId = "medical_and_dental_colleges";
                        break;
                    case R.string.pharmacy:
                        tableId = "pharmacy_colleges";
                        break;
                    case R.string.nursing_and_paramedical:
                        tableId = "nursing_and_paramedical_colleges";
                        break;
                    case R.string.education:
                        tableId = "education";
                        break;
                    default:
                        tableId = "university";
                        break;
                }
                Intent intent = new Intent(mainActivityContext, CollegeInfoActivity.class);
                intent.putExtra(ID,featuredCollegeModels.get(position).getId());
                intent.putExtra(TABLE_ID,tableId);
                mainActivityContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return featuredCollegeModels.size();
    }



}