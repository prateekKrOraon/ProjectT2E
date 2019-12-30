package com.think2exam.projectt2e.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.card.MaterialCardView;
import com.think2exam.projectt2e.R;
import com.think2exam.projectt2e.ui.activities.CollegeListActivity;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;



public class SearchCatItemAdapter extends RecyclerView.Adapter<SearchCatItemAdapter.SearchCatItemViewHolder> {


    private ArrayList<String> searchCatItems;
    private Context context;
    public static class SearchCatItemViewHolder extends RecyclerView.ViewHolder{

        public TextView name;
        public MaterialCardView materialCardView;

        public SearchCatItemViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.search_cat_item_name);
            materialCardView = itemView.findViewById(R.id.search_cat_item_card_view);

        }

    }

    public SearchCatItemAdapter(ArrayList<String> arrayList, Context context)
    {
        this.searchCatItems = arrayList;
        this.context = context;

    }

    @NonNull
    @Override
    public SearchCatItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_category_item_layout,parent,false);
        return new SearchCatItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final SearchCatItemViewHolder holder, final int position) {


        holder.name.setText(searchCatItems.get(position));
        holder.materialCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CollegeListActivity.class);
                intent.putExtra("tag",searchCatItems.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return searchCatItems.size();
    }




}
