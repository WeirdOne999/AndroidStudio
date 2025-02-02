package com.example.myfirstjava.main;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;

import com.example.myfirstjava.R;
import com.example.myfirstjava.mgp2d.core.GameActivity;
import com.example.myfirstjava.mgp2d.core.GameScene;
import com.example.myfirstjava.mgp2d.core.Vector2;

public class Skeleton extends EnemyEntity{


    float timer = 0;
    final float attackCooldown = 5.0f;

    Context context;

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
        context = GameActivity.instance;
    }

    public void ON(Vector2 pos, int layer){
        super.ON(75,2,0.375f);
        setLayer(layer);
        isEnemy=true;
        setPosition(pos);
        walk.setAnimationHolder(R.drawable.skeletonwalk,4,2,4,5,12);
        attack.setAnimationHolder(R.drawable.skeletonattack,4,5,5,9,12);
        idle.setAnimationHolder(R.drawable.skeletonidle,3,5,8,11,12);
        SetSprite(walk);
        int size = 100;
        alive();
        setSize(new Vector2(size,size));
        context = GameActivity.instance;
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
                Log.d("POOLARROW", "" + this);
                gamescene._gameEntityCache.add(ArrowPool.acquire(this));
                _animatedSprite.setLopping(true);
                currentState = State.WALK;
                AudioClass.getInstance().PlaySFX(context, R.raw.bow);
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
