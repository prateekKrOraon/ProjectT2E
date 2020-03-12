package com.think2exam.projectt2e.ui.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.chahinem.pageindicator.PageIndicator;
import com.hanks.htextview.base.AnimationListener;
import com.hanks.htextview.base.HTextView;
import com.hanks.htextview.typer.TyperTextView;
import com.think2exam.projectt2e.R;
import com.think2exam.projectt2e.adapters.CategoryAdapter;
import com.think2exam.projectt2e.adapters.CityAdapter;
import com.think2exam.projectt2e.adapters.StateAdapter;
import com.think2exam.projectt2e.adapters.PrestigiousCollegeAdapter;
import com.think2exam.projectt2e.adapters.ViewPagerAdapter;
import com.think2exam.projectt2e.modals.CategoryModel;
import com.think2exam.projectt2e.modals.CityModel;
import com.think2exam.projectt2e.modals.FeaturedCollegeModel;
import com.think2exam.projectt2e.modals.StateModel;
import com.think2exam.projectt2e.modals.PrestigiousCollegeModel;
import com.think2exam.projectt2e.adapters.SnapHelperOneByOne;
import com.think2exam.projectt2e.adapters.FeaturedCollegeAdapter;
import com.think2exam.projectt2e.modals.ViewPagerModel;
import com.think2exam.projectt2e.ui.activities.AboutQuizActivity;
import com.think2exam.projectt2e.utilities.DBOperations;
import com.think2exam.projectt2e.utility.HttpHandler;
import com.think2exam.projectt2e.utility.Top5CollegesQuery;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import static com.think2exam.projectt2e.Constants.SHARED_PREF;
import static java.lang.Thread.sleep;

public class HomeFragment extends Fragment {
    public static final String id = "home_fragment";
    private static  View parentView;
    private ArrayList<CityModel> CityModelArrayList;
    private ArrayList<StateModel> StateModelArrayList;
    private ArrayList<CategoryModel> CategoryModelArrayList;
    private ArrayList<PrestigiousCollegeModel> prestigiousCollegeModelArrayList;

    private static Context mainActivityContext;
    private ArrayList<FeaturedCollegeModel> featuredCollegeModels;
    private ArrayList<ViewPagerModel> viewPagerModels;
    private static int currentPage=0,numPages=0;
    private ProgressBar progressBarFeaturedClg;
    private LinearLayout FeaturedClgLayout;

    private int catId;
    private int i=1;

    GetTop5Colleges getTop5Colleges;

    public HomeFragment() {

    }

    @SuppressLint("ValidFragment")
    public HomeFragment(Context context) {
        mainActivityContext = context;
    }


