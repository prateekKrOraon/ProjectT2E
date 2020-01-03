package com.think2exam.projectt2e.ui.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.chahinem.pageindicator.PageIndicator;
import com.think2exam.projectt2e.R;
import com.think2exam.projectt2e.adapters.CategoryAdapter;
import com.think2exam.projectt2e.adapters.CityAdapter;
import com.think2exam.projectt2e.adapters.StateAdapter;
import com.think2exam.projectt2e.adapters.TopCollegeAdapter;
import com.think2exam.projectt2e.adapters.ViewPagerAdapter;
import com.think2exam.projectt2e.modals.CategoryModel;
import com.think2exam.projectt2e.modals.CityModel;
import com.think2exam.projectt2e.modals.FeaturedCollegeModel;
import com.think2exam.projectt2e.modals.StateModel;
import com.think2exam.projectt2e.modals.TopCollegeModel;
import com.think2exam.projectt2e.adapters.SnapHelperOneByOne;
import com.think2exam.projectt2e.adapters.FeaturedCollegeAdapter;
import com.think2exam.projectt2e.modals.ViewPagerModel;
import com.think2exam.projectt2e.ui.activities.AboutQuizActivity;


import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class HomeFragment extends Fragment {
    public static final String id = "home_fragment";
    private static  View parentView;
    private ArrayList<CityModel> CityModelArrayList;
    private ArrayList<StateModel> StateModelArrayList;
    private ArrayList<CategoryModel> CategoryModelArrayList;
    private ArrayList<TopCollegeModel> TopCollegeModelArrayList;

    private static Context mainActivityContext;
    private ArrayList<FeaturedCollegeModel> featuredCollegeModels;
    private ArrayList<ViewPagerModel> viewPagerModels;
    private static int currentPage=0,numPages=0;

    public HomeFragment() {
    }

    @SuppressLint("ValidFragment")
    public HomeFragment(Context context) {
        mainActivityContext = context;
    }



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        parentView = root;

        //set ArrayList
        setArrayList();

        //setting viewpager slideshow
        initViewPagerSlider();

        //setting featured college
        initFeaturedCollegeSlider();

        //Recycler view for top colleges
        RecyclerView tcRecyclerView = root.findViewById(R.id.home_top_colleges_recycler_view);
        RecyclerView.LayoutManager tcLayoutManager = new LinearLayoutManager(mainActivityContext);
        ((LinearLayoutManager) tcLayoutManager).setOrientation(LinearLayoutManager.HORIZONTAL);
        TopCollegeAdapter topCollegeAdapter = new TopCollegeAdapter(TopCollegeModelArrayList,mainActivityContext);
        tcRecyclerView.setHasFixedSize(true);
        tcRecyclerView.setLayoutManager(tcLayoutManager);
        tcRecyclerView.setAdapter(topCollegeAdapter);

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
        viewPagerModels.add(new ViewPagerModel("Do you want to find colleges in India?",R.drawable.university_back));
        viewPagerModels.add(new ViewPagerModel("Then ProjectT2E is here for you",R.drawable.iit_back));
        viewPagerModels.add(new ViewPagerModel("College Of Engineering Tezpur, Click here",R.drawable.tezpur_university));

        featuredCollegeModels = new ArrayList<>();
        featuredCollegeModels.add(new FeaturedCollegeModel(R.drawable.nit_logo,"National Institute Of technology Sikkim","#rank 1","Ravangla"));
        featuredCollegeModels.add(new FeaturedCollegeModel(R.drawable.nit_logo,"National Institute Of technology Silchar","#rank 1","Silchar"));
        featuredCollegeModels.add(new FeaturedCollegeModel(R.drawable.nit_logo,"National Institute Of technology Patna","#rank 1","Patna"));
        featuredCollegeModels.add(new FeaturedCollegeModel(R.drawable.nit_logo,"National Institute Of technology Rourkela","#rank 1","Rourkela"));
        featuredCollegeModels.add(new FeaturedCollegeModel(R.drawable.nit_logo,"National Institute Of technology Tricy","#rank 1","Tricypolly"));

        CityModelArrayList = new ArrayList<>();
        CityModelArrayList.add(new CityModel("Banglore",R.drawable.city1));
        CityModelArrayList.add(new CityModel("Hyderabad",R.drawable.city2));
        CityModelArrayList.add(new CityModel("Chennai",R.drawable.city3));
        CityModelArrayList.add(new CityModel("Kota",R.drawable.city4));
        CityModelArrayList.add(new CityModel("Guwahati",R.drawable.top_college3));

        StateModelArrayList = new ArrayList<>();
        StateModelArrayList.add(new StateModel("Assam",R.drawable.state));
        StateModelArrayList.add(new StateModel("Delhi",R.drawable.state));
        StateModelArrayList.add(new StateModel("Mumbai",R.drawable.state));

        CategoryModelArrayList = new ArrayList<>();
        CategoryModelArrayList.add(new CategoryModel("Engineering",R.drawable.engineering));
        CategoryModelArrayList.add(new CategoryModel("Management",R.drawable.management));
        CategoryModelArrayList.add(new CategoryModel("Architecture",R.drawable.architecture));
        CategoryModelArrayList.add(new CategoryModel("Medical",R.drawable.medical));
        CategoryModelArrayList.add(new CategoryModel("Pharmacy",R.drawable.pharmacy));


        TopCollegeModelArrayList = new ArrayList<>();
        TopCollegeModelArrayList.add(new TopCollegeModel("Top IIT",R.drawable.iit));
        TopCollegeModelArrayList.add(new TopCollegeModel("Top NIT",R.drawable.nit));
        TopCollegeModelArrayList.add(new TopCollegeModel("Top AIIMS",R.drawable.aiims));
        TopCollegeModelArrayList.add(new TopCollegeModel("Top IIM",R.drawable.top_college1));
        TopCollegeModelArrayList.add(new TopCollegeModel("Top Univerity",R.drawable.university));
        TopCollegeModelArrayList.add(new TopCollegeModel("Top Private College",R.drawable.top_college2));

    }


    private void initFeaturedCollegeSlider() {


        RecyclerView tcRecyclerView = parentView.findViewById(R.id.top_college_recycler_view);
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

    private void initViewPagerSlider()
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
        },5000,5000);

    }
}


