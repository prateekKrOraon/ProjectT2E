package com.think2exam.projectt2e.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
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
import com.think2exam.projectt2e.ui.dialogs.CollegeFilterDialog;
import com.think2exam.projectt2e.utilities.DBOperations;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.think2exam.projectt2e.Constants.SEARCH_CAT;
import static com.think2exam.projectt2e.Constants.SEARCH_CITY;
import static com.think2exam.projectt2e.Constants.SEARCH_KEY;
import static com.think2exam.projectt2e.Constants.SEARCH_STATE;

public class CollegeListActivity extends AppCompatActivity {

    private ArrayList<CollegeListModel> CollegeList = new ArrayList<>(),CollegeListDup;
    MaterialCardView searchBar;
    ImageView searchicon,crossicon,filtericon;
    TextView title;
    private String tag;
    int count=0;
    private String which;

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
        filtericon = findViewById(R.id.filter_icon);

        which = getIntent().getStringExtra("which");
        int query = getIntent().getIntExtra("query",-1);
        if(!which.equals("search")){
            tag = getResources().getString(getIntent().getIntExtra("catId",-1));
        }else{
            tag = which;
        }



       // count = CollegeList.size();
        title.setText(tag+" ("+count+")");


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

        //setting filter icon
        filtericon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickedFilterIcon();
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

            DBOperations dbOperations = DBOperations.getInstance();

            int query = getIntent().getIntExtra("query",Integer.MIN_VALUE);
            String which = getIntent().getStringExtra("which");

            JSONArray jsonArray = null;

            if(!which.equals("search")){
                int catId = getIntent().getIntExtra("catId",-1);
                switch (catId) {
                    case R.string.engineering:
                        tableName = "engineering_colleges";
                        break;
                    case R.string.agriculture:
                        tableName = "agriculture_colleges";
                        break;
                    case R.string.management:
                        tableName = "mba_colleges";
                        break;
                    case R.string.medical_and_dental:
                        tableName = "medical_and_dental_colleges";
                        break;
                    case R.string.pharmacy:
                        tableName = "pharmacy_colleges";
                        break;
                    case R.string.nursing_and_paramedical:
                        tableName = "nursing_and_paramedical_colleges";
                        break;
                    case R.string.education:
                        tableName = "education";
                        break;
                    default:
                        tableName = "university";
                        break;
                }
                jsonArray = dbOperations.getColleges(
                        which,
                        query==-1?"all":getString(query),
                        catId,
                        ""
                );
            }else{

                Intent intent = getIntent();
                String cat = intent.getStringExtra(SEARCH_CAT);
                switch (cat) {
                    case "Engineering":
                        tableName = "engineering_colleges";
                        break;
                    case "Agriculture":
                        tableName = "agriculture_colleges";
                        break;
                    case "Management":
                        tableName = "mba_colleges";
                        break;
                    case "Medical and Dental":
                        tableName = "medical_and_dental_colleges";
                        break;
                    case "Pharmacy":
                        tableName = "pharmacy_colleges";
                        break;
                    case "Nursing and Paramedical":
                        tableName = "nursing_and_paramedical_colleges";
                        break;
                    case "Education":
                        tableName = "education";
                        break;
                    case "University":
                        tableName = "university";
                        break;
                    default:
                        tableName = "All Category";
                        break;
                }
                jsonArray = dbOperations.searchCollege(
                        cat,
                        intent.getStringExtra(SEARCH_STATE),
                        intent.getStringExtra(SEARCH_CITY),
                        intent.getStringExtra(SEARCH_KEY)
                );
            }


//            if(which.equals("category"))
//            {
//
//                CompleteTableQuery completeTableQuery = new CompleteTableQuery();
//                completeTableQuery.setreqURL(getIntent().getIntExtra("tableName",-1));
//                jsonStr = completeTableQuery.request("");
//                tableName = getString(getIntent().getIntExtra("tableName",-1));
//
//            }
//            else if(which.equals("city"))
//            {
//
//                ByCityQuery byCityQuery = new ByCityQuery();
//                byCityQuery.setreqURL(getIntent().getIntExtra("catId",-1));
//                jsonStr = byCityQuery.request(getString(getIntent().getIntExtra("tag",-1)),"");
//                tableName = getString(getIntent().getIntExtra("catId",-1));
//
//            }
//            else if(which.equals("state"))
//            {
//                ByStateQuery byStateQuery = new ByStateQuery();
//                byStateQuery.setreqURL(getIntent().getIntExtra("catId",-1));
//                jsonStr = byStateQuery.request(getString(getIntent().getIntExtra("tag",-1)),"");
//                tableName = getString(getIntent().getIntExtra("catId",-1));
//
//            }
//            else if(which.equals("prestigious_college"))
//            {
//               PrestigiousCollegeQuery prestigiousCollegeQuery = new PrestigiousCollegeQuery();
//                prestigiousCollegeQuery.setreqURL(getIntent().getIntExtra("tag",-1));
//                jsonStr = prestigiousCollegeQuery.request();
//                tableName = getString(prestigiousCollegeQuery.getCatId());
//
//            }
//            else if(which.equals("search"))
//            {
//                SearchQuery searchQuery = new SearchQuery();
//                jsonStr = searchQuery.setreqURL(getApplicationContext(),getIntent().getStringExtra("category"),getIntent().getStringExtra("state"),getIntent().getStringExtra("city"),getIntent().getStringExtra("keyword"));
//                tableName = getIntent().getStringExtra("category");
//            }
//
//            if(jsonStr!=null)
//            {
//                try {
//                    jsonArray = new JSONArray(jsonStr);
//                    setCollegeItems(jsonArray);
//                }
//                catch (final JSONException e)
//                {
//                    e.printStackTrace();
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            Toast.makeText(getApplicationContext(),""+ e.getMessage(),Toast.LENGTH_LONG).show();
//                        }
//                    });                }
//
//            }

            try {
                setCollegeItems(jsonArray);
            } catch (final JSONException e) {
                e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),""+ e.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    });
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            progressBar.setVisibility(View.GONE);
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
            searchicon.setVisibility(View.GONE);
        }
        else
        {
            crossicon.setVisibility(View.GONE);
            searchicon.setVisibility(View.VISIBLE);
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
        title.setText(tag+" ("+count+")");

    }


   public void onClickedFilterIcon()
   {
       CollegeFilterDialog collegeFilterDialog = new CollegeFilterDialog();
       collegeFilterDialog.show(getSupportFragmentManager(),"filterdialog");
   }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
