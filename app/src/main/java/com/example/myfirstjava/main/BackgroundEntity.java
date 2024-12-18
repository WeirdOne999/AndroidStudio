package com.example.myfirstjava.main;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.example.myfirstjava.R;
import com.example.myfirstjava.mgp2d.core.GameActivity;
import com.example.myfirstjava.mgp2d.core.GameEntity;
import com.example.myfirstjava.mgp2d.core.GameScene;

public class BackgroundEntity extends GameEntity {
    private float _backgroundPosition;
    private Bitmap _backgroundBitmap0;
    private Bitmap _backgroundBitmap1;
    private final int screenWidth; // Screen width


    int speed = 1;

    public BackgroundEntity(int id,int speed) {
        int screenHeight = GameActivity.instance.getResources().getDisplayMetrics().heightPixels;
        screenWidth = GameActivity.instance.getResources().getDisplayMetrics().widthPixels;
        Bitmap bmp = BitmapFactory.decodeResource(GameActivity.instance.getResources(), id);
        _backgroundBitmap0 = Bitmap.createScaledBitmap(bmp,screenWidth,screenHeight,true);
        _backgroundBitmap1 = Bitmap.createScaledBitmap(bmp,screenWidth,screenHeight,true);

        this.speed = speed;


    }

    @Override
    public void onUpdate(float dt, GameScene gamescene) {
        super.onUpdate(dt, gamescene);

        _backgroundPosition = (_backgroundPosition - dt * 500 * speed) % (float)screenWidth;
    }

    @Override
    public void onRender(Canvas canvas) {
        canvas.drawBitmap(_backgroundBitmap0,_backgroundPosition,0,null);
        canvas.drawBitmap(_backgroundBitmap1,_backgroundPosition + screenWidth,0,null);
    }


}
