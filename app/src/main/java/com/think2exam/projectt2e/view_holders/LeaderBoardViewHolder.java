package com.think2exam.projectt2e.view_holders;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.think2exam.projectt2e.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class LeaderBoardViewHolder extends RecyclerView.ViewHolder {

    public CircleImageView imageView;
    public AppCompatTextView name;
    public AppCompatTextView points;
    public AppCompatTextView rank;
    public CardView cardView;

    public LeaderBoardViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.leader_board_image);
        name = itemView.findViewById(R.id.leader_board_name);
        points = itemView.findViewById(R.id.leader_board_level);
        rank = itemView.findViewById(R.id.leader_board_rank);
        cardView = itemView.findViewById(R.id.leaderboard_player_card);
    }
}
