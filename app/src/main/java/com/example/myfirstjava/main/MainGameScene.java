package com.example.myfirstjava.main;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;

import com.example.myfirstjava.R;
import com.example.myfirstjava.mgp2d.core.GameActivity;
import com.example.myfirstjava.mgp2d.core.GameScene;

public class MainGameScene extends GameScene {

    private Bitmap _backgroundBitmap0;
    private Bitmap _backgroundBitmap1;
    private float _backgroundPosition;
    int screenWidth;

    @Override
    public void onCreate() {
        super.onCreate();
        int screenHeight = GameActivity.instance.getResources().getDisplayMetrics().heightPixels;
        screenWidth = GameActivity.instance.getResources().getDisplayMetrics().widthPixels;
        Bitmap bmp = BitmapFactory.decodeResource(GameActivity.instance.getResources(), R.drawable.gamescene);
        _backgroundBitmap0 = Bitmap.createScaledBitmap(bmp,screenWidth,screenHeight,true);
        _backgroundBitmap1 = Bitmap.createScaledBitmap(bmp,screenWidth,screenHeight,true);
    }

    @Override
    public void onUpdate(float dt) {
        _backgroundPosition = (_backgroundPosition - dt * 500) % (float)screenWidth;
    }

    @Override
    public void onRender(Canvas canvas) {
        canvas.drawColor(Color.parseColor("#b2d4ff"));
        canvas.drawBitmap(_backgroundBitmap0,_backgroundPosition,0,null);
        canvas.drawBitmap(_backgroundBitmap0,_backgroundPosition + screenWidth,0,null);
    }
}
