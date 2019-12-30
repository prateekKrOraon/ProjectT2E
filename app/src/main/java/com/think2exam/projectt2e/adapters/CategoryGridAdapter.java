package com.think2exam.projectt2e.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;

import com.think2exam.projectt2e.R;
import com.think2exam.projectt2e.modals.CategoryModal;
import com.think2exam.projectt2e.ui.activities.QuizActivity;
import com.think2exam.projectt2e.view_holders.CategoryGridViewHolder;

import java.util.ArrayList;

public class CategoryGridAdapter extends BaseAdapter {

    ArrayList<CategoryModal> list;
    Context context;

    public CategoryGridAdapter(Context context, ArrayList<CategoryModal> list){
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
    public View getView(final int position, final View convertView, final ViewGroup parent) {

        View view = convertView;
        CategoryGridViewHolder holder;

        if(view == null){

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.category_grid_modal,parent,false);

            holder = new CategoryGridViewHolder(view);
            view.setTag(holder);

        }else{

            holder = (CategoryGridViewHolder) view.getTag();

        }

        CategoryModal temp = list.get(position);
        holder.titleView.setText(temp.title);
        holder.iconView.setImageResource(temp.iconId);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"Position " + position,Toast.LENGTH_LONG).show();
                context.startActivity(new Intent(context, QuizActivity.class));
            }
        });

        return view;
    }
}
