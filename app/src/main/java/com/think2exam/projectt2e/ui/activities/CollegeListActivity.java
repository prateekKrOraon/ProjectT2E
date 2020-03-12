package com.think2exam.projectt2e.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.card.MaterialCardView;
import com.think2exam.projectt2e.R;
import com.think2exam.projectt2e.adapters.CollegeListAdapter;
import com.think2exam.projectt2e.modals.CollegeListModel;
import com.think2exam.projectt2e.utility.ByCityQuery;
import com.think2exam.projectt2e.utility.ByStateQuery;
import com.think2exam.projectt2e.utility.CompleteTableQuery;
import com.think2exam.projectt2e.utility.PrestigiousCollegeQuery;
import com.think2exam.projectt2e.utility.SearchQuery;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CollegeListActivity extends AppCompatActivity {

    private ArrayList<CollegeListModel> CollegeList = new ArrayList<>(),CollegeListDup;
    MaterialCardView searchBar;
    ImageView searchicon,crossicon;
    TextView title;
    private String tag;
    int count=0;
    private String which;
    private String Title;

    RecyclerView cRecyclerView;
    ProgressBar progressBar;
    RecyclerView.LayoutManager cLayoutManager;
    CollegeListAdapter collegeListAdapter;

    public String tableName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_college_list);
        progressBar = findViewById(R.id.progress_bar_clg_list);

        //setting toolbar
        title = findViewById(R.id.toolbar_title);
        ImageView backBtn = findViewById(R.id.back_btn);
        crossicon = findViewById(R.id.cross_icon_);
        searchBar = findViewById(R.id.search_bar);
        searchicon  = findViewById(R.id.search_icon);

        which = getIntent().getStringExtra("which");
        tag = getResources().getString(getIntent().getIntExtra("tag",-1));
        Title = getIntent().getStringExtra("title");



       // count = CollegeList.size();
        title.setText(Title);


        new GetContacts().execute();

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               onBackPressed();
            }
        });




        //search

        final EditText searchBox = searchBar.findViewById(R.id.search_edittext);
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

        //setting cross icon
        crossicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchBox.setText("");
                onChangedText("");
            }
        });





    }


    private class GetContacts extends AsyncTask<Void, Void, Void> {
        String jsonStr;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            // Making a request to url and getting response


            if(which.equals("category"))
            {

                CompleteTableQuery completeTableQuery = new CompleteTableQuery();
                completeTableQuery.setreqURL(getIntent().getIntExtra("tableName",-1));
                jsonStr = completeTableQuery.request("");
                tableName = getString(getIntent().getIntExtra("tableName",-1));

            }
            else if(which.equals("city"))
            {

                ByCityQuery byCityQuery = new ByCityQuery();
                byCityQuery.setreqURL(getIntent().getIntExtra("catId",-1));
                jsonStr = byCityQuery.request(getString(getIntent().getIntExtra("tag",-1)),"");
                tableName = getString(getIntent().getIntExtra("catId",-1));

            }
            else if(which.equals("state"))
            {
                ByStateQuery byStateQuery = new ByStateQuery();
                byStateQuery.setreqURL(getIntent().getIntExtra("catId",-1));
                jsonStr = byStateQuery.request(getString(getIntent().getIntExtra("tag",-1)),"");
                tableName = getString(getIntent().getIntExtra("catId",-1));

            }
            else if(which.equals("prestigious_college"))
            {
                PrestigiousCollegeQuery prestigiousCollegeQuery = new PrestigiousCollegeQuery();
                prestigiousCollegeQuery.setreqURL(getIntent().getIntExtra("tag",-1));
                jsonStr = prestigiousCollegeQuery.request();
                tableName = getString(prestigiousCollegeQuery.getCatId());

            }
            else if(which.equals("search"))
            {
                SearchQuery searchQuery = new SearchQuery();
                jsonStr = searchQuery.setreqURL(getApplicationContext(),getIntent().getStringExtra("category"),getIntent().getStringExtra("state"),getIntent().getStringExtra("city"),getIntent().getStringExtra("keyword"));
                tableName = getIntent().getStringExtra("category");
            }

            if(jsonStr!=null)
            {
                try {
                    final JSONArray jsonArray = new JSONArray(jsonStr);
                    setCollegeItems(jsonArray);
                }
                catch (final JSONException e)
                {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),""+ e.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    });                }

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            progressBar.setVisibility(View.GONE);
            searchBar.setVisibility(View.VISIBLE);
            setRecyclerView();
        }
    }



    private void setRecyclerView()
    {
        //set recyclerView with Adapter

        cRecyclerView = findViewById(R.id.college_list_recycler_view);
        progressBar.setVisibility(View.GONE);
        cLayoutManager = new LinearLayoutManager(this);
        collegeListAdapter = new CollegeListAdapter(CollegeList,this);
        cRecyclerView.setHasFixedSize(true);
        cRecyclerView.setLayoutManager(cLayoutManager);
        cRecyclerView.setAdapter(collegeListAdapter);

    }



    public void setCollegeItems(JSONArray jsonArray) throws JSONException {


        for(int i=0;i<jsonArray.length();i++)
        {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            CollegeList.add(new CollegeListModel(jsonObject.getInt("id"),jsonObject.getString("college_name"),jsonObject.getString("college_location"),tableName));
        }

        CollegeListDup=CollegeList;
    }

    public void onChangedText(CharSequence s)
    {

        if(s.length()!=0)
        {
            crossicon.setVisibility(View.VISIBLE);
        }
        else
        {
            crossicon.setVisibility(View.GONE);
        }
        ArrayList<CollegeListModel> arrayList = new ArrayList<>();
         for(CollegeListModel college :CollegeList)
         {
              if((college.getName()+college.getLocation()).toLowerCase().contains(s.toString().toLowerCase()))
              {
                  arrayList.add(college);
              }
         }
         CollegeListDup = null;
         CollegeListDup = arrayList;
        cLayoutManager = new LinearLayoutManager(this);
        collegeListAdapter = new CollegeListAdapter(CollegeListDup,this);
        cRecyclerView.setHasFixedSize(true);
        cRecyclerView.setLayoutManager(cLayoutManager);
        cRecyclerView.setAdapter(collegeListAdapter);

        count = CollegeListDup.size();


    }




    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
