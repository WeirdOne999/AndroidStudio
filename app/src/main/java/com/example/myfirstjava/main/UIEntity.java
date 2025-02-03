package com.example.myfirstjava.main;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.myfirstjava.R;

public class UIEntity {
    private GameSurface gameSurface;
    private TextView highScoreText;
    private static final String PREFS_NAME = "HighScorePrefs";
    private static final String HIGH_SCORE_KEY = "HighScore";

    public static UIEntity instance;

    public UIEntity(Context context, FrameLayout container, int backgroundId) {

        instance = this;
        gameSurface = new GameSurface(context, container, backgroundId);
        container.addView(gameSurface);

        LayoutInflater inflater = LayoutInflater.from(context);
        View layout = inflater.inflate(R.layout.mainmenu, container, false);
        container.addView(layout);


        highScoreText = layout.findViewById(R.id.highscore);


        updateHighScoreText(context);

    }

    public void setSize(int width, int height) {
        gameSurface.setSize(width, height);
    }

    public void resume() {
        if (gameSurface != null) {
            gameSurface.resume();
        }
    }

    public void pause() {
        if (gameSurface != null) {
            gameSurface.pause();
        }
    }


    private void updateHighScoreText(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        int highScore = prefs.getInt(HIGH_SCORE_KEY, 0);

        new Handler(Looper.getMainLooper()).post(() -> {
            highScoreText.setText("HIGH SCORE: " + highScore);
        });
    }


    public void saveHighScore(Context context, int newScore) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        int currentHighScore = prefs.getInt(HIGH_SCORE_KEY, 0);
        if (newScore > currentHighScore) {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt(HIGH_SCORE_KEY, newScore);
            editor.apply();
            updateHighScoreText(context);
        }
    }

    public void resetHighScore(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove(HIGH_SCORE_KEY);
        editor.apply();
        updateHighScoreText(context);
    }

}
