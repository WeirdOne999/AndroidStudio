package com.example.myfirstjava.main;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;

import com.example.myfirstjava.R;
import com.example.myfirstjava.mgp2d.core.GameActivity;
import com.example.myfirstjava.mgp2d.core.GameScene;
import com.example.myfirstjava.mgp2d.core.Vector2;

public class Chicken extends LivingEntity{

    float timer = 10;
    float recharge = 10;
    public Chicken(Vector2 position, int layer) {
        isEnemy = false;
        setLayer(layer);
        int size = 100;
        SetHealth(5);
        setPosition(position);
        Bitmap bmp = BitmapFactory.decodeResource(GameActivity.instance.getResources(), R.drawable.minibunny);
        //_sprite = Bitmap.createScaledBitmap(bmp,size,size,true);
        setSize(new Vector2(size,size));
        setAnimatedSprite(Bitmap.createScaledBitmap(bmp,(int)getScreenHeight() / 1,(int)getScreenHeight() / 1,true),4,4,12,0,3);
        SetItem(Item.AXE,Level.WOODEN);
    }

    public void ON(Vector2 position, int layer) {
        alive();
        isEnemy = false;
        setLayer(layer);
        int size = 100;
        SetHealth(5);
        setPosition(position);
        Bitmap bmp = BitmapFactory.decodeResource(GameActivity.instance.getResources(), R.drawable.minibunny);
        //_sprite = Bitmap.createScaledBitmap(bmp,size,size,true);
        setSize(new Vector2(size,size));
        setAnimatedSprite(Bitmap.createScaledBitmap(bmp,(int)getScreenHeight() / 1,(int)getScreenHeight() / 1,true),4,4,12,0,3);
        SetItem(Item.AXE,Level.WOODEN);
    }

    @Override
    public void onUpdate(float dt, GameScene gamescene) {
        super.onUpdate(dt, gamescene);
        //Log.d("CHIKCENTEST","CHICKEN");
        timer -= dt + Math.abs(MainGameScene.instance.currentAccel);
        if (timer <= 0){
            //Log.d("ARRSIZ", "Array size: " + gamescene._gameEntities.size());
            //gamescene._gameEntityCache.add(new Egg(this.getLayer(),this.getPosition()));
            gamescene._gameEntityCache.add(EggPool.acquire(this.getPosition(),this.getLayer()));
            AudioClass.getInstance().PlaySFX(GameActivity.instance, R.raw.eggpop);
            UseItem(gamescene);
            timer = recharge;
        }
    }

    @Override
    public void onRender(Canvas canvas) {
        super.onRender(canvas);
    }
}
