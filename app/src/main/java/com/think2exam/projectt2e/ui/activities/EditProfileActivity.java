package com.think2exam.projectt2e.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

import android.graphics.Color;
import android.os.Bundle;

import com.think2exam.projectt2e.R;

public class EditProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        AppCompatButton saveButton = findViewById(R.id.save_profile_changes);

        boolean isEnabled = saveButton.isEnabled();

        saveButton.setBackgroundResource(isEnabled? R.drawable.quiz_option_button:R.drawable.quiz_button_border);
    }
}
