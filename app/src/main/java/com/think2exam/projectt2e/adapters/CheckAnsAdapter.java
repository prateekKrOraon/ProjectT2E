package com.think2exam.projectt2e.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.think2exam.projectt2e.R;
import com.think2exam.projectt2e.modals.QuestionModel;
import com.think2exam.projectt2e.view_holders.CheckAnsViewHolder;

import java.util.ArrayList;

public class CheckAnsAdapter extends RecyclerView.Adapter<CheckAnsViewHolder> {

    private ArrayList<QuestionModel> questions;
    private Context context;

    public CheckAnsAdapter(ArrayList<QuestionModel> questions, Context context) {
        this.questions = questions;
        this.context = context;
    }

    @NonNull
    @Override
    public CheckAnsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.correct_ans_card,parent,false);
        return new CheckAnsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CheckAnsViewHolder holder, int position) {

        QuestionModel model = questions.get(position);
        holder.questionView.setText(model.question);
        switch (model.answerKey){
            case 1:
                holder.ansView.setText(model.option1);
                break;
            case 2:
                holder.ansView.setText(model.option2);
                break;
            case 3:
                holder.ansView.setText(model.option3);
                break;
            case 4:
                holder.ansView.setText(model.option4);
                break;
            case 5:
                holder.ansView.setText(model.option5);
                break;
            default:
                Toast.makeText(context,"Answer not availabel for position "+position,Toast.LENGTH_SHORT).show();
        }

        holder.desView.setText(model.description);

    }

    @Override
    public int getItemCount() {
        return questions.size();
    }
}
