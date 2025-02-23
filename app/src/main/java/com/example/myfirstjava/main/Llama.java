package com.example.myfirstjava.main;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.example.myfirstjava.R;
import com.example.myfirstjava.mgp2d.core.GameActivity;
import com.example.myfirstjava.mgp2d.core.GameScene;
import com.example.myfirstjava.mgp2d.core.Vector2;

public class Llama extends LivingEntity{

    float timer = 2;
    float recharge = 2;
    boolean hasShot = false;
    Bitmap bmp;
    public Llama(Vector2 position, int layer) {
        isEnemy = false;
        setLayer(layer);
        int size = 100;
        SetHealth(10);
        setPosition(position);
        bmp = BitmapFactory.decodeResource(GameActivity.instance.getResources(), R.drawable.miniwolf);
        //_sprite = Bitmap.createScaledBitmap(bmp,size,size,true);
        setSize(new Vector2(size,size));
        setAnimatedSprite(Bitmap.createScaledBitmap(bmp,(int)(getScreenHeight() *1.5),(int)(getScreenHeight() * 1.5),true),8,7,12,0,3);

    }

    public void ON(Vector2 position, int layer) {
        alive();
        isEnemy = false;
        setLayer(layer);
        int size = 100;
        SetHealth(10);
        setPosition(position);
        bmp = BitmapFactory.decodeResource(GameActivity.instance.getResources(), R.drawable.miniwolf);
        //_sprite = Bitmap.createScaledBitmap(bmp,size,size,true);
        setSize(new Vector2(size,size));
        setAnimatedSprite(Bitmap.createScaledBitmap(bmp,(int)(getScreenHeight() *1.5),(int)(getScreenHeight() * 1.5),true),8,7,12,0,3);
        SetItem(Item.SWORD,Level.WOODEN);
    }

    @Override
    public void onUpdate(float dt, GameScene gamescene) {
        super.onUpdate(dt, gamescene);
        timer -= dt;
        if (timer <= 0 && MainGameScene.instance.enemymobs[getLayer()] > 0){
            if (!hasShot){
                //gamescene._gameEntityCache.add(new LlamaSpit(this,UseItem(gamescene)));
                gamescene._gameEntityCache.add(LlamaSpitPool.acquire(this,UseItem(gamescene)));
                AudioClass.getInstance().PlaySFX(GameActivity.instance, R.raw.llamaspit);
            }
            hasShot = true;

            _animatedSprite.setNew(28,32);
            _animatedSprite.setLopping(false);
            timer = recharge;
        }
        if (_animatedSprite.hasFinished()){
            hasShot = false;
            _animatedSprite.setNew(0,3);
            _animatedSprite.setLopping(true);
        }
    }

    @Override
    public void onRender(Canvas canvas) {
        super.onRender(canvas);
    }
}
