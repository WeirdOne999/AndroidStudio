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

    public Holder(Vector2 pos, int size, int layer){
        setPosition(pos);
        Bitmap bmp = BitmapFactory.decodeResource(GameActivity.instance.getResources(), R.drawable.holder);
        //setSprite(Bitmap.createScaledBitmap(bmp,size,size,true));
        //afterSetSprite();
        setLayer(layer);
        setSize(new Vector2(size,size));
    }

    @Override
    public void onUpdate(float dt, GameScene gameScene) {
    }

    @Override
    public void onRender(Canvas canvas) {
    }

    /*
    @Override
    public boolean isColliding(CollisionEntity collider) {
        if(super.isColliding(collider)){
            destroy();
        }
         return super.isColliding(collider);
    }

     */
}
