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
import com.think2exam.projectt2e.modals.StateModel;
import com.think2exam.projectt2e.ui.activities.CollegeListActivity;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;



public class StateAdapter extends RecyclerView.Adapter<StateAdapter.StateViewHolder> {


    private ArrayList<StateModel> StateItems;
    private Context context;
    public static class StateViewHolder extends RecyclerView.ViewHolder{

        public TextView name;
        public MaterialCardView materialCardView;
        public ImageView image;

        public StateViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.state_name);
            materialCardView = itemView.findViewById(R.id.state_card_view);
            image = itemView.findViewById(R.id.state_icon);

        }

    }

    public StateAdapter(ArrayList<StateModel> arrayList, Context context)
    {
        this.StateItems = arrayList;
        this.context = context;

    }

    @NonNull
    @Override
    public StateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.state_layout,parent,false);
        return new StateViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull final StateViewHolder holder, final int position) {


        holder.name.setText(context.getResources().getString(StateItems.get(position).getName()));
        Glide.with(context)
                .load(context.getDrawable(StateItems.get(position).getIcon()))
                .into(holder.image);
        holder.materialCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref = context.getApplicationContext().getSharedPreferences("MyPreference", 0); // 0 - for private mode
                int catId = pref.getInt("category_id",-1);


                Intent intent = new Intent(context, CollegeListActivity.class);
                intent.putExtra("which","state");
                intent.putExtra("query",StateItems.get(position).getName());
                intent.putExtra("catId",catId);
                if(catId == R.string.university)
                {
                    intent.putExtra("title",context.getString(R.string.university)+ " in "+context.getString(StateItems.get(position).getName()));

                }
                else
                {
                    intent.putExtra("title",context.getString(catId)+ " Colleges in "+context.getString(StateItems.get(position).getName()));

                }

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return StateItems.size();
    }




}