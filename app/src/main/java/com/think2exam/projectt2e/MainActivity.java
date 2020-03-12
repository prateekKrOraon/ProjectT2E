package com.think2exam.projectt2e;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.think2exam.projectt2e.ui.home.HomeFragment;
import com.think2exam.projectt2e.ui.profile.ProfileFragment;
import com.think2exam.projectt2e.ui.quiz.QuizFragment;
import com.think2exam.projectt2e.ui.search.SearchFragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity  {

    HomeFragment mHomeFragment;
    QuizFragment mQuizFragment;
    SearchFragment mSearchFragment;
    ProfileFragment mProfileFragment;
    BottomNavigationView bottomNavigationView;

    public static MainActivity activity;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.enter, R.anim.exit);

        setLayout();


        activity = this;

    }

    public static MainActivity getInstance()
    {
        return activity;
    }

    public void setLayout()
    {
        setContentView(R.layout.activity_main);
        mHomeFragment = new HomeFragment(this);
        mQuizFragment = new QuizFragment();
        mSearchFragment = new SearchFragment(this);
        mProfileFragment = new ProfileFragment();

        //setting initial fragment to be home fragment
        switchFragment(mHomeFragment, HomeFragment.id);

        //Initializing Bottom navigation
        bottomNavigationView = findViewById(R.id.bottom_nav_bar);

        //Event listener for bottom navigation view
        bottomNavigationViewListener(bottomNavigationView);

        activity = this;

    }

    private void switchFragment(Fragment fragment,String id) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.host_fragment, fragment);
       // fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();
    }


    //Bottom navigation view listener
    private void bottomNavigationViewListener(BottomNavigationView bottomNavigationView) {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()){
                    case R.id.bottom_nav_bar_home:
                        switchFragment(mHomeFragment, HomeFragment.id);
                        break;
                    case R.id.bottom_nav_bar_quiz:
                        switchFragment(mQuizFragment, QuizFragment.id);
                        break;
                    case R.id.bottom_nav_bar_search:
                        switchFragment(mSearchFragment, SearchFragment.id);
                        break;
                    case R.id.bottom_nav_bar_profile:
                        switchFragment(mProfileFragment, ProfileFragment.id);
                        break;
                    default:
                        return false;
                }
                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {

        int selectedItemId = bottomNavigationView.getSelectedItemId();
        if(selectedItemId != R.id.bottom_nav_bar_home){
            bottomNavigationView.setSelectedItemId(R.id.bottom_nav_bar_home);
        }else{
            super.onBackPressed();
        }

    }
}
