package com.think2exam.projectt2e.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.think2exam.projectt2e.R;
import com.think2exam.projectt2e.modals.ViewPagerModel;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class ViewPagerAdapter extends PagerAdapter {

    private ArrayList<ViewPagerModel> viewPagerModels;
    private LayoutInflater inflater;
    private Context context;

    public ViewPagerAdapter(Context context,ArrayList<ViewPagerModel> viewPagerModels)
    {
        this.context = context;
        this.viewPagerModels = viewPagerModels;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }

    @Override
    public int getCount() {
        return viewPagerModels.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View layout = inflater.inflate(R.layout.view_pager_layout,container,false);
        ImageView image = layout.findViewById(R.id.view_pager_image);
        //TextView textView = layout.findViewById(R.id.view_pager_text);

//        textView.setText(viewPagerModels.get(position).getName());
//        textView.setMaxLines(1);
//        textView.setEnabled(true);
//        Animation a = AnimationUtils.loadAnimation(context, R.anim.animation);
//        a.reset();
//        textView.clearAnimation();
//        textView.startAnimation(a);

        Glide.with(context)
                .load(viewPagerModels.get(position).getIcon())
                .into(image);

        container.addView(layout,0);
        return layout;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }
}