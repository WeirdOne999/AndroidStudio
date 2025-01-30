package com.example.myfirstjava.main;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.example.myfirstjava.R;
import com.example.myfirstjava.mgp2d.core.GameActivity;
import com.example.myfirstjava.mgp2d.core.GameScene;
import com.example.myfirstjava.mgp2d.core.Vector2;

public class Sheep extends LivingEntity{

    Bitmap bmp;
    EnemyEntity touched;
    float damage = 10.f;

    float timer;
    float cooldown = 5;
    public Sheep(Vector2 position, int layer) {
        timer = cooldown;
        isEnemy = false;
        setLayer(layer);
        int size = 100;
        SetHealth(50);
        setPosition(position);
        bmp = BitmapFactory.decodeResource(GameActivity.instance.getResources(), R.drawable.miniboar);
        //_sprite = Bitmap.createScaledBitmap(bmp,size,size,true);
        setSize(new Vector2(size,size));
        setAnimatedSprite(Bitmap.createScaledBitmap(bmp,(int)(getScreenHeight() *1.5),(int)(getScreenHeight() * 1.5),true),6,5,12,0,3);
        SetItem(Item.SWORD,Level.DIAMOND);
    }

    @Override
    public void onUpdate(float dt, GameScene gamescene) {
        super.onUpdate(dt, gamescene);
        timer += dt;
        touched = null;

        for (int i = 0; i < gamescene._gameEntities.size();i++){
            if (gamescene._gameEntities.get(i) != this){

                if (gamescene._gameEntities.get(i) instanceof EnemyEntity)  {
                    if (this.isColliding(gamescene._gameEntities.get(i))){

                        touched = (EnemyEntity)gamescene._gameEntities.get(i);


                        if(timer > cooldown){
                            _animatedSprite.setNew(15,20);
                        }
                    }
                }
            }
        }

        if (touched != null && timer > cooldown){
            timer = 0;
            _animatedSprite.setLopping(false);
            //TODO: CHANGE THE RANGE CUZ ITS ABIT OUT


        }

        if(_animatedSprite.hasFinished()){
            _animatedSprite.setLopping(true);
            _animatedSprite.setNew(0,3);
            if(touched != null) touched.SetHealth(touched.getHealth() - (damage * UseItem(gamescene)));
        }

    }

    @Override
    public void onRender(Canvas canvas) {
        super.onRender(canvas);
    }
}
