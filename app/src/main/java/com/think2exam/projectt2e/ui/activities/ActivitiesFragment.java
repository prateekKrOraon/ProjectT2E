package com.think2exam.projectt2e.ui.activities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.think2exam.projectt2e.R;

public class ActivitiesFragment extends Fragment {

    public static final String id = "activities_fragment";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_activities, container, false);
        final TextView textView = root.findViewById(R.id.text_activities);
        textView.setText("Activities Fragment");
        return root;
    }
}