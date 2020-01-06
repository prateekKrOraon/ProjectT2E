package com.think2exam.projectt2e.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.think2exam.projectt2e.R;
import com.think2exam.projectt2e.adapters.SearchCatItemAdapter;

import java.util.ArrayList;

public class SearchCategoryActivity extends AppCompatActivity {

    ArrayList<String> SearchCatItemArray;
    ArrayList<String> SearchCatItemArrayDup;
    String tag;
    TextView title;
    int count;
    EditText searchBox;
    ImageView searchIcon,crossIcon;
    RecyclerView siRecyclerView;
    RecyclerView.LayoutManager siLayoutManager;
    SearchCatItemAdapter searchCatItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_category);

        title = findViewById(R.id.search_cat_toolbar_title);
        ImageView backBtn = findViewById(R.id.search_cat_back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        tag = getIntent().getStringExtra("cat_name");
        setItemsArray();
        count=SearchCatItemArray.size();
        title.setText(tag+" ("+count+")");


        //seting recycler view
        siRecyclerView = findViewById(R.id.search_cat_item_recyler_view);
        siLayoutManager = new LinearLayoutManager(this);
        searchCatItemAdapter = new SearchCatItemAdapter(SearchCatItemArray,this);
        siRecyclerView.setHasFixedSize(true);
        siRecyclerView.setLayoutManager(siLayoutManager);
        siRecyclerView.setAdapter(searchCatItemAdapter);

        //setting Search box
        searchBox = findViewById(R.id.s_search_edittext);
        searchIcon = findViewById(R.id.s_search_icon);
        crossIcon = findViewById(R.id.s_cross_icon_);

        searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
               onChangedText(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //cross icon
        crossIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchBox.setText("");
            }
        });

    }

    public void onChangedText(CharSequence s)
    {
        if(s.length()!=0)
        {
            crossIcon.setVisibility(View.VISIBLE);
            searchIcon.setVisibility(View.GONE);
        }
        else
        {
            crossIcon.setVisibility(View.GONE);
            searchIcon.setVisibility(View.VISIBLE);
        }
        ArrayList<String> arrayList = new ArrayList<>();
        for(String searchItem :SearchCatItemArray)
        {
            if(searchItem.toLowerCase().contains(s.toString().toLowerCase()))
            {
                arrayList.add(searchItem);
            }
        }

        SearchCatItemArrayDup=null;
        SearchCatItemArrayDup=arrayList;
        siLayoutManager = new LinearLayoutManager(this);
        searchCatItemAdapter = new SearchCatItemAdapter(SearchCatItemArrayDup,this);
        siRecyclerView.setHasFixedSize(true);
        siRecyclerView.setLayoutManager(siLayoutManager);
        siRecyclerView.setAdapter(searchCatItemAdapter);

        count = SearchCatItemArrayDup.size();
        title.setText(tag+" ("+count+")");
    }

    public void setItemsArray()
    {
        SearchCatItemArray = new ArrayList<>();
        if(tag.equals("COURSE"))
        {
            SearchCatItemArray.add("BTech");
            SearchCatItemArray.add("MTech");
            SearchCatItemArray.add("Architecture");
            SearchCatItemArray.add("MBBS");
        }
        else if(tag.equals("CITY"))
        {
            SearchCatItemArray.add("Ravangla1");
            SearchCatItemArray.add("Ravangla2");
            SearchCatItemArray.add("Ravangla3");
            SearchCatItemArray.add("Ravangla4");
            SearchCatItemArray.add("Ravangla5");
            SearchCatItemArray.add("Ravangla6");
        }
        else if(tag.equals("STATE"))
        {
            SearchCatItemArray.add("Sikkim");
            SearchCatItemArray.add("Andra Pradesh");
            SearchCatItemArray.add("Bihar");
            SearchCatItemArray.add("Delhi");
            SearchCatItemArray.add("Assam");
        }
        else if(tag.equals("INSTITUTE"))
        {
            SearchCatItemArray.add("IIT");
            SearchCatItemArray.add("NIT");
            SearchCatItemArray.add("IIIT");
            SearchCatItemArray.add("Central University");
            SearchCatItemArray.add("Deemed University");
            SearchCatItemArray.add("Private College");

        }
    }
}
