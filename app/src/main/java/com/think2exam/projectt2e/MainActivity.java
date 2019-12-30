package com.think2exam.projectt2e;

import android.os.Bundle;
import android.view.MenuItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.think2exam.projectt2e.ui.activities.ActivitiesFragment;
import com.think2exam.projectt2e.ui.home.HomeFragment;
import com.think2exam.projectt2e.ui.profile.ProfileFragment;
import com.think2exam.projectt2e.ui.quiz.QuizFragment;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {

    HomeFragment mHomeFragment;
    QuizFragment mQuizFragment;
    ActivitiesFragment mActivitiesFragment;
    ProfileFragment mProfileFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mHomeFragment = new HomeFragment();
        mQuizFragment = new QuizFragment();
        mActivitiesFragment = new ActivitiesFragment();
        mProfileFragment = new ProfileFragment();

        //setting support toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //setting initial fragment to be home fragment
        switchFragment(mHomeFragment,HomeFragment.id);

        //Initializing Bottom navigation
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav_bar);

        //Event listener for bottom navigation view
        bottomNavigationViewListener(bottomNavigationView);
    }



    private void switchFragment(Fragment fragment,String id) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.host_fragment, fragment);
        fragmentTransaction.addToBackStack(id);
        fragmentTransaction.commit();
    }


    //Bottom navigation view listener
    private void bottomNavigationViewListener(BottomNavigationView bottomNavigationView) {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()){
                    case R.id.bottom_nav_bar_home:
                        switchFragment(mHomeFragment,HomeFragment.id);
                        break;
                    case R.id.bottom_nav_bar_quiz:
                        switchFragment(mQuizFragment,QuizFragment.id);
                        break;
                    case R.id.bottom_nav_bar_activities:
                        switchFragment(mActivitiesFragment,ActivitiesFragment.id);
                        break;
                    case R.id.bottom_nav_bar_profile:
                        switchFragment(mProfileFragment,ProfileFragment.id);
                        break;
                    default:
                        return false;
                }
                return true;
            }
        });
    }

}
