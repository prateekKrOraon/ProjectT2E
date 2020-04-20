package com.think2exam.projectt2e.ui.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hanks.htextview.base.AnimationListener;
import com.hanks.htextview.base.HTextView;
import com.hanks.htextview.typer.TyperTextView;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import com.think2exam.projectt2e.R;
import com.think2exam.projectt2e.adapters.CategoryAdapter;
import com.think2exam.projectt2e.adapters.CityAdapter;
import com.think2exam.projectt2e.adapters.ColImageSliderAdapter;
import com.think2exam.projectt2e.adapters.FeaturedCollegeAdapter;
import com.think2exam.projectt2e.adapters.HomeImageSliderAdapter;
import com.think2exam.projectt2e.adapters.QuizCatAdapterHome;
import com.think2exam.projectt2e.adapters.SnapHelperOneByOne;
import com.think2exam.projectt2e.adapters.StateAdapter;
import com.think2exam.projectt2e.adapters.PrestigiousCollegeAdapter;
import com.think2exam.projectt2e.modals.CategoryModel;
import com.think2exam.projectt2e.modals.CityModel;
import com.think2exam.projectt2e.modals.FeaturedCollegeModel;
import com.think2exam.projectt2e.modals.HomeImageSliderModel;
import com.think2exam.projectt2e.modals.QuizCategoryModal;
import com.think2exam.projectt2e.modals.StateModel;
import com.think2exam.projectt2e.modals.PrestigiousCollegeModel;

import com.think2exam.projectt2e.utilities.DBOperations;
import com.think2exam.projectt2e.utilities.FeaturedColleges;
import com.think2exam.projectt2e.utilities.QuizCategories;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


import static com.think2exam.projectt2e.Constants.ID;
import static com.think2exam.projectt2e.Constants.QUIZ_CATEGORY;
import static com.think2exam.projectt2e.Constants.QUIZ_CATEGORY_DES;
import static com.think2exam.projectt2e.Constants.SHARED_PREF;


public class HomeFragment extends Fragment {
    public static final String id = "home_fragment";
    private static  View parentView;
    private ArrayList<CityModel> CityModelArrayList;
    private ArrayList<StateModel> StateModelArrayList;
    private ArrayList<CategoryModel> CategoryModelArrayList;
    private ArrayList<PrestigiousCollegeModel> prestigiousCollegeModelArrayList;

    private static Context mainActivityContext;
    FeaturedCollegeAdapter featuredCollegeAdapter;
    QuizCatAdapterHome quizCatAdapterHome;

    private int catId;
    public FeaturedColleges featuredColleges = FeaturedColleges.getInstance();
    public QuizCategories quizCategories = QuizCategories.getInstance();
    public ArrayList<FeaturedCollegeModel> featuredCollegeModels;
    public ArrayList<QuizCategoryModal> quizCategoryModals;


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
        SharedPreferences pref = getContext().getSharedPreferences("MyPreference", 0);
        catId = pref.getInt("category_id",-1);

        //set Top cities and top states
        final TextView topCity = root.findViewById(R.id.popular_city_text);
        TextView topState = root.findViewById(R.id.popular_state_text);
        if(catId!=R.string.university) {
            topCity.setText(getResources().getString(catId) + " college in top cities");
            topState.setText(getResources().getString(catId) + " college in top states");
        }
        else
        {
            topCity.setText("Universities in top cities");
            topState.setText("Universities in top states");
        }




        //set ArrayList
        setArrayList();
        featuredCollegeModels = featuredColleges.getFeaturedColleges();
        if(featuredCollegeModels.size()==0) {
            new GetTop5Colleges().execute();
        }
        initFeaturedCollegeSlider();

        quizCategoryModals = quizCategories.getQuizCategories();
        if(quizCategoryModals.size()==0){
            new GetQuiZCategory().execute();
        }
        initQuizCatSlider(root);


        setPrestigiousCollege(root);

        setCityAdapter(root);

        setStateAdapter(root);

        setCategoryAdapter(root);

        setHomeSliderLayout(root);

        setTyperText(root);


