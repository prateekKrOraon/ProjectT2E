package com.think2exam.projectt2e.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.service.quicksettings.Tile;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.think2exam.projectt2e.MainActivity;
import com.think2exam.projectt2e.R;
import com.think2exam.projectt2e.adapters.CollegeListAdapter;
import com.think2exam.projectt2e.modals.CollegeListModel;

import com.think2exam.projectt2e.utilities.DBOperations;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.Key;
import java.util.ArrayList;

import static com.think2exam.projectt2e.Constants.TITLE;

public class CollegeListActivity extends AppCompatActivity {

    private ArrayList<CollegeListModel> CollegeList;
    MaterialCardView searchBar;
    RelativeLayout titleLayout;
    TextView title;
    ImageView searchicon,search;
    EditText searchEdit;
    private String Title;

    RecyclerView cRecyclerView;
    RelativeLayout progressLayout;
    RecyclerView.LayoutManager cLayoutManager;
    CollegeListAdapter collegeListAdapter;
    TextView tryAgain;
    LinearLayout tryAgainLayout;
    TextView noCollege;
    String Keyword=null;

    public int prevCollegeListSize=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_college_list);
        progressLayout = findViewById(R.id.progress_bar);


        //setting toolbar
        title = findViewById(R.id.toolbar_title);
        ImageView backBtn = findViewById(R.id.back_btn);
        searchBar = findViewById(R.id.search_bar);
        titleLayout = findViewById(R.id.title_layout);
        search = findViewById(R.id.search);
        searchEdit = findViewById(R.id.search_edittext);
        searchicon  = findViewById(R.id.search_icon);
        tryAgain = findViewById(R.id.try_again);
        tryAgainLayout = findViewById(R.id.try_again_layout);
        noCollege = findViewById(R.id.no_college);
        Title = getIntent().getStringExtra(TITLE);

        setTitleText();



        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        searchicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSearchLayout();
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Keyword = searchEdit.getText().toString().trim();
                new GetCollegeHandler1().execute(1);
            }
        });

        cRecyclerView = findViewById(R.id.college_list_recycler_view);
        new GetCollegeHandler1().execute(1);


    }

    public void setTitleText(){
        if(Keyword!=null && Keyword.length()!=0) {
            title.setText(Title + " " + "'" + Keyword + "'");
        }else if(getIntent().getStringExtra("keyword")!=null && getIntent().getStringExtra("keyword").trim().length()!=0) {
            title.setText(Title + " " + "'" + getIntent().getStringExtra("keyword").trim() + "'");
        }else {
            title.setText(Title);
        }
        title.setSingleLine();
        title.setSelected(true);

    }

    public void setSearchLayout(){
        searchBar.setVisibility(View.VISIBLE);
        titleLayout.setVisibility(View.GONE);
    }

    public void setTitleLayout(){
        searchBar.setVisibility(View.GONE);
        titleLayout.setVisibility(View.VISIBLE);
    }


    private class GetCollegeHandler1 extends AsyncTask<Integer, Void, Void> {
        JSONArray jsonArray=null;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressLayout.setVisibility(View.VISIBLE);
            cRecyclerView.setVisibility(View.GONE);
            tryAgainLayout.setVisibility(View.GONE);
            noCollege.setVisibility(View.GONE);
            CollegeList = new ArrayList<>();


        }

        @Override
        protected Void doInBackground(Integer... integers) {
            // Making a request to url and getting response

            DBOperations dbOperations = DBOperations.getInstance();
            String which = getIntent().getStringExtra("which");
            int catId = getIntent().getIntExtra("catId",-1);
            if(which.equals("search"))
            {
                String state = getIntent().getStringExtra("state");
                String city = getIntent().getStringExtra("city");
                String keyword = Keyword==null?getIntent().getStringExtra("keyword"): Keyword;
                jsonArray = dbOperations.getColleges(which, state,city,catId,keyword,integers[0]);
            }
            else
            {
                int query = getIntent().getIntExtra("query",Integer.MIN_VALUE);
                jsonArray = dbOperations.getColleges(which,
                        query==-1?"all":getString(query),"", catId, Keyword!=null?Keyword:"",integers[0]);
            }



            try {
                if(jsonArray!=null) {
                    setCollegeItems(jsonArray);
                }
            } catch (final Exception e) {

                System.out.println("Error: "+e.getMessage());
                              }


            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            progressLayout.setVisibility(View.GONE);
            setTitleText();
            if(jsonArray!=null)
            {
                setRecyclerView();
            }else{
                setTryAgain();
            }

        }
    }

    public void setTryAgain(){
        tryAgainLayout.setVisibility(View.VISIBLE);
        ImageView imageView  = findViewById(R.id.try_again_image);
        Glide.with(MainActivity.activity)
                .load(R.drawable.internet_down)
                .into(imageView);
        tryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tryAgainLayout.setVisibility(View.GONE);
                progressLayout.setVisibility(View.VISIBLE);
                new GetCollegeHandler1().execute(1);
            }
        });
    }

    private class GetCollegeHandler2 extends AsyncTask<Integer, Void, Void> {

        JSONArray jsonArray;
        @Override
        protected Void doInBackground(Integer... integers) {
            // Making a request to url and getting response


            DBOperations dbOperations = DBOperations.getInstance();
            String which = getIntent().getStringExtra("which");
            int catId = getIntent().getIntExtra("catId",-1);
            if(which.equals("search"))
            {
                String state = getIntent().getStringExtra("state");
                String city = getIntent().getStringExtra("city");
                String keyword = Keyword==null?getIntent().getStringExtra("keyword"): Keyword;
                jsonArray = dbOperations.getColleges(which, state,city,catId,keyword,integers[0]);
            }
            else
            {
                int query = getIntent().getIntExtra("query",Integer.MIN_VALUE);
                int start_id;
                System.out.println(which);
                if(which.equals("prestigious_college")) {
                    start_id = Integer.MAX_VALUE;
                } else {
                    start_id = integers[0];
                }
                jsonArray = dbOperations.getColleges(which,
                        query==-1?"all":getString(query),"", catId, Keyword!=null?Keyword:"",start_id);

            }



            try {
                setCollegeItems(jsonArray);
            } catch (final Exception e) {

                System.out.println("Error: "+e.getMessage());
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if(jsonArray!=null) {
                int index = CollegeList.indexOf(null);
                CollegeList.remove(index);
                collegeListAdapter.notifyItemRemoved(index);
                collegeListAdapter.notifyDataSetChanged();
                collegeListAdapter.setLoaded();
            }
        }
    }


    private void setRecyclerView()
    {
        setTitleLayout();
        //set recyclerView with Adapter
        if(CollegeList.size()==0){
            noCollege.setVisibility(View.VISIBLE);
            cRecyclerView.setVisibility(View.GONE);
        }else {
            noCollege.setVisibility(View.GONE);
            cRecyclerView.setVisibility(View.VISIBLE);
            cLayoutManager = new LinearLayoutManager(this);
            cRecyclerView.setLayoutManager(cLayoutManager);
            collegeListAdapter = new CollegeListAdapter(CollegeList, this, cRecyclerView);
            cRecyclerView.setAdapter(collegeListAdapter);
            setOnLoadMore();
        }

    }

    public void setOnLoadMore()
    {
        collegeListAdapter.setOnLoadMoreListener(new CollegeListAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                //id of last tuple selected in earlier attempt
                final int start_id = getIdOfLastItem();
                if (CollegeList.size()!=prevCollegeListSize && CollegeList.size()%10==0) {

                    new Handler().post(new Runnable() {
                        @Override
                        public void run() {
                            CollegeList.add(null);
                            collegeListAdapter.notifyItemInserted(CollegeList.size() - 1);
                            new GetCollegeHandler2().execute(start_id);
                        }
                    });
                } else {
                }
            }
        });
    }


    public int getIdOfLastItem() //incremented by 1
    {
        if(CollegeList!=null && CollegeList.size()!=0)
        {
            return  CollegeList.get(CollegeList.size()-1).getId()+1;
        }
        return 1;
    }

    public void setCollegeItems(JSONArray jsonArray) throws Exception {

        prevCollegeListSize = CollegeList.size();
        for(int i=0;i<jsonArray.length();i++)
        {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            CollegeList.add(new CollegeListModel(jsonObject.getInt("id"),
                    jsonObject.getString("college_name"),
                    jsonObject.getString("college_location"),
                    getIntent().getIntExtra("catId",-1)));
        }
    }


    @Override
    public void onBackPressed() {
        if(searchBar.getVisibility() == View.VISIBLE){
            setTitleLayout();
        }else {
            super.onBackPressed();
        }

    }
}
