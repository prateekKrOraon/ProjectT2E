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
import com.think2exam.projectt2e.LogInActivity;
import com.think2exam.projectt2e.MainActivity;
import com.think2exam.projectt2e.PersonalizeActivity;
import com.think2exam.projectt2e.R;
import com.think2exam.projectt2e.modals.CityModel;
import com.think2exam.projectt2e.modals.PersonalizeModel;
import com.think2exam.projectt2e.ui.activities.CollegeListActivity;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;


public class PersonalizeAdapter extends RecyclerView.Adapter<PersonalizeAdapter.PersonalizeAdapterViewHolder> {


    private ArrayList<PersonalizeModel> personalizelist;
    private Context context;
    public static class PersonalizeAdapterViewHolder extends RecyclerView.ViewHolder{

        public TextView name;
        public ImageView image;
        public MaterialCardView materialCardView;

        public PersonalizeAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.personalize_name);
            image = itemView.findViewById(R.id.personalize_image);
            materialCardView = itemView.findViewById(R.id.pers_card_view);

        }

    }

    public PersonalizeAdapter(ArrayList<PersonalizeModel> arrayList, Context context)
    {
        this.personalizelist = arrayList;
        this.context = context;

    }

    @NonNull
    @Override
    public PersonalizeAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.personalise_recycler_view_item,parent,false);
        return new PersonalizeAdapterViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull final PersonalizeAdapterViewHolder holder, final int position) {

        //setting radius of card view
        DisplayMetrics displayMetrics = new DisplayMetrics();
        PersonalizeActivity.persActivity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        float size = displayMetrics.widthPixels;

        ViewGroup.LayoutParams layoutParams = holder.materialCardView.getLayoutParams();
        layoutParams.width=(int)size/4;
        //layoutParams.height=(int)size/4;


        holder.name.setText(context.getResources().getString(personalizelist.get(position).getName()));
        Glide.with(context)
                .load(personalizelist.get(position).getImage())
                .into(holder.image);


        holder.materialCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref = context.getApplicationContext().getSharedPreferences("MyPreference", 0); // 0 - for private mode
                SharedPreferences.Editor editor = pref.edit();
                editor.putInt("category_id", personalizelist.get(position).getName());
                editor.commit();
                PersonalizeActivity.persActivity.finish();

                if(true)//@prateek add user condition
                {
                    Intent intent = new Intent(context, LogInActivity.class);
                    context.startActivity(intent);
                }
                else
                {
                    Intent intent = new Intent(context,MainActivity.class);
                    context.startActivity(intent);
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return personalizelist.size();
    }




}