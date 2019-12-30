package com.think2exam.projectt2e.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.think2exam.projectt2e.view_holders.QuickFactsViewHolder;
import com.think2exam.projectt2e.R;
import com.think2exam.projectt2e.modals.QuickFactsModal;
import java.util.ArrayList;

public class QuickFactsAdapter extends BaseAdapter {

    private ArrayList<QuickFactsModal> list;
    private Context context;
    public QuickFactsAdapter(Context context, ArrayList<QuickFactsModal> list){
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
        QuickFactsViewHolder holder;

        if(view == null){

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            try{
                view = inflater.inflate(R.layout.quick_facts_modal,parent,false);
            }catch (NullPointerException ex){
                System.out.println(ex.toString());
            }
            holder = new QuickFactsViewHolder(view);
            view.setTag(holder);

        }else{
            holder = (QuickFactsViewHolder) view.getTag();
        }

        QuickFactsModal temp = list.get(position);
        holder.titleView.setText(temp.title);
        holder.subtitleView.setText(temp.subTitle);
        holder.iconView.setImageResource(temp.iconId);

        return view;
    }
}
