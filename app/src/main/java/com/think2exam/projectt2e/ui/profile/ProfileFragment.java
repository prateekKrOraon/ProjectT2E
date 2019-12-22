package com.think2exam.projectt2e.ui.profile;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.think2exam.projectt2e.R;

public class ProfileFragment extends Fragment {

    public static final String id = "profile_fragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        TextView textView = root.findViewById(R.id.text_profile);
        textView.setText("Profile Fragment");

        return root;
    }

}
