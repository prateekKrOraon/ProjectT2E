package com.think2exam.projectt2e.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.think2exam.projectt2e.R;
import com.think2exam.projectt2e.adapters.LeaderBoardAdapter;
import com.think2exam.projectt2e.modals.LeaderBoardModal;

import java.util.ArrayList;

public class LeaderBoardActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    LeaderBoardAdapter mLeaderBoardAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_board);

        Toolbar toolbar = findViewById(R.id.toolbar_leader_board);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ArrayList<LeaderBoardModal> list = new ArrayList<>();

        for(int i =0; i<10 ;i++){
            list.add(
                    new LeaderBoardModal(
                            "#" + (i+1),
                            R.drawable.outline_person_black_48,
                            "Player " + i,
                            "Level " + (10-i)
                    )
            );
        }

        mRecyclerView = findViewById(R.id.leader_board_recycler_view);
        mLayoutManager = new LinearLayoutManager(this);
        mLeaderBoardAdapter = new LeaderBoardAdapter(this,list);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mLeaderBoardAdapter);

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
