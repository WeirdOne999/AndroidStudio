package com.example.myfirstjava.main;

import android.graphics.Canvas;

import com.example.myfirstjava.R;
import com.example.myfirstjava.mgp2d.core.GameScene;
import com.example.myfirstjava.mgp2d.core.Vector2;

public class Skeleton extends EnemyEntity{


    float timer = 0;
    final float attackCooldown = 5.0f;



    public Skeleton(Vector2 pos, int layer){
        super(75,2,0.375f);
        setLayer(layer);
        isEnemy=true;
        setPosition(pos);
        walk.setAnimationHolder(R.drawable.skeletonwalk,4,2,4,5,12);
        attack.setAnimationHolder(R.drawable.skeletonattack,4,5,5,9,12);
        idle.setAnimationHolder(R.drawable.skeletonidle,3,5,8,11,12);
        SetSprite(walk);
        int size = 100;
        setSize(new Vector2(size,size));
    }

    @Override
    public void onUpdate(float dt, GameScene gamescene) {
        super.onUpdate(dt, gamescene);
        //every few seconds skeleton will attack
        //attack will shoot a arrow (projectile)
        //while attacking skeleton is unable to move

        timer += dt;

        if (timer > attackCooldown){
            currentState = State.ATTACK;
            _animatedSprite.setLopping(false);
            if (_animatedSprite.hasFinished()){
                //shoot arrow
                timer = 0;
                gamescene._gameEntityCache.add(new Arrow(this));
                _animatedSprite.setLopping(true);
                currentState = State.WALK;
            }


        }

        if(touchedPlant != null){
            currentspeed = 0;
        }else{
            currentspeed = speed;
        }
    }

    @Override
    public void onRender(Canvas canvas) {
        super.onRender(canvas);
    }
}