    @Override
    public void onResume() {
        super.onResume();
        //getTop5Colleges.execute();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        parentView = root;
        SharedPreferences pref = getContext().getSharedPreferences("MyPreference", 0); // 0 - for private mode
        catId = pref.getInt("category_id",-1);

        //Toast.makeText(mainActivityContext, ""+R.string.university+" "+R.string.did_not_answer, Toast.LENGTH_SHORT).show();
        //set Top cities and top states
        final TextView topCity = root.findViewById(R.id.popular_city_text);
        TextView topState = root.findViewById(R.id.popular_state_text);
        if(catId!=R.string.university) {
            topCity.setText(getResources().getString(catId) + " College in Top Cities");
            topState.setText(getResources().getString(catId) + " College in Top States");
        }
        else
        {
            topCity.setText("Universities in Top Cities");
            topState.setText("Universities in Top States");
        }

        final TyperTextView typerTextView1 = root.findViewById(R.id.view_pager_text1);

        TyperTextAnimater typerTextAnimater = new TyperTextAnimater(typerTextView1);
        typerTextAnimater.setTyperTextView();



        //set ArrayList
        setArrayList();

        //setting viewpager slideshow
        initViewPagerSlider(root);
        FeaturedClgLayout = root.findViewById(R.id.ll_featured_clg);
        progressBarFeaturedClg = root.findViewById(R.id.progress_bar_featured_clg);

        new GetTop5Colleges().execute();

        //Recycler view for top colleges
        RecyclerView tcRecyclerView = root.findViewById(R.id.home_top_colleges_recycler_view);
        RecyclerView.LayoutManager tcLayoutManager = new LinearLayoutManager(mainActivityContext);
        ((LinearLayoutManager) tcLayoutManager).setOrientation(LinearLayoutManager.HORIZONTAL);
        PrestigiousCollegeAdapter prestigiousCollegeAdapter = new PrestigiousCollegeAdapter(prestigiousCollegeModelArrayList,mainActivityContext);
        tcRecyclerView.setHasFixedSize(true);
        tcRecyclerView.setLayoutManager(tcLayoutManager);
        tcRecyclerView.setAdapter(prestigiousCollegeAdapter);

        //Recycler view for City
        RecyclerView ctRecyclerView = root.findViewById(R.id.home_city_recycler_view);
        RecyclerView.LayoutManager ctLayoutManager = new LinearLayoutManager(mainActivityContext);
        ((LinearLayoutManager) ctLayoutManager).setOrientation(LinearLayoutManager.HORIZONTAL);
        CityAdapter cityAdapter = new CityAdapter(CityModelArrayList,mainActivityContext);
        ctRecyclerView.setHasFixedSize(true);
        ctRecyclerView.setLayoutManager(ctLayoutManager);
        ctRecyclerView.setAdapter(cityAdapter);

        //Recycler view for state
        RecyclerView stRecyclerView = root.findViewById(R.id.home_state_recycler_view);
        RecyclerView.LayoutManager stLayoutManager = new LinearLayoutManager(mainActivityContext);
        ((LinearLayoutManager) stLayoutManager).setOrientation(LinearLayoutManager.HORIZONTAL);
        StateAdapter stateAdapter = new StateAdapter(StateModelArrayList,mainActivityContext);
        stRecyclerView.setHasFixedSize(true);
        stRecyclerView.setLayoutManager(stLayoutManager);
        stRecyclerView.setAdapter(stateAdapter);

        //Recycler view for category
        RecyclerView catRecyclerView   = root.findViewById(R.id.home_category_recycler_view);
        RecyclerView.LayoutManager catLayoutManager = new GridLayoutManager(mainActivityContext,3);
        CategoryAdapter categoryAdapter = new CategoryAdapter(CategoryModelArrayList,mainActivityContext);
        catRecyclerView.setHasFixedSize(true);
        catRecyclerView.setLayoutManager(catLayoutManager);
        catRecyclerView.setAdapter(categoryAdapter);

        //learn more quiz
        LinearLayout learn_more_btn = root.findViewById(R.id.learn_more_btn);
        learn_more_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mainActivityContext, AboutQuizActivity.class);
                mainActivityContext.startActivity(intent);
            }
        });


        return root;
    }

    private void setArrayList()
    {
        viewPagerModels = new ArrayList<>();
        viewPagerModels.add(new ViewPagerModel("Do you want to Explore colleges in India?",R.drawable.university_back));
        viewPagerModels.add(new ViewPagerModel("Then Think2Exam is here for you",R.drawable.iit_back));
        viewPagerModels.add(new ViewPagerModel("6000+ Colleges",R.drawable.tezpur_university));

        featuredCollegeModels = new ArrayList<>();


        CityModelArrayList = new ArrayList<>();
        CityModelArrayList.add(new CityModel(R.string.bengaluru,R.drawable.city1));
        CityModelArrayList.add(new CityModel(R.string.hyderabad,R.drawable.city2));
        CityModelArrayList.add(new CityModel(R.string.chennai,R.drawable.city3));
        CityModelArrayList.add(new CityModel(R.string.ahmedabad,R.drawable.city4));
        CityModelArrayList.add(new CityModel(R.string.mumbai,R.drawable.top_college3));
        CityModelArrayList.add(new CityModel(R.string.jaipur,R.drawable.city5));
        CityModelArrayList.add(new CityModel(R.string.kolkata,R.drawable.city));


        // Uttar Pradesh, Andhra Pradesh, Maharashtra, Karnataka, Rajasthan and Tamil Nadu
        StateModelArrayList = new ArrayList<>();
        StateModelArrayList.add(new StateModel(R.string.uttar_pradesh,R.drawable.uttar_pradesh));
        StateModelArrayList.add(new StateModel(R.string.andra_pradesh,R.drawable.andra_pradesh));
        StateModelArrayList.add(new StateModel(R.string.maharashtra,R.drawable.maharashtra));
        StateModelArrayList.add(new StateModel(R.string.karnataka,R.drawable.karnataka));
        StateModelArrayList.add(new StateModel(R.string.rajasthan,R.drawable.rajasthan));
        StateModelArrayList.add(new StateModel(R.string.tamil_nadu,R.drawable.tamil_nadu));


        CategoryModelArrayList = new ArrayList<>();
        CategoryModelArrayList.add(new CategoryModel(R.string.engineering,R.drawable.engineering));
        CategoryModelArrayList.add(new CategoryModel(R.string.management,R.drawable.management));
        CategoryModelArrayList.add(new CategoryModel(R.string.agriculture,R.drawable.agriculture));
        CategoryModelArrayList.add(new CategoryModel(R.string.medical_and_dental,R.drawable.medical));
        CategoryModelArrayList.add(new CategoryModel(R.string.pharmacy,R.drawable.pharmacy));
        CategoryModelArrayList.add(new CategoryModel(R.string.nursing_and_paramedical,R.drawable.nurse));
        CategoryModelArrayList.add(new CategoryModel(R.string.education,R.drawable.education));
        CategoryModelArrayList.add(new CategoryModel(R.string.university,R.drawable.graduate));


        prestigiousCollegeModelArrayList = new ArrayList<>();
        prestigiousCollegeModelArrayList.add(new PrestigiousCollegeModel(R.string.iit,R.string.engineering,R.drawable.iit));
        prestigiousCollegeModelArrayList.add(new PrestigiousCollegeModel(R.string.nit,R.string.engineering,R.drawable.nit));
        prestigiousCollegeModelArrayList.add(new PrestigiousCollegeModel(R.string.aiims,R.string.medical_and_dental,R.drawable.aiims));
        prestigiousCollegeModelArrayList.add(new PrestigiousCollegeModel(R.string.iim,R.string.management,R.drawable.top_college1));
        prestigiousCollegeModelArrayList.add(new PrestigiousCollegeModel(R.string.university,R.string.university,R.drawable.university));

    }


    private void initFeaturedCollegeSlider() {


        RecyclerView tcRecyclerView = parentView.findViewById(R.id.featured_college_recycler_view);
        tcRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager tcLayoutManager = new LinearLayoutManager(mainActivityContext);
        ((LinearLayoutManager) tcLayoutManager).setOrientation(LinearLayoutManager.HORIZONTAL);
        LinearSnapHelper linearSnapHelper = new SnapHelperOneByOne();
        linearSnapHelper.attachToRecyclerView(tcRecyclerView);

        FeaturedCollegeAdapter featuredCollegeAdapter = new FeaturedCollegeAdapter(featuredCollegeModels,mainActivityContext);
        tcRecyclerView.setLayoutManager(tcLayoutManager);
        tcRecyclerView.setAdapter(featuredCollegeAdapter);

        PageIndicator pageIndicator = parentView.findViewById(R.id.page_indicator);
        pageIndicator.attachTo(tcRecyclerView);


    }

    private void initViewPagerSlider(View root)
    {
        final ViewPager viewPager = parentView.findViewById(R.id.view_pager);
        viewPager.setAdapter(new ViewPagerAdapter(mainActivityContext,viewPagerModels));
        numPages = viewPagerModels.size();
        final Handler handler = new Handler();
        final Runnable update = new Runnable() {
            @Override
            public void run() {
                if(currentPage == numPages){
                    currentPage = 0;
                }
                viewPager.setCurrentItem(currentPage++,true);

            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(update);
            }
        },4000,3000);

    }

    private class GetTop5Colleges extends AsyncTask<Void, Void, Void> {
        String jsonStr;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBarFeaturedClg.setVisibility(View.VISIBLE);
            FeaturedClgLayout.setVisibility(View.GONE);

        }

        @Override
        protected Void doInBackground(Void... arg0) {

            DBOperations dbOperations = DBOperations.getInstance();
            JSONArray jsonArray = dbOperations.getColleges(SHARED_PREF,SHARED_PREF,catId,"");

            try {
                setTop5Colleges(jsonArray);
            } catch (final JSONException e) {
                e.printStackTrace();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getContext(),""+ e.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });
            }

