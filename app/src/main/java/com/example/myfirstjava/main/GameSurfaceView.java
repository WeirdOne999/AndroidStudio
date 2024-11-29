package com.example.myfirstjava.main;

import android.content.Context;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameSurfaceView extends SurfaceView implements SurfaceHolder.Callback {

    private MainGameScene mainGameScene;

    public GameSurfaceView(Context context) {
        super(context);
        getHolder().addCallback(this);
        mainGameScene = new MainGameScene(); // Initialize your MainGameScene here
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // Start your game rendering here
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        // Handle any surface changes, like screen rotation
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // Handle cleanup when surface is destroyed
    }

    // You can call onRender or custom rendering logic in this method
    public void render(Canvas canvas) {
        mainGameScene.onRender(canvas); // Call MainGameScene's rendering logic here
    }
}
