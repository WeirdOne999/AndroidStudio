package com.example.myfirstjava.main;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.Gravity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Button;
import android.widget.FrameLayout;

import com.example.myfirstjava.R;

//SURFACE VIEW CLASS
public class MainGameSurfaceView extends SurfaceView implements Runnable {
    private Thread gameThread;
    private boolean isRunning;
    private SurfaceHolder surfaceHolder;



    public Button[] characterButtons = new Button[5];

    private int screenWidth, screenHeight;

    private int buttonWidth, buttonHeight;

    public int buttonposX, buttonpoxY;
    public int buttonStartX, buttonStartY;

    public void setSize(int width,int height){
        screenWidth = width;
        screenHeight = height;
    }

    public MainGameSurfaceView(Context context, FrameLayout frameLayout) {
        super(context);

        surfaceHolder = getHolder();
        screenWidth = getResources().getDisplayMetrics().widthPixels;
        screenHeight = getResources().getDisplayMetrics().heightPixels;



        buttonWidth = 50; // Width in dp
        buttonHeight = 50; // Height in dp
        buttonStartX = screenWidth - 2500; // Starting X position
        buttonStartY = screenHeight - 900; // Fixed Y position near the bottom of the screen

        // Create a row of buttons
        for (int i = 0; i < characterButtons.length; i++) {
            characterButtons[i] = new Button(context);
            characterButtons[i].setBackgroundResource(R.drawable.chicken);

            characterButtons[i].setText("0");
            characterButtons[i].setTextColor(Color.WHITE);
            characterButtons[i].setShadowLayer(5, 2, 2, Color.BLACK);
            characterButtons[i].setGravity(Gravity.RIGHT | Gravity.BOTTOM);


            // Calculate position for each button in the row
            int buttonPosY = buttonStartY + (i * (buttonHeight + 100)); // 20 is spacing between buttons

            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    (int) (buttonWidth * getResources().getDisplayMetrics().density),
                    (int) (buttonHeight * getResources().getDisplayMetrics().density)
            );
            params.leftMargin = buttonStartX; // X position
            params.topMargin = buttonPosY; // Fixed Y position

            characterButtons[i].setLayoutParams(params);

            // Add the button to the FrameLayout
            frameLayout.addView(characterButtons[i]);
        }

    }

    public Button[] getCharacterButton() {
        return characterButtons;
    }

    @Override
    public void run() {
        while (isRunning) {
            if (!surfaceHolder.getSurface().isValid()) continue;

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




        // Use post() to update the button's position on the UI thread
        post(() -> {
            for (int i = 0; i < characterButtons.length; i++) {

                    characterButtons[i].setEnabled(true); // Example dynamic update

            }
        });
    }

    private void render(Canvas canvas) {




    }

    public void resume() {
        isRunning = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void pause() {
        isRunning = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
