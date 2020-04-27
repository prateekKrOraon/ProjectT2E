package com.think2exam.projectt2e;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.think2exam.projectt2e.modals.UserModel;
import com.think2exam.projectt2e.ui.home.HomeFragment;
import com.think2exam.projectt2e.ui.profile.ProfileFragment;
import com.think2exam.projectt2e.ui.quiz.QuizFragment;
import com.think2exam.projectt2e.ui.search.SearchFragment;
import com.think2exam.projectt2e.utilities.User;

import java.lang.reflect.Constructor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import static com.think2exam.projectt2e.Constants.EMAIL_FEEDBACK;

public class MainActivity extends AppCompatActivity  {

    HomeFragment mHomeFragment;
    QuizFragment mQuizFragment;
    SearchFragment mSearchFragment;
    ProfileFragment mProfileFragment;
    BottomNavigationView bottomNavigationView;
    ImageView tbIcon;
    public static MainActivity activity;
    public User user = User.getInstance();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.enter, R.anim.exit);

        setLayout();
        getUserFromSharedPref();


        activity = this;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    public void getUserFromSharedPref(){
        SharedPreferences Prefs= getSharedPreferences("user",MODE_PRIVATE);
        Gson gson = new Gson();
        String json = Prefs.getString("user_details", "");
        UserModel userModel = gson.fromJson(json, UserModel.class);
        if(userModel!=null){
            user.setId(userModel.getId());
            user.setFname(userModel.getFname());
            user.setLname(userModel.getLname());
            user.setPhoneNo(userModel.getMobile());
            user.setImage(userModel.getImage());
            user.setEmail(userModel.getEmail());
            user.setTotalMatches(userModel.getTotalMatches());
            user.setTotalPoints(userModel.getTotalPoints());
            user.setWins(userModel.getWins());
            user.setCorrectAns(userModel.getCorrectAns());
            user.setWrongAns(userModel.getWrongAns());
            user.setNoAns(userModel.getNoAns());
            user.setAvgPoints();
        }

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

        tbIcon = findViewById(R.id.icon_toolbar);
        tbIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPopup();
            }
        });

    }

    private void openPopup(){
        final PopupMenu menu = new PopupMenu(this,tbIcon);
        menu.getMenu().add(0,0,0,"Rate us");
        menu.getMenu().add(0,1,1,"Give us feedback");

        menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                 if(item.getItemId()==0){
                     rateUs();
                 }else if(item.getItemId()==1) {
                     sendFeedback();
                 }
                 return true;
            }
        });
        menu.show();


    }

    private void sendFeedback(){
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + EMAIL_FEEDBACK));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Feedback Think2Exam App");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Device: "+getDeviceName());
        startActivity(Intent.createChooser(emailIntent, "Send mail..."));
    }

    private String getDeviceName(){
        String deviceName = android.os.Build.MANUFACTURER + " " + android.os.Build.MODEL;
        return deviceName;
    }

    private void rateUs(){
        Uri uri = Uri.parse("market://details?id=" + getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
        }
    }


    private void switchFragment(Fragment fragment,String id) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.host_fragment, fragment);
        fragmentTransaction.addToBackStack("");
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
            super.onBackPressed();

    }
}
