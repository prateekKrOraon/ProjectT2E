package com.think2exam.projectt2e.ui.quiz;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.Fragment;

import com.think2exam.projectt2e.R;
import com.think2exam.projectt2e.ui.activities.QuizCategoryActivity;

public class QuizFragment extends Fragment {

    public static final String id = "quiz_fragment";
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_quiz, container, false);

        LinearLayoutCompat playButton = root.findViewById(R.id.quiz_play_now_btn);

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity().getApplicationContext(), QuizCategoryActivity.class));
            }
        });

        return root;
    }
}