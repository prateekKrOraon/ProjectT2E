package com.think2exam.projectt2e.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;

import com.think2exam.projectt2e.R;
import com.think2exam.projectt2e.adapters.LeaderBoardAdapter;
import com.think2exam.projectt2e.modals.LeaderBoardModal;
import com.think2exam.projectt2e.utilities.DBOperations;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.think2exam.projectt2e.Constants.FIRST_NAME;
import static com.think2exam.projectt2e.Constants.LAST_NAME;
import static com.think2exam.projectt2e.Constants.TOTAL_POINTS;

public class LeaderBoardActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    LeaderBoardAdapter mLeaderBoardAdapter;
    ArrayList<LeaderBoardModal> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(list == null){
            setContentView(R.layout.loading);
            GetTopPlayers getTopPlayers = new GetTopPlayers();
            getTopPlayers.execute();
        }else{
            initializeLayout();
        }




        //TODO: @dhrubajit implement api




//        for(int i =0; i<10 ;i++){
//            list.add(
//                    new LeaderBoardModal(
//                            "#" + (i+1),
//                            R.drawable.outline_person_black_48,
//                            "Player " + i,
//                            "Total XP " + (10-i)
//                    )
//            );
//        }


    }

    private void initializeLayout(){
        setContentView(R.layout.activity_leader_board);


        Toolbar toolbar = findViewById(R.id.toolbar_leader_board);
        try {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }catch (NullPointerException ex){
            ex.printStackTrace();
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

    private class GetTopPlayers extends AsyncTask<String,Void,Void>{

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            initializeLayout();
        }

        @Override
        protected Void doInBackground(String... strings) {
            DBOperations dbOperations = DBOperations.getInstance();
            JSONArray array = dbOperations.getTopPlayers();
            list = new ArrayList<>();
            try {
                for(int i = 0;i<array.length();i++){
                    JSONObject object = array.getJSONObject(i);
                    list.add(
                            new LeaderBoardModal(
                                    i+1 + "",
                                    R.drawable.outline_person_black_48,
                                    object.getString(FIRST_NAME) + " " + object.getString(LAST_NAME),
                                    object.getInt(TOTAL_POINTS) + "XP"
                            )
                    );
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
