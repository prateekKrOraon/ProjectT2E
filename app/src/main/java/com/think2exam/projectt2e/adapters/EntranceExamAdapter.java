package com.think2exam.projectt2e.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.card.MaterialCardView;
import com.think2exam.projectt2e.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;



public class EntranceExamAdapter extends RecyclerView.Adapter<EntranceExamAdapter.EntranceExamViewHolder> {


    private ArrayList<String> entranceExamArrayList;

    OnEnExamItemClickListener onEnExamItemClickListener;
    public interface OnEnExamItemClickListener{
        public void onItemClicked(String examName);
    }

    public static class EntranceExamViewHolder extends RecyclerView.ViewHolder{

        public TextView name;
        MaterialCardView materialCardView;

        public EntranceExamViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.entrance_exam_name);
            materialCardView = itemView.findViewById(R.id.en_exam_cardview);

        }

    }

    public EntranceExamAdapter(ArrayList<String> entranceExamArrayList,OnEnExamItemClickListener onEnExamItemClickListener)
    {
        this.entranceExamArrayList = entranceExamArrayList;
        this.onEnExamItemClickListener = onEnExamItemClickListener;
    }

    @NonNull
    @Override
    public EntranceExamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.entrance_exam_list_item,parent,false);
        return new EntranceExamViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final EntranceExamViewHolder holder, int position) {

        holder.name.setText(entranceExamArrayList.get(position));
        holder.materialCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onEnExamItemClickListener.onItemClicked(holder.name.getText().toString());
            }
        });
    }

    @Override
    public int getItemCount() {
        return entranceExamArrayList.size();
    }







}
