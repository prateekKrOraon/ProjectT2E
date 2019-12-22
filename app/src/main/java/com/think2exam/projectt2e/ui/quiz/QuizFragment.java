package com.think2exam.projectt2e.ui.quiz;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.think2exam.projectt2e.R;

public class QuizFragment extends Fragment {

    public static final String id = "quiz_fragment";
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_quiz, container, false);
        final TextView textView = root.findViewById(R.id.text_quiz);
        textView.setText("Quiz Fragment");
        return root;
    }
}