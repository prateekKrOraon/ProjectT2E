package com.think2exam.projectt2e.ui.search;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.think2exam.projectt2e.R;
import com.think2exam.projectt2e.adapters.SearchCatAdapter;
import com.think2exam.projectt2e.modals.SearchCategoryModel;

import java.util.ArrayList;

public class SearchFragment extends Fragment {

    public static final String id = "activities_fragment";
    public SearchFragment(){}

    public Context mainActivityContext;
    ArrayList<SearchCategoryModel> SearchCatList;

    @SuppressLint("ValidFragment")
    public SearchFragment(Context context)
    {
         mainActivityContext = context;
    }


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_search, container, false);

        setItems();
        RecyclerView sRecyclerView = root.findViewById(R.id.search_cat_recycler_view);
        RecyclerView.LayoutManager sLayoutManager = new GridLayoutManager(mainActivityContext,2);
        SearchCatAdapter searchCatAdapter = new SearchCatAdapter(SearchCatList,mainActivityContext);
        sRecyclerView.setLayoutManager(sLayoutManager);
        sRecyclerView.setHasFixedSize(true);
        sRecyclerView.setAdapter(searchCatAdapter);


        return root;
    }

    public void setItems()
    {
        SearchCatList = new ArrayList<>();
        SearchCatList.add(new SearchCategoryModel("ALL",R.drawable.iit));
        SearchCatList.add(new SearchCategoryModel("INSTITUTE",R.drawable.nit));
        SearchCatList.add(new SearchCategoryModel("COURSE",R.drawable.course));
        SearchCatList.add(new SearchCategoryModel("CITY",R.drawable.city));
        SearchCatList.add(new SearchCategoryModel("STATE",R.drawable.state));
    }
}