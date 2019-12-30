package com.think2exam.projectt2e.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.think2exam.projectt2e.R;
import com.think2exam.projectt2e.modals.CollegeFacilitiesModal;
import com.think2exam.projectt2e.view_holders.CollegeFacilitiesViewHolder;

import java.util.ArrayList;

public class CollegeFacilitiesAdapter extends BaseAdapter {
    ArrayList<CollegeFacilitiesModal> list;
    Context context;

    public CollegeFacilitiesAdapter(Context context, ArrayList<CollegeFacilitiesModal> list){
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        CollegeFacilitiesViewHolder holder;
        if(view == null){

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.facilities_modal,parent,false);
            holder = new CollegeFacilitiesViewHolder(view);
            view.setTag(holder);

        }else{
            holder = (CollegeFacilitiesViewHolder) view.getTag();
        }

        CollegeFacilitiesModal temp = list.get(position);
        holder.icon.setImageResource(temp.iconId);
        holder.title.setText(temp.title);

        return view;
    }
}
