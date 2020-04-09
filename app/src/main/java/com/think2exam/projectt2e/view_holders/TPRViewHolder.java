package com.think2exam.projectt2e.view_holders;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.think2exam.projectt2e.R;

public class TPRViewHolder extends RecyclerView.ViewHolder {

    public AppCompatTextView title;
    public AppCompatTextView copyrightInfo;
    public AppCompatTextView link;
    public AppCompatTextView licAbs;
    public AppCompatTextView licLink;
    public AppCompatTextView licAL;

    public TPRViewHolder(@NonNull View itemView) {
        super(itemView);
        this.title = itemView.findViewById(R.id.tpr_title);
        this.copyrightInfo = itemView.findViewById(R.id.tpr_copyright_info);
        this.link = itemView.findViewById(R.id.tpr_link);
        this.licAbs = itemView.findViewById(R.id.tpr_lic_provider_abs);
        this.licLink = itemView.findViewById(R.id.tpr_lic_pro_link);
        this.licAL = itemView.findViewById(R.id.tpr_lic_pro_al);
    }
}
