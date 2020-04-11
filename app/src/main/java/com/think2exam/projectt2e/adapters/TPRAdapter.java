package com.think2exam.projectt2e.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.think2exam.projectt2e.R;
import com.think2exam.projectt2e.modals.TPRModel;
import com.think2exam.projectt2e.view_holders.TPRViewHolder;

import java.util.ArrayList;

public class TPRAdapter extends RecyclerView.Adapter<TPRViewHolder> {

    private ArrayList<TPRModel> list;
    private Context context;

    public TPRAdapter(ArrayList<TPRModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public TPRViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.tpr_layout,parent,false);

        return new TPRViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TPRViewHolder holder, int position) {

        final TPRModel model = list.get(position);

        holder.title.setText(model.title);
        holder.copyrightInfo.setText(model.copyrightInfo);
        holder.link.setText(model.link);
        holder.link.setPaintFlags(holder.link.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        holder.licAbs.setText(model.licAbs);
        holder.licLink.setText(model.licLink);
        holder.licLink.setPaintFlags(holder.licLink.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        holder.licAL.setText(model.licAl);

        holder.link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchBrowser = new Intent(Intent.ACTION_VIEW, Uri.parse(model.link));
                context.startActivity(launchBrowser);
            }
        });

        holder.licLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!model.licLink.equals("")){
                    Intent launchBrowser = new Intent(Intent.ACTION_VIEW,Uri.parse(model.licLink));
                    context.startActivity(launchBrowser);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
