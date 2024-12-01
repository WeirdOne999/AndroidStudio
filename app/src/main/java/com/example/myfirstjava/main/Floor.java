package com.example.myfirstjava.main;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.example.myfirstjava.R;
import com.example.myfirstjava.mgp2d.core.GameActivity;
import com.example.myfirstjava.mgp2d.core.GameEntity;
import com.example.myfirstjava.mgp2d.core.GameScene;
import com.example.myfirstjava.mgp2d.core.Vector2;

public class Floor extends GameEntity {
    public Floor(Vector2 pos, int layer){
        Vector2 spritesize = new Vector2(getScreenWidth(),getScreenHeight() / 12);
        setPosition(pos);
        Bitmap bmp = BitmapFactory.decodeResource(GameActivity.instance.getResources(), R.drawable.holder);
        //setSprite(Bitmap.createScaledBitmap(bmp,(int)spritesize.x,(int)spritesize.y,true));
        setLayer(layer);
        setSize(new Vector2((int)spritesize.x,(int)spritesize.y));
    }

    @Override
    public void onUpdate(float dt, GameScene gamescene) {

    }

    @Override
    public void onRender(Canvas canvas) {

    }
}
