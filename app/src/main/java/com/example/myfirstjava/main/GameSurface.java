package com.example.myfirstjava.main;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameSurface extends SurfaceView implements Runnable {
    private Thread gameThread;
    private boolean isRunning;
    private SurfaceHolder surfaceHolder;

    private float backgroundPosition;
    private Bitmap backgroundBitmap0;
    private Bitmap backgroundBitmap1;
    private int screenWidth, screenHeight;

    public GameSurface(Context context, int backgroundId) {
        super(context);

        surfaceHolder = getHolder();
        screenWidth = getResources().getDisplayMetrics().widthPixels;
        screenHeight = getResources().getDisplayMetrics().heightPixels;

        Bitmap bmp = BitmapFactory.decodeResource(getResources(), backgroundId);
        backgroundBitmap0 = Bitmap.createScaledBitmap(bmp, screenWidth * 2, screenHeight, true);
        backgroundBitmap1 = Bitmap.createScaledBitmap(bmp, screenWidth * 2, screenHeight, true);
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
        backgroundPosition = (backgroundPosition - dt * 100) % (float) screenWidth;
    }

    private void render(Canvas canvas) {
        canvas.drawBitmap(backgroundBitmap0, backgroundPosition, 0, null);
        canvas.drawBitmap(backgroundBitmap1, backgroundPosition + screenWidth, 0, null);
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
