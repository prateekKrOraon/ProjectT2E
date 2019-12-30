package com.think2exam.projectt2e.ui.profile;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.think2exam.projectt2e.Constants;
import com.think2exam.projectt2e.R;
import com.think2exam.projectt2e.adapters.ProfileOptionsAdapter;
import com.think2exam.projectt2e.modals.ProfileOptionsModal;

import java.util.ArrayList;

public class ProfileFragment extends Fragment {

    public static final String id = "profile_fragment";

    private ArrayList<ProfileOptionsModal> personalOptions;
    private ArrayList<ProfileOptionsModal> appOptions;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        personalOptions = new ArrayList<>();
        appOptions = new ArrayList<>();

        initializeOptions(root);
        return root;
    }

    private void initializeOptions(View root) {

        //Creating list for personal section of profile
        personalOptions.add(new ProfileOptionsModal("My Profile",R.drawable.outline_person_black_48));
        personalOptions.add(new ProfileOptionsModal("Points Summary",R.drawable.ic_trophy_outline_white_48dp));

        LinearLayoutManager personalManger = new LinearLayoutManager(this.getContext());
        personalManger.setOrientation(LinearLayoutManager.VERTICAL);

        ProfileOptionsAdapter personalAdapter = new ProfileOptionsAdapter(this.getContext(),personalOptions, Constants.TAG_PROFILE_PERSONAL);

        RecyclerView personal = root.findViewById(R.id.profile_recycler_view_personal);
        personal.setLayoutManager(personalManger);
        personal.setAdapter(personalAdapter);

        //creating list for application section of profile
        appOptions.add(new ProfileOptionsModal("Share",R.drawable.baseline_share_black_48));
        appOptions.add(new ProfileOptionsModal("Support",R.drawable.outline_headset_mic_black_48));
        appOptions.add(new ProfileOptionsModal("About Think2Exam",R.drawable.outline_info_black_48));

        LinearLayoutManager appManager = new LinearLayoutManager(this.getContext());
        personalManger.setOrientation(LinearLayoutManager.VERTICAL);

        ProfileOptionsAdapter appAdapter = new ProfileOptionsAdapter(this.getContext(),appOptions,Constants.TAG_PROFILE_APPLICATION);

        RecyclerView app = root.findViewById(R.id.profile_recycler_view_application);
        app.setLayoutManager(appManager);
        app.setAdapter(appAdapter);

    }

}
