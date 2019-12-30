package com.think2exam.projectt2e.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;
import com.think2exam.projectt2e.R;
import com.think2exam.projectt2e.modals.SearchCatItem;
import com.think2exam.projectt2e.ui.activities.CollegeListActivity;
import com.think2exam.projectt2e.ui.activities.SearchCategoryActivity;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;



public class SearchCatAdapter extends RecyclerView.Adapter<SearchCatAdapter.SearchCatViewHolder> {


    private ArrayList<SearchCatItem> searchCatItems;
    private Context context;
    public static class SearchCatViewHolder extends RecyclerView.ViewHolder{

        public TextView name;
        public ImageView icon;
        public MaterialCardView materialCardView;

        public SearchCatViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.cat_name);
            icon = itemView.findViewById(R.id.cat_image);
            materialCardView = itemView.findViewById(R.id.cat_card_view);

        }

    }

    public SearchCatAdapter(ArrayList<SearchCatItem> arrayList, Context context)
    {
        this.searchCatItems = arrayList;
        this.context = context;

    }

    @NonNull
    @Override
    public SearchCatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_category_layout,parent,false);
        return new SearchCatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final SearchCatViewHolder holder, final int position) {


       holder.name.setText(searchCatItems.get(position).getName());

       Glide.with(context)
                .load(searchCatItems.get(position).getIcon())
                .into(holder.icon);
       holder.materialCardView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               setOnClicked(position);
           }
       });
    }

    @Override
    public int getItemCount() {
        return searchCatItems.size();
    }

    public void setOnClicked(int position)
    {
        if(position==0)
        {
            Intent intent = new Intent(context, CollegeListActivity.class);
            intent.putExtra("tag",searchCatItems.get(position).getName());
            context.startActivity(intent);
        }
        else
        {
            Intent intent = new Intent(context, SearchCategoryActivity.class);
            intent.putExtra("cat_name",searchCatItems.get(position).getName());
            context.startActivity(intent);
        }
    }



}
