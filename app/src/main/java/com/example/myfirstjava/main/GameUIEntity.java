package com.example.myfirstjava.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;


//INTERFACE CLASS FOR MAINMENU FROM GAME SURFACE
public class GameUIEntity {
    private MainGameSurfaceView gameSurface;
    private int test = 0;

    public int ButtonPressed =0;

    public boolean CursorActionExecute = false;
    private boolean isResuming = true; // Flag to indicate if the update is ongoing
    public GameUIEntity(Context context, FrameLayout container) {
        gameSurface = new MainGameSurfaceView(context,container);
        container.addView(gameSurface); // Add the GameSurface to the provided container


        gameSurface.post(() -> setupButtonClicks());
    }

    public void setSize(int width, int height){
        gameSurface.setSize(width,height);
    }


    public void Update(){
        if (gameSurface != null){
            gameSurface.run();
        }

        if (isResuming) {
            resume();
        }
    }

    public void resume() {
        test++;
        if (gameSurface.characterButtons != null && gameSurface.characterButtons.length > 0 &&
                gameSurface.characteramounts != null && gameSurface.characteramounts.length > 0) {
            // Modify text for each button dynamically in the update loop
            gameSurface.post(() -> {
                for (int i = 0; i < gameSurface.characterButtons.length; i++) {
                    //gameSurface.characterButtons[i].setText(String.valueOf(gameSurface.characteramounts[i]));
                }
            });
        }
    }

    public void setupButtonClicks() {
        gameSurface.post(() -> {
            for (int i = 0; i < gameSurface.characterButtons.length; i++) {
                final int index = i;  // Use final to access inside the listener
//                gameSurface.characterButtons[i].setOnTouchListener(new View.OnTouchListener() {
//                    @SuppressLint("ClickableViewAccessibility")
//                    @Override
//                    public boolean onTouch(View v, MotionEvent event) {
//                        switch (event.getAction()){
//                            case MotionEvent.ACTION_DOWN:
//                                ButtonPressed = index;
//                                DecrementCharacterText(index);
//                                CursorActionExecute = true;
//                                return true;
//                        }
//                        return false;
//                    }
//                });
                gameSurface.characterButtons[i].setOnClickListener(v -> {
                    // Handle the button click for the i-th button
                    ButtonPressed = index;
                    //DecrementCharacterText(index);
                    CursorActionExecute = true;
                    // You can add more logic here, e.g., change background or trigger game actions
                });
            }
        });

        CursorActionExecute = false;
    }

    public void IncrementCharacterText(int index){
        gameSurface.characteramounts[index]++;
    }

    public void DecrementCharacterText(int index){
        gameSurface.characteramounts[index]--;
    }

    public void pause() {
        if (gameSurface != null) {
            gameSurface.pause();
        }
        isResuming = false; // Stop the resume updates when paused
    }

    public void startResuming() {
        isResuming = true;
    }

    // Stop continuous updates when the game is paused
    public void stopResuming() {
        isResuming = false;
    }
}
