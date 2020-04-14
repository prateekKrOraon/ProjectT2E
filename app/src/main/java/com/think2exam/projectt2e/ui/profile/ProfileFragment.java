package com.think2exam.projectt2e.ui.profile;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.think2exam.projectt2e.Constants;
import com.think2exam.projectt2e.LogInActivity;
import com.think2exam.projectt2e.MainActivity;
import com.think2exam.projectt2e.R;
import com.think2exam.projectt2e.adapters.ProfileOptionsAdapter;
import com.think2exam.projectt2e.modals.ProfileOptionsModal;
import com.think2exam.projectt2e.modals.UserModel;
import com.think2exam.projectt2e.utilities.User;

import java.util.ArrayList;
import static com.think2exam.projectt2e.MainActivity.*;

public class ProfileFragment extends Fragment {

    public static final String id = "profile_fragment";

    private ArrayList<ProfileOptionsModal> personalOptions;
    private ArrayList<ProfileOptionsModal> appOptions;
    User user = User.getInstance();
    View root;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        setUserDetails(root);

        personalOptions = new ArrayList<>();
        appOptions = new ArrayList<>();

        initializeOptions(root);

        if(user.id==-1){
            setLogin(root);
        }else {
            setLogout(root);
        }
        this.root=root;
        return root;
    }


    public void setLogin(View root){
        TextView logIn = root.findViewById(R.id.log_in);
        logIn.setVisibility(View.VISIBLE);
        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), LogInActivity.class));
                MainActivity.getInstance().finish();
            }
        });
    }

    public void setLogout(final View root){
        final AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Do you want to logout ?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        user.setId(-1);
                        deleteFromSharefPref();
                        startActivity(new Intent(getContext(), LogInActivity.class));
                        MainActivity.getInstance().finish();
                        dialog.cancel();
                    }
                });
          builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();

            }
        });
        TextView logOut = root.findViewById(R.id.log_out);
        logOut.setVisibility(View.VISIBLE);
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.show();

            }
        });
    }

    public void deleteFromSharefPref(){
        SharedPreferences Prefs= getContext().getSharedPreferences("user",MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = Prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(null);
        prefsEditor.putString("user_details", json);
        prefsEditor.apply();
    }

    public void setUserDetails(View root)
    {
        TextView Name = root.findViewById(R.id.user_name);
        TextView Phone = root.findViewById(R.id.user_mobile);
        if(user.id!=-1) {

            Name.setText(user.fname + " " + user.lname);
            Phone.setText("+91-" + user.phoneNo);
        }else {

            Name.setText("Guest");
            Phone.setText("+91-XXXXXXXXXX" );
        }
    }


    private void initializeOptions(View root) {

        //Creating list for personal section of profile
        personalOptions.add(new ProfileOptionsModal("Edit Profile",R.drawable.outline_person_black_48));
        personalOptions.add(new ProfileOptionsModal("Points Summary",R.drawable.ic_trophy_outline_white_48dp));
        personalOptions.add(new ProfileOptionsModal("Internship at Think2Exam",R.drawable.ic_laptop_black_48dp));


        LinearLayoutManager personalManger = new LinearLayoutManager(this.getContext());
        personalManger.setOrientation(LinearLayoutManager.VERTICAL);

        ProfileOptionsAdapter personalAdapter = new ProfileOptionsAdapter(this.getContext(),personalOptions, Constants.TAG_PROFILE_PERSONAL);

        RecyclerView personal = root.findViewById(R.id.profile_recycler_view_personal);
        personal.setLayoutManager(personalManger);
        personal.setAdapter(personalAdapter);

        //creating list for application section of profile
        appOptions.add(new ProfileOptionsModal("Share",R.drawable.baseline_share_black_48));
        appOptions.add(new ProfileOptionsModal("About Application",R.drawable.ic_app_information_black_24dp));
        appOptions.add(new ProfileOptionsModal("About Think2Exam",R.drawable.outline_info_black_48));

        LinearLayoutManager appManager = new LinearLayoutManager(this.getContext());
        personalManger.setOrientation(LinearLayoutManager.VERTICAL);

        ProfileOptionsAdapter appAdapter = new ProfileOptionsAdapter(this.getContext(),appOptions,Constants.TAG_PROFILE_APPLICATION);

        RecyclerView app = root.findViewById(R.id.profile_recycler_view_application);
        app.setLayoutManager(appManager);
        app.setAdapter(appAdapter);

    }

    @Override
    public void onResume() {
        super.onResume();
        setUserDetails(root);
    }
}
