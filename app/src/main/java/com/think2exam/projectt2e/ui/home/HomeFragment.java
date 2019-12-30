package com.think2exam.projectt2e.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.think2exam.projectt2e.R;
import com.think2exam.projectt2e.ui.activities.CollegeInfoActivity;

public class HomeFragment extends Fragment {
    public static final String id = "home_fragment";
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        textView.setText("Home Fragment");

        Button button = root.findViewById(R.id.home_button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity().getApplicationContext(), CollegeInfoActivity.class));
            }
        });
        return root;
    }
}