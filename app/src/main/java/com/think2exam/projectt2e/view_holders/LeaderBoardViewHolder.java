package com.think2exam.projectt2e.view_holders;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.think2exam.projectt2e.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class LeaderBoardViewHolder extends RecyclerView.ViewHolder {

    public CircleImageView imageView;
    public AppCompatTextView name;
    public AppCompatTextView level;
    public AppCompatTextView rank;

    public LeaderBoardViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.leader_board_image);
        name = itemView.findViewById(R.id.leader_board_name);
        level = itemView.findViewById(R.id.leader_board_level);
        rank = itemView.findViewById(R.id.leader_board_rank);
    }
}
