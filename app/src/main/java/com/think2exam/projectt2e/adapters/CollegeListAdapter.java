package com.think2exam.projectt2e.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;
import com.think2exam.projectt2e.R;
import com.think2exam.projectt2e.modals.CollegeInfoModel;
import com.think2exam.projectt2e.modals.CollegeListModel;
import com.think2exam.projectt2e.ui.activities.CollegeInfoActivity;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;



public class CollegeListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private ArrayList<CollegeListModel> CollegeListItems;
    private Context context;
    // for load more
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private OnLoadMoreListener onLoadMoreListener;

    private boolean isLoading;

    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    public void add(int position, CollegeListModel collegeListModel) {
        CollegeListItems.add(position, collegeListModel);
        notifyItemInserted(position);
    }


    public CollegeListAdapter(ArrayList<CollegeListModel> arrayList, Context context,RecyclerView recyclerView)
    {
        this.CollegeListItems = arrayList;
        this.context = context;

        // load more
        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int totalItemCount = linearLayoutManager.getItemCount();
                int visibleItemCount = linearLayoutManager.getChildCount();
                int firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();

                //lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                if (!isLoading && totalItemCount <= (visibleItemCount + firstVisibleItemPosition) && firstVisibleItemPosition>=0 ) {
                    if (onLoadMoreListener != null) {
                        onLoadMoreListener.onLoadMore();
                    }
                    isLoading = true;
                }
            }
        });

    }




    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.college_list_item, parent, false);
            return new ViewHolderRow(view);
        } else if (viewType == VIEW_TYPE_LOADING) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.load_more, parent, false);
            return new ViewHolderLoading(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

        if(holder instanceof ViewHolderRow)
        {
            ((ViewHolderRow) holder).name.setText(CollegeListItems.get(position).getName());
            Glide.with(context)
                    .load(context.getResources().getDrawable(R.drawable.location_color_primary_24dp))
                    .into(((ViewHolderRow) holder).locIcon);
            ((ViewHolderRow) holder).location.setText(CollegeListItems.get(position).getLocation());
            ((ViewHolderRow) holder).materialCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, CollegeInfoActivity.class);
                    intent.putExtra("id",CollegeListItems.get(position).getId());
                    intent.putExtra("catId",CollegeListItems.get(position).getCatId());
                    context.startActivity(intent);
                }
            });

        }
        else if(holder instanceof ViewHolderLoading)
        {
            ViewHolderLoading loadingViewHolder = (ViewHolderLoading) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }
    }


    @Override
    public int getItemCount() {
        return CollegeListItems == null ? 0 : CollegeListItems.size();
    }

    public void setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener) {
        this.onLoadMoreListener = mOnLoadMoreListener;
    }


    @Override
    public int getItemViewType(int position) {
        return CollegeListItems.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    public void setLoaded() {
        isLoading = false;
    }



    private class ViewHolderLoading extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public ViewHolderLoading(View view) {
            super(view);
            progressBar = (ProgressBar) view.findViewById(R.id.load_more_progressbar);
        }
    }



    public class ViewHolderRow extends RecyclerView.ViewHolder{

        public TextView name;
        public MaterialCardView materialCardView;
        public TextView location;
        public ImageView locIcon;

        public ViewHolderRow(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.college_name);
            materialCardView = itemView.findViewById(R.id.college_card_view);
            location = itemView.findViewById(R.id.college_location_name);
            locIcon = itemView.findViewById(R.id.college_location_icon);


        }

    }




}
