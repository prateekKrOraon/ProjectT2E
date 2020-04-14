package com.think2exam.projectt2e.view_holders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.think2exam.projectt2e.R;

public class CheckAnsViewHolder extends RecyclerView.ViewHolder {

    public TextView questionView;
    public TextView ansView;
    public TextView desView;

    public CheckAnsViewHolder(@NonNull View itemView) {
        super(itemView);
        questionView = itemView.findViewById(R.id.check_ans_question);
        ansView = itemView.findViewById(R.id.check_ans_ans);
        desView = itemView.findViewById(R.id.check_ans_des);
    }
}
