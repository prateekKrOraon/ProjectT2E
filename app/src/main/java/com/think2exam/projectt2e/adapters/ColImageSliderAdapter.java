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

import java.util.ArrayList;

public class ColImageSliderAdapter extends SliderViewAdapter<ColImageSliderAdapter.SliderAdapterVH> {

    private Context context;

    private ArrayList<String> images;

    public ColImageSliderAdapter(Context context,ArrayList<String> images) {
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
                .load(Uri.parse(images.get(position)))
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