package com.think2exam.projectt2e.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.smarteist.autoimageslider.SliderViewAdapter;
import com.think2exam.projectt2e.R;
import com.think2exam.projectt2e.modals.HomeImageSliderModel;
import com.think2exam.projectt2e.ui.home.HomeFragment;

import java.util.ArrayList;

public class HomeImageSliderAdapter extends SliderViewAdapter<HomeImageSliderAdapter.SliderAdapterVH> {

    private HomeFragment context;

    private ArrayList<HomeImageSliderModel> images;

    public HomeImageSliderAdapter(HomeFragment context, ArrayList<HomeImageSliderModel> images) {
        this.context = context;
        this.images = images;
    }


    @Override
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_image_slider_layout, null);
        return new SliderAdapterVH(inflate);
    }

    @Override
    public void onBindViewHolder(SliderAdapterVH viewHolder, final int position) {


        Glide.with(context)
                .load(images.get(position).getImage())
                .into(viewHolder.image);
        viewHolder.name.setText(images.get(position).getName());


    }

    @Override
    public int getCount() {
        //slider view count could be dynamic siz
        return images.size();
    }

    class SliderAdapterVH extends SliderViewAdapter.ViewHolder {

        View itemView;
        ImageView image;
        TextView name;


        public SliderAdapterVH(View itemView) {
            super(itemView);
            this.itemView = itemView;
            image = itemView.findViewById(R.id.home_image);
            name = itemView.findViewById(R.id.home_image_name);
        }
    }

}