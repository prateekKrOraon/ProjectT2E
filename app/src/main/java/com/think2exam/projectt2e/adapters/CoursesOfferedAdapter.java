package com.think2exam.projectt2e.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatTextView;

import com.think2exam.projectt2e.R;
import com.think2exam.projectt2e.modals.CoursesOfferedModal;
import com.think2exam.projectt2e.ui.activities.CourseDetailsActivity;
import com.think2exam.projectt2e.view_holders.CoursesOfferedViewHolder;

import java.util.ArrayList;

public class    CoursesOfferedAdapter extends BaseAdapter {
    ArrayList<CoursesOfferedModal> list;
    Context context;
    public CoursesOfferedAdapter(Context context, ArrayList<CoursesOfferedModal> list){
        this.list = list;
        this.context = context;
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
    public View getView(final int position, View convertView, ViewGroup parent) {

        View view = convertView;
        CoursesOfferedViewHolder holder;
        if(view == null){

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.course_list_modal,parent,false);
            holder = new CoursesOfferedViewHolder(view);
            view.setTag(holder);

        }else{
            holder = (CoursesOfferedViewHolder) view.getTag();
        }

        CoursesOfferedModal temp = list.get(position);
        holder.title.setText(temp.courseName);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CourseDetailsActivity.class);
                context.startActivity(intent);
                Toast.makeText(context, "item: " + position, Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}




