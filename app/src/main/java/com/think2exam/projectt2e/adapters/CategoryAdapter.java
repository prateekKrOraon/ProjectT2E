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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.think2exam.projectt2e.R;
import com.think2exam.projectt2e.modals.CategoryModel;
import com.think2exam.projectt2e.ui.activities.CollegeListActivity;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;



public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {


    private ArrayList<CategoryModel> CategoryItems;
    private Context context;
    public static class CategoryViewHolder extends RecyclerView.ViewHolder{

        public TextView name;
        public FloatingActionButton floatingActionButton;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.category_name);
            floatingActionButton = itemView.findViewById(R.id.category_icon);


        }

    }

    public CategoryAdapter(ArrayList<CategoryModel> arrayList, Context context)
    {
        this.CategoryItems = arrayList;
        this.context = context;

    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_layout,parent,false);
        return new CategoryViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull final CategoryViewHolder holder, final int position) {



        holder.name.setText(CategoryItems.get(position).getName());
        Glide.with(context)
                .load(context.getDrawable(CategoryItems.get(position).getIcon()))
                .into(holder.floatingActionButton);
        holder.floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, CollegeListActivity.class);
                intent.putExtra("tag",CategoryItems.get(position).getName());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return CategoryItems.size();
    }




}