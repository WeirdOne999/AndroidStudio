package com.example.myfirstjava.main;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.view.Gravity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.core.content.ContextCompat;

import com.example.myfirstjava.R;
import com.example.myfirstjava.mgp2d.core.GameActivity;

//SURFACE VIEW CLASS
public class GameSurface extends SurfaceView implements Runnable {
    private Thread gameThread;
    private boolean isRunning;
    private SurfaceHolder surfaceHolder;

    private float backgroundPosition;
    private Bitmap backgroundBitmap0;
    private Bitmap backgroundBitmap1;



    private int screenWidth, screenHeight;


    private boolean draw = false;
    public void setSize(int width,int height){
        screenWidth = width;
        screenHeight = height;
    }

    public GameSurface(Context context, FrameLayout frameLayout, int backgroundId) {
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
        backgroundPosition = (backgroundPosition - dt * 200) % (float) screenWidth;




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
