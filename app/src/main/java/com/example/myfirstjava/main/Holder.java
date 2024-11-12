package com.example.myfirstjava.main;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.example.myfirstjava.R;
import com.example.myfirstjava.mgp2d.core.GameActivity;
import com.example.myfirstjava.mgp2d.core.GameEntity;
import com.example.myfirstjava.mgp2d.core.GameScene;
import com.example.myfirstjava.mgp2d.core.Vector2;
import com.google.android.material.transition.Hold;

public class Holder extends GameEntity {

    GameEntity _mob;
    Bitmap image;

    public Holder(Vector2 pos, int size){
        setPosition(pos);
        Bitmap bmp = BitmapFactory.decodeResource(GameActivity.instance.getResources(), R.drawable.holder);
        image = Bitmap.createScaledBitmap(bmp,size,size,true);
    }

    @Override
    public void onUpdate(float dt, GameScene gameScene) {
    }

    @Override
    public void onRender(Canvas canvas) {

        canvas.drawBitmap(image,getPosition().x,getPosition().y,null);
    }
}
