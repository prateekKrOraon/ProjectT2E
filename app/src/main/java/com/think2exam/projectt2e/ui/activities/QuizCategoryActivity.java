package com.think2exam.projectt2e.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListAdapter;

import com.think2exam.projectt2e.R;
import com.think2exam.projectt2e.adapters.CategoryGridAdapter;
import com.think2exam.projectt2e.modals.CategoryModal;

import java.util.ArrayList;

public class QuizCategoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_category);

        Toolbar toolbar = findViewById(R.id.toolbar_quiz_category);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Categories");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ArrayList<CategoryModal> list = new ArrayList<>();

        list.add(new CategoryModal("Computer Science",R.drawable.ic_laptop_black_48dp));
        list.add(new CategoryModal("Science",R.drawable.ic_earth_black_48dp));
        list.add(new CategoryModal("Sports",R.drawable.outline_sports_basketball_black_48));
        list.add(new CategoryModal("Technology",R.drawable.ic_google_physical_web_black_48dp));

        CategoryGridAdapter adapter = new CategoryGridAdapter(QuizCategoryActivity.this,list);
        GridView categoryGrid = findViewById(R.id.categories_gird);
        categoryGrid.setAdapter(adapter);

        setListViewHeightBasedOnChildren(categoryGrid);
    }

    public static void setListViewHeightBasedOnChildren(GridView gridView) {

        ListAdapter listAdapter = gridView.getAdapter();

        if (listAdapter == null){
            return;
        }

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(gridView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;

        View view = null;for (int i = 0; i < listAdapter.getCount(); i++) {

            view = listAdapter.getView(i, view, gridView);

            if (i == 0) {
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));
            }

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = gridView.getLayoutParams();

        params.height = totalHeight + (gridView.getColumnWidth() * (listAdapter.getCount() - 1));

        gridView.setLayoutParams(params);
        gridView.requestLayout();

    }
}
