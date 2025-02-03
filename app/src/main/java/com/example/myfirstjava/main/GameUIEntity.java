package com.example.myfirstjava.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.myfirstjava.R;
import com.example.myfirstjava.mgp2d.core.GameActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


//INTERFACE CLASS FOR MAINMENU FROM GAME SURFACE
public class GameUIEntity {
    private MainGameSurfaceView gameSurface;
    private int test = 0;

    public int ButtonPressed = 0;
    public boolean GoToHouse = false;
    public boolean GoToCraft = false;
    public boolean CursorActionExecute = false;

    private Vibrator _vibrator;
    private boolean isResuming = true;

    public GameUIEntity(Context context, FrameLayout container) {
        gameSurface = new MainGameSurfaceView(context, container);
        container.addView(gameSurface); // Add the GameSurface to the provided container
        gameSurface.post(() -> setupButtonClicks());
        gameSurface.createhatchingbuttons();
        gameSurface.creategamebuttons();
        gameSurface.GameLayout();
        _vibrator = (Vibrator) GameActivity.instance.getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
    }


    public void setSize(int width, int height) {
        gameSurface.setSize(width, height);
    }

    public void Update() {
        if (gameSurface != null) {
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

            gameSurface.post(() -> {
                for (int i = 0; i < gameSurface.characterButtons.length; i++) {
                    gameSurface.characterButtons[i].setText(String.valueOf(gameSurface.characteramounts[i]));
                }
            });
        }

        if (gameSurface.MaterialAmount != null && MainGameScene.instance != null && gameSurface.eggCountText != null) {

            if (gameSurface.MaterialAmount.size() == MainGameScene.instance.material.size()) {
                int index = 0;


                for (Map.Entry<String, Integer> entry : MainGameScene.instance.material.entrySet()) {
                    String materialName = entry.getKey();
                    int materialAmount = entry.getValue();


                    int finalIndex = index;
                    (GameActivity.instance).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            TextView materialTextView = gameSurface.MaterialAmount.get(finalIndex);
                            if (materialAmount > 99) {
                                materialTextView.setText("99+");
                            } else {
                                materialTextView.setText(String.valueOf(materialAmount));
                            }

                        }
                    });


                    index++;
                }
            } else {
                Log.e("Game", "Mismatch between number of materials and TextViews!");
            }
            (GameActivity.instance).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    // Ensure we have a valid egg count text reference and update it
                    gameSurface.eggCountText.setText(String.valueOf(MainGameScene.instance.Egg));
                }
            });
        }

        Log.d("MainGameSurfaceView", "MaterialGrid: " + gameSurface.MaterialScrollview);


        EggUpdate();
        Log.e("GameUIEntity", "EGGS SIZE:" + gameSurface.eggs.size());
        Log.e("GameUIEntity", "EGGSBUTTON SIZE:" + gameSurface.eggButtons.size());

        for (int i = 0; i < gameSurface.characteramounts.length; i++) {
            if (gameSurface.characteramounts[i] <= 0) {
                gameSurface.characteramounts[i] = 0;
            }
        }

        for (int i = 0; i < gameSurface.characterButtons.length; i++) {
            Log.e("GameUIEntity" + i, "CharacterAmount:" + i + " " + gameSurface.characteramounts[i]);
        }

        Log.e("GameUIEntity", String.valueOf(GameActivity.instance.deltaTime));


        //ITEMS

            if (gameSurface.itembuttons != null && gameSurface.itembuttons.size() > 0 ) {
                // Modify text for each button dynamically in the update loop
                gameSurface.post(() -> {
                    for (int i = 0; i < gameSurface.myItems.size(); i++) {
                        //gameSurface.itembuttons.get(i).setText(String.valueOf(gameSurface.myItems.get(i).getAmount()));
                        gameSurface.itembuttons.get(i).setText(String.valueOf(gameSurface.myItems.get(i).getAmount()));

                    }

                });
            }

            for (int i =0; i < gameSurface.myItems.size();i++){
                if (gameSurface.myItems.get(i).getAmount() <= 0){
                    gameSurface.myItems.get(i).setAmount(0);
                }
            }

            for (int i = 0; i < gameSurface.myItems.size(); i++) {
                Log.e("GameUIEntity" + i, "ItemsAmount:" + i + " " + gameSurface.myItems.get(i).getAmount());
            }



    }

    @SuppressLint("ClickableViewAccessibility")
    public void setupButtonClicks() {
        gameSurface.post(() -> {
            ButtonPressed = -1;
            Log.d("MYFAULT","falseExec");
            for (int i = 0; i < gameSurface.characterButtons.length; i++) {
                final int index = i;  // Use final to access inside the listener
                gameSurface.characterButtons[i].setOnTouchListener((v, event) -> {
                    //character dragging
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        MainGameScene.instance.Planting = true;
                        ButtonPressed = index;
                        Log.d("MYFAULT","ALW TRUE 2");
                        CursorActionExecute = true;
                    }
                    return false;
                });

                gameSurface.characterButtons[i].setOnClickListener(v -> {
                    AudioClass.getInstance().PlaySFX(GameActivity.instance, R.raw.ui);
                    for (Button buttons : gameSurface.hatchingeggs) {
                        //buttons.setVisibility(View.GONE);
                    }
                });


            }

            gameSurface.toggleinvenbutton.setOnClickListener(v -> {
                AudioClass.getInstance().PlaySFX(GameActivity.instance, R.raw.ui);
                //TOGGLE BETWEEN MOBS AND ITEMS
                if (gameSurface.mobinvenactive){
                    gameSurface.itemsscrollview.setVisibility(View.VISIBLE);
                    for (Button buttons : gameSurface.characterButtons){
                        buttons.setVisibility(View.INVISIBLE);

                    }
                    gameSurface.mobinvenactive = false;
                }else {
                    gameSurface.itemsscrollview.setVisibility(View.INVISIBLE);
                    for (Button buttons : gameSurface.characterButtons){
                        buttons.setVisibility(View.VISIBLE);

                    }
                    gameSurface.mobinvenactive = true;
                }

            });
            Log.d("DEBUG", "UIitemButtons: " + gameSurface.UIitemButtons);
            for (int i =0; i < gameSurface.UIitemButtons.size(); i++){
                final int finalI = i;


                gameSurface.UIitemButtons.get(i).setOnClickListener(v -> {



                    AudioClass.getInstance().PlaySFX(GameActivity.instance, R.raw.ui);
                    gameSurface.craftedItemImage.setBackgroundResource(gameSurface.myItems.get(finalI).getImageResource());
                    gameSurface.craftedItemText.setText(gameSurface.myItems.get(finalI).getName());
                });
            }

            gameSurface.confirmcraftbutton.setOnClickListener(v -> {


                for (int i = 0; i < gameSurface.myItems.size(); i++) {

                    if (gameSurface.craftedItemText.getText() == gameSurface.myItems.get(i).getName()){
                        Boolean canProduceItem = true;

                        ArrayList<String> keysList = new ArrayList<>(gameSurface.myItems.get(i).materials.keySet());

                        for (int k = 0; k < gameSurface.myItems.get(i).materials.size(); k++ ){
                            //TO REMOVE FROM INVENTORY
                            String temp = keysList.get(k);

                            if (MainGameScene.instance.material.containsKey(temp)){
                                if (MainGameScene.instance.material.get(temp) <= gameSurface.myItems.get(i).materials.get(temp)){

                                    canProduceItem = false;
                                    AudioClass.getInstance().PlaySFX(GameActivity.instance, R.raw.ui);
                                    break;
                                }

                            }else{
                                canProduceItem = false;
                                AudioClass.getInstance().PlaySFX(GameActivity.instance, R.raw.ui);
                                break;
                            }
                        }

                        if(canProduceItem){
                            //MainGameScene.instance.material.put(temp,MainGameScene.instance.material.get(temp) - gameSurface.myItems.get(i).materials.get(temp));
                            //AudioClass.getInstance().PlaySFX(GameActivity.instance, R.raw.completed);

                            for (int k = 0; k < gameSurface.myItems.get(i).materials.size(); k++ ){
                                //TO REMOVE FROM INVENTORY
                                String temp = keysList.get(k);
                                if (MainGameScene.instance.material.containsKey(temp)){
                                    if (MainGameScene.instance.material.get(temp) >= gameSurface.myItems.get(i).materials.get(temp)){
                                        MainGameScene.instance.material.put(temp,MainGameScene.instance.material.get(temp) - gameSurface.myItems.get(i).materials.get(temp));
                                    }

                                }
                            }

                            gameSurface.myItems.get(i).setAmount(gameSurface.myItems.get(i).getAmount() + 1);
                            AudioClass.getInstance().PlaySFX(GameActivity.instance, R.raw.completed);
                        }

                    }
                }




            });

            for (int i =0; i < gameSurface.itembuttons.size(); i++){
                int finalI = i;
                gameSurface.itembuttons.get(i).setOnClickListener(v -> {
                    //set dragging here for items
                    gameSurface.myItems.get(finalI).setAmount(gameSurface.myItems.get(finalI).getAmount() - 1);
                });

                gameSurface.itembuttons.get(i).setOnTouchListener((v, event) -> {
                    //item dragging
                    //TODO CHANGE TO SET
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        MainGameScene.instance.Planting = true;
                        ButtonPressed = finalI + gameSurface.characterButtons.length;
                        Log.d("MYFAULT","ALW TRUE 3");
                        CursorActionExecute = true;
                    }
                    return false;
                });

            }

            gameSurface.toHouseButton.setOnClickListener(v -> {
                AudioClass.getInstance().PlaySFX(GameActivity.instance, R.raw.ui);
                GoToHouse = true;
                GoToCraft = false;
                gameSurface.HatchingLayout();
            });

            gameSurface.toFarmButton.setOnClickListener(v -> {
                AudioClass.getInstance().PlaySFX(GameActivity.instance, R.raw.ui);
                gameSurface.GameLayout();
                GoToHouse = false;
                GoToCraft = false;

            });

            gameSurface.toCraftButton.setOnClickListener(v -> {
                AudioClass.getInstance().PlaySFX(GameActivity.instance, R.raw.ui);
                GoToHouse = false;
                GoToCraft = true;
                gameSurface.CraftingLayout();
            });

            if (gameSurface.hatchingeggs != null) {
                for (int i = 0; i < gameSurface.hatchingeggs.length; i++) {
                    final int index = i;  // Use final to access inside the listener
                    if (MainGameScene.instance.Egg >= gameSurface.cost[i]) {
                        gameSurface.hatchingeggs[i].setOnClickListener(v -> {
                            AudioClass.getInstance().PlaySFX(GameActivity.instance, R.raw.ui);
                            EggClass egg = new EggClass(gameSurface.hatchingeggsdrawables[index], gameSurface.hatchingduration[index], gameSurface.characterNames[index]);
                            egg.reset();
                            MainGameScene.instance.addVariable("Egg", -gameSurface.cost[index]);
                            synchronized (gameSurface.eggs) {
                                gameSurface.eggs.add(egg);
                            }
                            CreateNewEggs();
                        });
                    }
                }
            }
        });

    }

    private void CreateNewEggs() {
        FrameLayout frameLayout = (FrameLayout) gameSurface.getParent();
        if (frameLayout != null) {
            int startX = 100;
            int spacingX = 400;
            int yPosition = 700;

            // Remove any extra buttons safely
            synchronized (gameSurface.eggs) {
                while (gameSurface.eggButtons.size() > gameSurface.eggs.size()) {
                    Button extraButton = gameSurface.eggButtons.remove(gameSurface.eggButtons.size() - 1);
                    frameLayout.removeView(extraButton);
                }

                // Add missing buttons
                while (gameSurface.eggButtons.size() < gameSurface.eggs.size()) {
                    Button eggButton = new Button(gameSurface.getContext());
                    eggButton.setText("0:00");
                    eggButton.setTextColor(Color.WHITE);
                    eggButton.setShadowLayer(5, 2, 2, Color.BLACK);
                    eggButton.setGravity(Gravity.BOTTOM);
                    gameSurface.eggButtons.add(eggButton);
                    frameLayout.addView(eggButton);
                }


                for (int i = 0; i < gameSurface.eggs.size(); i++) {
                    Button eggButton = gameSurface.eggButtons.get(i);
                    eggButton.setBackground(gameSurface.eggs.get(i).getBackgroundImage());
                    FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                            300, // Width
                            300  // Height
                    );
                    params.leftMargin = startX + i * spacingX;
                    params.topMargin = yPosition;
                    eggButton.setLayoutParams(params);
                    eggButton.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    public void removeEgg(int index) {
        if (index < 0 || index >= gameSurface.eggs.size()) {
            Log.e("GameUIEntity", "Invalid index for removal: " + index);
            return;
        }


        synchronized (gameSurface.eggs) {
            EggClass eggToRemove = gameSurface.eggs.get(index);
            for (int i = 0; i < gameSurface.characterNames.length; i++) {
                if (eggToRemove.getName().equals(gameSurface.characterNames[i])) {
                    IncrementCharacterText(i);
                }
            }
            gameSurface.eggs.remove(index);


            Button buttonToRemove = gameSurface.eggButtons.get(index);
            if (buttonToRemove != null) {
                FrameLayout frameLayout = (FrameLayout) gameSurface.getParent();
                if (frameLayout != null) {
                    frameLayout.removeView(buttonToRemove);
                }
            }
            gameSurface.eggButtons.remove(index);


            CreateNewEggs();
        }
    }

    @SuppressLint("NewApi")
    public void EggUpdate() {
        synchronized (gameSurface.eggs) {
            for (int i = 0; i < gameSurface.eggs.size(); i++) {
                EggClass egg = gameSurface.eggs.get(i);
                Button eggButton = gameSurface.eggButtons.get(i);

                if (eggButton != null) {
                    eggButton.setText(egg.isHatched() ? "Hatched!" : egg.getRemainingTime());
                    eggButton.setOnClickListener(null);

                    if (egg.isHatched()) {
                        MainGameSurfaceView.instance.toHouseButton.setTextColor(Color.GREEN);
                        int finalIndex = i;
                        eggButton.setOnClickListener(v -> {
                            removeEgg(finalIndex);
                            MainGameSurfaceView.instance.toHouseButton.setTextColor(Color.WHITE);
                        });


                        if (!egg.hasPlayedSFX()) {
                            AudioClass.getInstance().PlaySFX(GameActivity.instance, R.raw.completed);
                            egg.setPlayedSFX(true);
                            _vibrator.vibrate(VibrationEffect.createOneShot(500,100));
                        }

                    }
                }
            }
        }
    }


    public void IncrementCharacterText(int index) {
        gameSurface.characteramounts[index]++;
    }

    public void DecrementCharacterText(int index) {
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

