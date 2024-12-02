package com.example.myfirstjava.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;


//INTERFACE CLASS FOR MAINMENU FROM GAME SURFACE
public class GameUIEntity {
    private MainGameSurfaceView gameSurface;
    private int test = 0;

    public int ButtonPressed =0;
    public boolean GoToHouse = false;
    public boolean CursorActionExecute = false;

    private boolean isResuming = true; // Flag to indicate if the update is ongoing


    public GameUIEntity(Context context, FrameLayout container) {
        gameSurface = new MainGameSurfaceView(context,container);
        container.addView(gameSurface); // Add the GameSurface to the provided container


        gameSurface.post(() -> setupButtonClicks());

        gameSurface.createhatchingbuttons();
        gameSurface.creategamebuttons();
        gameSurface.GameLayout();
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
                    gameSurface.characterButtons[i].setText(String.valueOf(gameSurface.characteramounts[i]));
                }
            });
        }
        EggUpdate();
        Log.e("GameUIEntity", "EGGS SIZE:" + String.valueOf(gameSurface.eggs.size()));
        Log.e("GameUIEntity", "EGGSBUTTON SIZE:" + String.valueOf(gameSurface.eggButtons.size()));

        for (int i =0; i < gameSurface.characteramounts.length; i++){
            if (gameSurface.characteramounts[i] <= 0){
                gameSurface.characteramounts[i] = 0;
            }
        }

        //setupButtonClicks();
    }

    public void setupButtonClicks() {
        gameSurface.post(() -> {
            ButtonPressed = -1;

            for (int i = 0; i < gameSurface.characterButtons.length; i++) {
                final int index = i;  // Use final to access inside the listener
                //if (gameSurface.characteramounts[i] >0){
                    gameSurface.characterButtons[i].setOnTouchListener(new View.OnTouchListener() {

                        @SuppressLint("ClickableViewAccessibility")
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            switch (event.getAction()) {
                                case MotionEvent.ACTION_DOWN:
                                    MainGameScene.instance.Planting = true;
                                    ButtonPressed = index;
                                    CursorActionExecute = true;
                                    DecrementCharacterText(index);
                            }
                            return false;
                        }

                    });

                    gameSurface.characterButtons[i].setOnClickListener(v -> {
                        // Handle the button click for the i-th button
                        for (Button buttons : gameSurface.hatchingeggs){
                            //buttons.setVisibility(View.GONE);

                        }
                        DecrementCharacterText(index);



                        // You can add more logic here, e.g., change background or trigger game actions
                    });


               // }




                    gameSurface.toHouseButton.setOnClickListener(v -> {
                        GoToHouse = true;

                        gameSurface.HatchingLayout();


                    });

                gameSurface.toFarmButton.setOnClickListener(v -> {
                    GoToHouse = false;

                    gameSurface.GameLayout();


                });

            }
            if (gameSurface.hatchingeggs != null){
                for (int i = 0; i < gameSurface.hatchingeggs.length; i++) {
                    final int index = i;  // Use final to access inside the listener
                    if (MainGameScene.instance.Egg >= gameSurface.cost[i]){

                        gameSurface.hatchingeggs[i].setOnClickListener(v -> {
                            EggClass egg = new EggClass(gameSurface.hatchingeggsdrawables[index], gameSurface.hatchingduration[index], gameSurface.characterNames[index]);
                            MainGameScene.instance.addVariable("Egg", -gameSurface.cost[index]);
                            gameSurface.eggs.add(egg);
                            CreateNewEggs();
                        });

                    }



                }
            }

        });

        CursorActionExecute = false;
    }

   private void CreateNewEggs(){
       FrameLayout frameLayout = (FrameLayout) gameSurface.getParent();
       if (frameLayout != null) {

           int startX = 100; // Initial X position
           int startY = 500; // Initial Y position
           int spacingX = 400; // Horizontal spacing between eggs
           int yPosition = 700; // Vertical spacing between eggs


           while (gameSurface.eggButtons.size() < gameSurface.eggs.size()) {
               Button eggButton = new Button(gameSurface.getContext());
               eggButton.setText("0:00");
               eggButton.setTextColor(Color.WHITE);
               eggButton.setShadowLayer(5, 2, 2, Color.BLACK);
               eggButton.setGravity(Gravity.BOTTOM);

               gameSurface.eggButtons.add(eggButton);
               frameLayout.addView(eggButton);
           }

           // Update existing buttons
           for (int i = 0; i < gameSurface.eggs.size(); i++) {
               Button eggButton = gameSurface.eggButtons.get(i);
               eggButton.setBackground(gameSurface.eggs.get(i).getBackgroundImage());

               // Update position
               FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                       300, // Width
                       300  // Height
               );
               params.leftMargin = startX + i * spacingX;
               params.topMargin = yPosition;
               eggButton.setLayoutParams(params);
               eggButton.setVisibility(View.VISIBLE); // Ensure button is visible
           }

           // Hide unused buttons
           for (int i = gameSurface.eggs.size(); i < gameSurface.eggButtons.size(); i++) {

               gameSurface.eggButtons.get(i).setVisibility(View.GONE);
           }
       }
   }
    public void removeEgg(int index) {
        for (int i =0 ; i < gameSurface.characterNames.length; i++){
            if (gameSurface.eggs.get(index).getName() == gameSurface.characterNames[i]){
                IncrementCharacterText(i);
            }
        }

        Log.e("GameUIEntity", "EGGNAME:" + String.valueOf(gameSurface.eggs.get(index).getName()) + "CHARNAME:" + String.valueOf(gameSurface.characterNames[index]));
        if (index >= 0 && index < gameSurface.eggs.size()) {
            gameSurface.eggs.remove(index);
            CreateNewEggs();

        }
    }

    public void EggUpdate() {
        // Exit early if there are no eggs
        if (gameSurface.eggs.isEmpty()) return;

        // Ensure eggButtons and eggs are synchronized in size
        while (gameSurface.eggButtons.size() < gameSurface.eggs.size()) {
            Button eggButton = new Button(gameSurface.getContext());
            eggButton.setText("0:00");
            eggButton.setTextColor(Color.WHITE);
            eggButton.setShadowLayer(5, 2, 2, Color.BLACK);
            eggButton.setGravity(Gravity.BOTTOM);
            gameSurface.eggButtons.add(eggButton);
            // Ensure button is added to the layout if necessary
            ((FrameLayout) gameSurface.getParent()).addView(eggButton);
        }

        // Update existing buttons with the egg information
        for (int i = 0; i < gameSurface.eggs.size(); i++) {
            EggClass egg = gameSurface.eggs.get(i);
            Button eggButton = gameSurface.eggButtons.get(i);

            // Avoid redundant updates
            String remainingTime = egg.getRemainingTime();
            if (eggButton != null && !eggButton.getText().toString().equals(remainingTime)) {
                eggButton.setText(remainingTime);
            }

            // Check if the egg has hatched
            if (egg.isHatched()) {
                if (eggButton != null && !eggButton.getText().toString().equals("Hatched!")) {
                    eggButton.setText("Hatched!");
                }

                // Add click listener to remove the egg once it's hatched
                int finalI = i;  // Make the index final for use in the listener
                if (eggButton != null) {
                    eggButton.setOnClickListener(v -> removeEgg(finalI));
                }
            }
        }


        for (int i = gameSurface.eggs.size(); i < gameSurface.eggButtons.size(); i++) {
        if (gameSurface.eggButtons.get(i) != null) {
            gameSurface.eggButtons.get(i).setVisibility(View.GONE);
        }
        }
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
