package com.think2exam.projectt2e.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.think2exam.projectt2e.R;

import static com.think2exam.projectt2e.Constants.PRIVACY;

public class AboutAppActivity extends AppCompatActivity {

    LinearLayout defaultLayout;
    LinearLayout developersLayout;
    LinearLayout privacyPolicyLink;
    LinearLayout thirdPartyRes;

    int count=4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_app);
        Toolbar toolbar = findViewById(R.id.toolbar);

        try {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }catch(NullPointerException ex){
            ex.printStackTrace();
        }

        defaultLayout = findViewById(R.id.default_ll);
        developersLayout = findViewById(R.id.developers_ll);
        defaultLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(count>0){
                    Toast.makeText(AboutAppActivity.this, ""+count, Toast.LENGTH_SHORT).show();
                    count--;
                }else {
                    Toast.makeText(AboutAppActivity.this, ""+count, Toast.LENGTH_SHORT).show();
                    developersLayout.setVisibility(View.VISIBLE);
                    defaultLayout.setVisibility(View.GONE);
                    developersLayout.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.fade_in));
                }
            }
        });

        thirdPartyRes = findViewById(R.id.tpr_button);

        thirdPartyRes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ThirdPartyResourcesActivity.class));
            }
        });

        privacyPolicyLink = findViewById(R.id.privacy_button);
        privacyPolicyLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(PRIVACY));
                startActivity(intent);
            }
        });


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
