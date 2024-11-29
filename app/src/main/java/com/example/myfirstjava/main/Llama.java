package com.example.myfirstjava.main;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.example.myfirstjava.R;
import com.example.myfirstjava.mgp2d.core.GameActivity;
import com.example.myfirstjava.mgp2d.core.GameScene;
import com.example.myfirstjava.mgp2d.core.Vector2;

public class Llama extends LivingEntity{

    public Llama(Vector2 position, int layer) {
        setLayer(layer);
        int size = 100;
        SetHealth(10);
        setPosition(position);
        Bitmap bmp = BitmapFactory.decodeResource(GameActivity.instance.getResources(), R.drawable.wolfidle);
        //_sprite = Bitmap.createScaledBitmap(bmp,size,size,true);
        setSize(new Vector2(size,size));
        setAnimatedSprite(Bitmap.createScaledBitmap(bmp,(int)getScreenHeight() / 1,(int)(getScreenHeight() / 5.4f),true),1,5,12);
    }

    @Override
    public void onUpdate(float dt, GameScene gamescene) {
        super.onUpdate(dt, gamescene);
    }

    @Override
    public void onRender(Canvas canvas) {
        super.onRender(canvas);
    }
}
