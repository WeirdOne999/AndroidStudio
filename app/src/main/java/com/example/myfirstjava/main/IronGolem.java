package com.example.myfirstjava.main;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.example.myfirstjava.R;
import com.example.myfirstjava.mgp2d.core.GameActivity;
import com.example.myfirstjava.mgp2d.core.GameScene;
import com.example.myfirstjava.mgp2d.core.Vector2;

public class IronGolem extends LivingEntity{
    Bitmap bmp;
    float timer;
    float cooldown = 5;
    EnemyEntity touched;
    float damage = 10.f;
    public IronGolem(Vector2 position, int layer) {
        timer = cooldown;
        isEnemy = false;
        setLayer(layer);
        int size = 100;
        SetHealth(50);
        setPosition(position);
        bmp = BitmapFactory.decodeResource(GameActivity.instance.getResources(), R.drawable.minibear);
        //_sprite = Bitmap.createScaledBitmap(bmp,size,size,true);
        setSize(new Vector2(size,size));
        setAnimatedSprite(Bitmap.createScaledBitmap(bmp,(int)(getScreenHeight() *1.5),(int)(getScreenHeight() * 1.5),true),8,10,12,0,3);

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
                            _animatedSprite.setNew(30,33);
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


            _animatedSprite.setNew(0,3);
            _animatedSprite.setLopping(true);
            if(touched != null) gamescene._gameEntityCache.add(new AreaDamage(3,touched.getPosition(),50));
        }

    }

    @Override
    public void onRender(Canvas canvas) {
        super.onRender(canvas);
    }
}
