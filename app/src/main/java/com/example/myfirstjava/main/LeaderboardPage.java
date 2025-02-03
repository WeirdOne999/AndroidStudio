package com.example.myfirstjava.main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myfirstjava.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class LeaderboardPage extends AppCompatActivity implements View.OnClickListener {

    private Button _backbtn;
    private LinearLayout leaderboardContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.leaderboard);

        _backbtn = findViewById(R.id.back_btn);
        _backbtn.setOnClickListener(this);

        leaderboardContainer = findViewById(R.id.leaderboard_container);
        loadLeaderboard();
    }

    @Override
    public void onClick(View v) {
        if (v == _backbtn) {
            startActivity(new Intent().setClass(this, MainMenu.class));
        }
    }

    private void loadLeaderboard() {
        SharedPreferences prefs = getSharedPreferences("Leaderboard", MODE_PRIVATE);
        Map<String, ?> scoresMap = prefs.getAll();

        // Convert Map to List for sorting
        List<Map.Entry<String, Integer>> scoreList = new ArrayList<>();
        for (Map.Entry<String, ?> entry : scoresMap.entrySet()) {
            if (entry.getValue() instanceof Integer) {
                scoreList.add((Map.Entry<String, Integer>) entry);
                Log.d("Leaderboard", "UPDATED: PlayerName=" + entry.getKey() + " Score=" + entry.getValue());
            }
        }

        // Sort by highest score first
        Collections.sort(scoreList, (a, b) -> b.getValue() - a.getValue());

        // Clear previous views
        leaderboardContainer.removeAllViews();

        // Add each score to the UI
        for (Map.Entry<String, Integer> entry : scoreList) {
            String playerName = entry.getKey();
            int score = entry.getValue();

            TextView scoreEntry = new TextView(this);
            scoreEntry.setText(playerName + " - " + score);
            scoreEntry.setTextSize(24);
            scoreEntry.setTextColor(Color.BLUE);
            scoreEntry.setPadding(20, 30, 20, 10);
            scoreEntry.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            scoreEntry.setGravity(Gravity.CENTER_HORIZONTAL); // Center horizontally
            scoreEntry.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));


            leaderboardContainer.addView(scoreEntry);
        }
    }

}