        return root;
    }

    private void setTyperText(View root){
        TyperTextView typerTextView = root.findViewById(R.id.text_typer);
        typerTextView.setTyperSpeed(120);
        typerTextView.setCharIncrease(1);
            typerTextView.animateText("Do you want to find right college?");
            typerTextView.setAnimationListener(new AnimationListener() {
                @Override
                public void onAnimationEnd(HTextView hTextView) {
                    hTextView.animateText("Then THINK2EXAM is here for you");
                    hTextView.setAnimationListener(new AnimationListener() {
                        @Override
                        public void onAnimationEnd(HTextView hTextView) {
                            hTextView.animateText("with 6000+ Colleges");
                            hTextView.setAnimationListener(new AnimationListener() {
                                @Override
                                public void onAnimationEnd(HTextView hTextView) {

                                }
                            });
                        }
                    });
                }
            });

    }

    private void setHomeSliderLayout(View root)
    {
        ArrayList<HomeImageSliderModel> Images = new ArrayList<>();
        Images.add(new HomeImageSliderModel(R.drawable.home_slider_image_1,"Buddha Park, Ravangla"));
        Images.add(new HomeImageSliderModel(R.drawable.home_slider_image_2,"AIIMS Delhi"));
        Images.add(new HomeImageSliderModel(R.drawable.home_slider_image_3,"IIT Guwahati"));
        SliderView imageSlider = root.findViewById(R.id.home_image_slider);
        imageSlider.setSliderAdapter(new HomeImageSliderAdapter(this,Images));
        imageSlider.startAutoCycle();
        imageSlider.setIndicatorAnimation(IndicatorAnimations.WORM);
        imageSlider.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
    }

    public void initQuizCatSlider(View root)
    {
        RecyclerView qRecyclerView = root.findViewById(R.id.quiz_cat_rv_home);
        RecyclerView.LayoutManager stLayoutManager = new LinearLayoutManager(mainActivityContext);
        ((LinearLayoutManager) stLayoutManager).setOrientation(LinearLayoutManager.HORIZONTAL);
        quizCatAdapterHome = new QuizCatAdapterHome(quizCategoryModals,mainActivityContext);
        qRecyclerView.setHasFixedSize(true);
        qRecyclerView.setLayoutManager(stLayoutManager);
        qRecyclerView.setAdapter(quizCatAdapterHome);
    }

    private void setCategoryAdapter(View root)
    {
        //Recycler view for category
        RecyclerView catRecyclerView   = root.findViewById(R.id.home_category_recycler_view);
        RecyclerView.LayoutManager catLayoutManager = new GridLayoutManager(mainActivityContext,3);
        CategoryAdapter categoryAdapter = new CategoryAdapter(CategoryModelArrayList,mainActivityContext);
        catRecyclerView.setHasFixedSize(true);
        catRecyclerView.setLayoutManager(catLayoutManager);
        catRecyclerView.setAdapter(categoryAdapter);

    }

    private void setStateAdapter(View root)
    {
        //Recycler view for state
        RecyclerView stRecyclerView = root.findViewById(R.id.home_state_recycler_view);
        RecyclerView.LayoutManager stLayoutManager = new LinearLayoutManager(mainActivityContext);
        ((LinearLayoutManager) stLayoutManager).setOrientation(LinearLayoutManager.HORIZONTAL);
        StateAdapter stateAdapter = new StateAdapter(StateModelArrayList,mainActivityContext);
        stRecyclerView.setHasFixedSize(true);
        stRecyclerView.setLayoutManager(stLayoutManager);
        stRecyclerView.setAdapter(stateAdapter);
    }

    private void setCityAdapter(View root)
    {
        //Recycler view for City
        RecyclerView ctRecyclerView = root.findViewById(R.id.home_city_recycler_view);
        RecyclerView.LayoutManager ctLayoutManager = new LinearLayoutManager(mainActivityContext);
        ((LinearLayoutManager) ctLayoutManager).setOrientation(LinearLayoutManager.HORIZONTAL);
        CityAdapter cityAdapter = new CityAdapter(CityModelArrayList,mainActivityContext);
        ctRecyclerView.setHasFixedSize(true);
        ctRecyclerView.setLayoutManager(ctLayoutManager);
        ctRecyclerView.setAdapter(cityAdapter);
    }


    private void setPrestigiousCollege(View root)
    {
        //Recycler view for top colleges
        RecyclerView tcRecyclerView = root.findViewById(R.id.home_top_colleges_recycler_view);
        RecyclerView.LayoutManager tcLayoutManager = new LinearLayoutManager(mainActivityContext);
        ((LinearLayoutManager) tcLayoutManager).setOrientation(LinearLayoutManager.HORIZONTAL);
        PrestigiousCollegeAdapter prestigiousCollegeAdapter = new PrestigiousCollegeAdapter(prestigiousCollegeModelArrayList,mainActivityContext);
        tcRecyclerView.setHasFixedSize(true);
        tcRecyclerView.setLayoutManager(tcLayoutManager);
        tcRecyclerView.setAdapter(prestigiousCollegeAdapter);
    }

    private void setArrayList()
    {

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
        prestigiousCollegeModelArrayList.add(new PrestigiousCollegeModel(R.string.iit,R.drawable.iit,R.string.engineering));
        prestigiousCollegeModelArrayList.add(new PrestigiousCollegeModel(R.string.nit,R.drawable.nit,R.string.engineering));
        prestigiousCollegeModelArrayList.add(new PrestigiousCollegeModel(R.string.aiims,R.drawable.aiims,R.string.medical_and_dental));
        prestigiousCollegeModelArrayList.add(new PrestigiousCollegeModel(R.string.iim,R.drawable.top_college1,R.string.management));
        prestigiousCollegeModelArrayList.add(new PrestigiousCollegeModel(R.string.university,R.drawable.university,R.string.university));

    }


    private void initFeaturedCollegeSlider() {


        RecyclerView tcRecyclerView = parentView.findViewById(R.id.featured_college_recycler_view);
        tcRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager tcLayoutManager = new LinearLayoutManager(mainActivityContext);
        ((LinearLayoutManager) tcLayoutManager).setOrientation(LinearLayoutManager.HORIZONTAL);
        LinearSnapHelper linearSnapHelper = new SnapHelperOneByOne();
        linearSnapHelper.attachToRecyclerView(tcRecyclerView);
        featuredCollegeAdapter = new FeaturedCollegeAdapter(featuredCollegeModels,mainActivityContext);
        tcRecyclerView.setLayoutManager(tcLayoutManager);
        tcRecyclerView.setAdapter(featuredCollegeAdapter);


    }



    private class GetTop5Colleges extends AsyncTask<Void, Void, Void> {

        JSONArray jsonArray=null;



        @Override
        protected Void doInBackground(Void... arg0) {


            DBOperations dbOperations = DBOperations.getInstance();
            jsonArray = dbOperations.getColleges(SHARED_PREF,SHARED_PREF,"",catId,"",-1);

            if(jsonArray!=null && featuredCollegeModels.size()==0) {
                try {
                    setTop5Colleges(jsonArray);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            featuredCollegeModels = featuredColleges.getFeaturedColleges();
            featuredCollegeAdapter.notifyDataSetChanged();
        }
    }

    private void setTop5Colleges(JSONArray jsonArray) throws Exception {
        for(int i=0;i<jsonArray.length();i++)
        {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            featuredColleges.setFeaturedColleges(new FeaturedCollegeModel(R.drawable.col_logo_default,
                    jsonObject.getInt("id"),
                    jsonObject.getString("college_name"),
                    "rank "+jsonObject.getString("college_rank"),
                    jsonObject.getString("college_location"),
                    catId));
        }

    }


    private void setCategories(JSONArray jsonArray){
        if(jsonArray != null){
            try {
                for(int i = 0; i<jsonArray.length(); i++){
                    JSONObject object = jsonArray.getJSONObject(i);
                    quizCategories.setQuizCategories(
                            new QuizCategoryModal(
                                    Integer.parseInt(object.getString(ID)),
                                    object.getString(QUIZ_CATEGORY),
                                    object.getString(QUIZ_CATEGORY_DES),
                                    R.drawable.ic_google_physical_web_black_48dp
                            )
                    );
                }
            }catch (JSONException e){
                e.printStackTrace();
            }
        }
    }

    private class GetQuiZCategory extends AsyncTask<Void,Void,Void> {


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            quizCategoryModals = quizCategories.getQuizCategories();
            quizCatAdapterHome.notifyDataSetChanged();

        }

        @Override
        protected Void doInBackground(Void... strings) {

            DBOperations dbOperations = DBOperations.getInstance();
            JSONArray jsonArray = dbOperations.getCategories();
            if(jsonArray!=null && quizCategoryModals.size()==0) {
                setCategories(jsonArray);
            }

            return null;
        }
    }



}