//            HttpHandler sh = new HttpHandler();
//            Top5CollegesQuery top5CollegesQuery = new Top5CollegesQuery();
//            String reqURL = top5CollegesQuery.setreqURL(catId);
//            jsonStr = sh.getTop5Colleges(reqURL);
//            if(jsonStr!=null){
//                try {
//                    final JSONArray jsonArray = new JSONArray(jsonStr);
//                    setTop5Colleges(jsonArray);
//
//                }catch (final JSONException e){
//                    e.printStackTrace();
//                    getActivity().runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            Toast.makeText(getContext(),""+ e.getMessage(),Toast.LENGTH_LONG).show();
//                        }
//                    });
//                }
//
//            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            try {
                progressBarFeaturedClg.setVisibility(View.GONE);
                FeaturedClgLayout.setVisibility(View.VISIBLE);
                initFeaturedCollegeSlider();
            }catch (Exception e){}finally {

            }
        }
    }

    private void setTop5Colleges(JSONArray jsonArray) throws JSONException {

        for(int i=0;i<jsonArray.length();i++)
        {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            featuredCollegeModels.add(new FeaturedCollegeModel(R.drawable.col_logo_default,jsonObject.getInt("id"),jsonObject.getString("college_name"),"rank "+jsonObject.getString("college_rank"),jsonObject.getString("college_location")));
        }

    }



}
