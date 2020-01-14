package com.think2exam.projectt2e.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.think2exam.projectt2e.R;
import com.think2exam.projectt2e.modals.LeaderBoardModal;
import com.think2exam.projectt2e.view_holders.LeaderBoardViewHolder;

import java.util.ArrayList;

public class LeaderBoardAdapter extends RecyclerView.Adapter<LeaderBoardViewHolder> {

    ArrayList<LeaderBoardModal> list;
    Context context;

    public LeaderBoardAdapter(Context context, ArrayList<LeaderBoardModal> list){
        this.context = context;
        this.list = list;
    };

    @NonNull
    @Override
    public LeaderBoardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.leaderboard_item,parent,false);
        return new LeaderBoardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LeaderBoardViewHolder holder, int position) {
        holder.rank.setText(list.get(position).rank);
        holder.name.setText(list.get(position).name);
        holder.level.setText(list.get(position).level);
        Glide.with(context).load(context.getDrawable(list.get(position).image)).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
