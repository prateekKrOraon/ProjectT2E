package com.think2exam.projectt2e.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.smarteist.autoimageslider.SliderViewAdapter;
import com.think2exam.projectt2e.R;
import com.think2exam.projectt2e.ui.home.HomeFragment;

import java.util.ArrayList;

public class HomeImageSliderAdapter extends SliderViewAdapter<HomeImageSliderAdapter.SliderAdapterVH> {

    private HomeFragment context;

    private ArrayList<Integer> images;

    public HomeImageSliderAdapter(HomeFragment context, ArrayList<Integer> images) {
        this.context = context;
        this.images = images;
    }


    @Override
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.college_image_slider_layout, null);
        return new SliderAdapterVH(inflate);
    }

    @Override
    public void onBindViewHolder(SliderAdapterVH viewHolder, final int position) {


        Glide.with(context)
                .load(images.get(position))
                .into(viewHolder.image);


    }

    @Override
    public int getCount() {
        //slider view count could be dynamic siz
        return images.size();
    }

    class SliderAdapterVH extends SliderViewAdapter.ViewHolder {

        View itemView;
        ImageView image;


        public SliderAdapterVH(View itemView) {
            super(itemView);
            this.itemView = itemView;
            image = itemView.findViewById(R.id.col_info_image);
        }
    }

}