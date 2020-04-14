package com.think2exam.projectt2e.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;
import com.think2exam.projectt2e.MainActivity;
import com.think2exam.projectt2e.R;
import com.think2exam.projectt2e.modals.CityModel;
import com.think2exam.projectt2e.modals.EditProfileModel;
import com.think2exam.projectt2e.ui.activities.CollegeListActivity;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;


public class EditProfileAdapter extends RecyclerView.Adapter<EditProfileAdapter.EditProfileVH> {


    private ArrayList<EditProfileModel> editProfileModels;
    private Context context;

    public onItemClickListener onItemClickListener;

    public interface onItemClickListener{
        public void onItemClick(int pos);
    }
    public static class EditProfileVH extends RecyclerView.ViewHolder{

        public ImageView icon,edit;
        public TextView name;
        public TextView field;

        public EditProfileVH(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.item_name);
            field = itemView.findViewById(R.id.item_field);
            icon = itemView.findViewById(R.id.item_icon);
            edit = itemView.findViewById(R.id.item_edit);
        }

    }

    public EditProfileAdapter(ArrayList<EditProfileModel> arrayList, Context context,onItemClickListener listener)
    {
        this.editProfileModels = arrayList;
        this.context = context;
        onItemClickListener = listener;

    }

    @NonNull
    @Override
    public EditProfileVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.edit_profile_item,parent,false);
        return new EditProfileVH(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull final EditProfileVH holder, final int position) {

        holder.name.setText(context.getResources().getString(editProfileModels.get(position).getItemName()));
        holder.field.setText(editProfileModels.get(position).getItemField());
        Glide.with(context)
                .load(context.getDrawable(editProfileModels.get(position).getItemIcon()))
                .into(holder.icon);
        if(position==editProfileModels.size()-1){
            holder.edit.setVisibility(View.GONE);
        }
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return editProfileModels.size();
    }




}