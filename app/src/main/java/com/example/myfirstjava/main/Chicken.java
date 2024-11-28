package com.example.myfirstjava.main;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.example.myfirstjava.R;
import com.example.myfirstjava.mgp2d.core.GameActivity;
import com.example.myfirstjava.mgp2d.core.Vector2;

public class Chicken extends LivingEntity{
    public Chicken(Vector2 position) {
        int size = 100;
        SetHealth(5);
        setPosition(position);
        setLayer(1);
        Bitmap bmp = BitmapFactory.decodeResource(GameActivity.instance.getResources(), R.drawable.minibunny);
        //_sprite = Bitmap.createScaledBitmap(bmp,size,size,true);
        setSize(new Vector2(size,size));
        setAnimatedSprite(Bitmap.createScaledBitmap(bmp,size * 10,size* 10,true),4,4,12);
    }

    @Override
    public void onRender(Canvas canvas) {
        super.onRender(canvas);
    }
}
