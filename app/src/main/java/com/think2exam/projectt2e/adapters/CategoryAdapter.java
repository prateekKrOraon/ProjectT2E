package com.think2exam.projectt2e.adapters;

import android.content.Context;
import android.content.Intent;
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
import com.think2exam.projectt2e.modals.CategoryModel;
import com.think2exam.projectt2e.ui.activities.CollegeListActivity;
import com.think2exam.projectt2e.utility.CompleteTableQuery;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;



public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {


    private ArrayList<CategoryModel> CategoryItems;
    private Context context;
    private String reqURL="";
    private int tableName;

    public static class CategoryViewHolder extends RecyclerView.ViewHolder{

        public TextView name;
        public MaterialCardView category_card;
        public ImageView category_image;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.category_name);
            category_card = itemView.findViewById(R.id.category_card);
            category_image = itemView.findViewById(R.id.category_image);

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
                .into(holder.category_image);

        //setting radius of card view
        DisplayMetrics displayMetrics = new DisplayMetrics();
        MainActivity.activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        float size = displayMetrics.widthPixels;
        float radius = size/10;
        ViewGroup.LayoutParams layoutParams = holder.category_card.getLayoutParams();
        layoutParams.width=(int)size/5;
        layoutParams.height=(int)size/5;
        holder.category_card.setRadius(radius);



        holder.category_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                reqURL="";
                tableName = CategoryItems.get(position).getName();



                if(reqURL!=null){

                    Intent intent = new Intent(context, CollegeListActivity.class);
                    intent.putExtra("which","category");
                    intent.putExtra("tag",CategoryItems.get(position).getName());
                    intent.putExtra("tableName",tableName);
                    context.startActivity(intent);

                }

    }
});
    }

    @Override
    public int getItemCount() {
        return CategoryItems.size();
    }





}