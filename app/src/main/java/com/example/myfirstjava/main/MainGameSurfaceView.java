package com.example.myfirstjava.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Gravity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myfirstjava.R;

import java.util.Arrays;

//SURFACE VIEW CLASS
public class MainGameSurfaceView extends SurfaceView implements Runnable {
    private Thread gameThread;
    private boolean isRunning;
    private SurfaceHolder surfaceHolder;

    private Bitmap backgroundBitmap;
    private int backgroundWidth, backgroundHeight;

    public Button[] characterButtons = new Button[4];
    public Drawable[] characterdrawables = new Drawable[4];

    public int[] characteramounts = new int[4];
    public int characterbuttonindex =0;
    public Button[] hatchingeggs = new Button[7];

    public Button[] eggshop = new Button[5];
    public Button toHouseButton;
    private int screenWidth, screenHeight;

    private int buttonWidth, buttonHeight;

    public int buttonposX, buttonpoxY;
    public int buttonStartX, buttonStartY;

    public int hatcheggwidth, hatcheggheight;
    public int hatchbuttonStartX, hatchbuttonStartY;



    public void setSize(int width,int height){
        screenWidth = width;
        screenHeight = height;
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public MainGameSurfaceView(Context context, FrameLayout frameLayout) {
        super(context);
        setZOrderOnTop(true);
        ImageView backgroundView = new ImageView(context);
        backgroundView.setImageResource(R.drawable.blank_bg); // Replace 'blank_bg' with your image
        FrameLayout.LayoutParams backgroundParams = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
        );
        backgroundView.setLayoutParams(backgroundParams);
        frameLayout.addView(backgroundView); // Add the background first

        surfaceHolder = getHolder();
        setZOrderOnTop(false); // Keep SurfaceView below other views
        screenWidth = getResources().getDisplayMetrics().widthPixels;
        screenHeight = getResources().getDisplayMetrics().heightPixels;
        buttonWidth = 50; // Width in dp
        buttonHeight = 50; // Height in dp
        buttonStartX = screenWidth - 2500; // Starting X position
        buttonStartY = screenHeight - 900; // Fixed Y position near the bottom of the screen

        characterdrawables[0] = context.getResources().getDrawable(R.drawable.chicken, null);
        characterdrawables[1] = context.getResources().getDrawable(R.drawable.llama, null);
        characterdrawables[2] = context.getResources().getDrawable(R.drawable.irongolem, null);
        characterdrawables[3] = context.getResources().getDrawable(R.drawable.sheep, null);


        Arrays.fill(characteramounts, 10);



        toHouseButton = new Button(context);
        toHouseButton.setText("HOUSE");

        FrameLayout.LayoutParams tohouseparams = new FrameLayout.LayoutParams(
                (int) (150 * getResources().getDisplayMetrics().density),
                (int) (50 * getResources().getDisplayMetrics().density)
        );
        tohouseparams.leftMargin = screenWidth - 500; // X position
        tohouseparams.topMargin = screenHeight - 200; // Fixed Y position

        toHouseButton.setLayoutParams(tohouseparams);
        frameLayout.addView(toHouseButton);


        // Create a row of buttons
        for (int i = 0; i < characterButtons.length; i++) {
            characterButtons[i] = new Button(context);

            characterButtons[i].setBackground(characterdrawables[i]);
            characterButtons[i].setText("0");
            characterButtons[i].setTextColor(Color.WHITE);
            characterButtons[i].setShadowLayer(5, 2, 2, Color.BLACK);
            characterButtons[i].setGravity(Gravity.RIGHT | Gravity.BOTTOM);
            // Calculate position for each button in the row
            int buttonPosY = buttonStartY + (i * (buttonHeight + 100)); // 20 is spacing between buttons

            FrameLayout.LayoutParams characterbuttonparams = new FrameLayout.LayoutParams(
                    (int) (buttonWidth * getResources().getDisplayMetrics().density),
                    (int) (buttonHeight * getResources().getDisplayMetrics().density)
            );
            characterbuttonparams.leftMargin = buttonStartX; // X position
            characterbuttonparams.topMargin = buttonPosY; // Fixed Y position

            characterButtons[i].setLayoutParams(characterbuttonparams);

            // Add the button to the FrameLayout
            frameLayout.addView(characterButtons[i]);
        }








//        hatcheggwidth = 100; // Width in dp
//        hatcheggheight = 100; // Height in dp
//        hatchbuttonStartX = screenWidth - 2300; // Starting X position
//        hatchbuttonStartY = screenHeight - 900; // Fixed Y position near the bottom of the screen
//
//        // Create a row of buttons
//        for (int i = 0; i < hatchingeggs.length; i++) {
//            hatchingeggs[i] = new Button(context);
//            hatchingeggs[i].setBackgroundResource(R.drawable.egg);
//
//
//
//            // Calculate position for each button in the row
//            int buttonPosX = hatchbuttonStartX + (i * (hatcheggwidth + 200)); // 20 is spacing between buttons
//
//            FrameLayout.LayoutParams hatchbuttonparams = new FrameLayout.LayoutParams(
//                    (int) (hatcheggwidth * getResources().getDisplayMetrics().density),
//                    (int) (hatcheggheight * getResources().getDisplayMetrics().density)
//            );
//            hatchbuttonparams.leftMargin = buttonPosX; // X position
//            hatchbuttonparams.topMargin = hatchbuttonStartY; // Fixed Y position
//
//            hatchingeggs[i].setLayoutParams(hatchbuttonparams);
//
//            // Add the button to the FrameLayout
//            frameLayout.addView(hatchingeggs[i]);
//        }


    }


    public Button[] getCharacterButton() {
        return characterButtons;
    }

    @Override
    public void run() {
        while (isRunning) {

            if (!surfaceHolder.getSurface().isValid()) {
                try {
                    Thread.sleep(10); // Avoid busy-waiting if the surface isn't ready
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                continue;
            }
            float dt = 1.0f / 60.0f; // Assuming 60 FPS for simplicity
            update(dt);
            Canvas canvas = surfaceHolder.lockCanvas();
            if (canvas != null) {
                render(canvas);
                surfaceHolder.unlockCanvasAndPost(canvas);
            }


        }
    }


    private void update(float dt) {

        if (characterButtons != null && characterButtons.length > 0) {
            // Modify text for each button dynamically in the update loop
            this.post(() -> {
                for (int i = 0; i < characterButtons.length; i++) {
                    characterButtons[i].setText("10");
                    Log.d("Update", "Button " + (i + 1) + " text updated");
                }
            });
        }


        // Use post() to update the button's position on the UI thread



    }



    private void render(Canvas canvas) {
        canvas.drawColor(Color.BLACK); // Optional: Clear with black color before drawing

        // Draw the background image
        if (backgroundBitmap != null) {
            canvas.drawBitmap(backgroundBitmap, 0, 0, null);
        }


    }

    public void resume() {
        if (isRunning) {
            // If already running, don't start another thread
            return;
        }

        isRunning = true;

        // Start a new thread safely
        synchronized (this) {
            if (gameThread == null || !gameThread.isAlive()) {
                gameThread = new Thread(this);
                gameThread.start();
            }
        }
    }

    public void pause() {
        isRunning = false;

        // Safely stop the thread
        synchronized (this) {
            if (gameThread != null) {
                try {
                    gameThread.join(); // Wait for the thread to terminate
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    gameThread = null; // Ensure the thread reference is cleared
                }
            }
        }
    }

}
