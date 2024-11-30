package com.example.myfirstjava.main;

import android.graphics.Canvas;
import android.util.Log;

import com.example.myfirstjava.R;
import com.example.myfirstjava.mgp2d.core.GameEntity;
import com.example.myfirstjava.mgp2d.core.GameScene;
import com.example.myfirstjava.mgp2d.core.Vector2;

public class Zombie extends EnemyEntity{
    public Zombie(Vector2 pos,int layer){
        super(100,5,0.375f);
        setLayer(layer);
        setPosition(pos);
        walk.setAnimationHolder(R.drawable.zombiewalk,4,4,4,7,12);
        attack.setAnimationHolder(R.drawable.zombieattack,4,5,5,9,12);
        idle.setAnimationHolder(R.drawable.zombieidlenew,1,19,0,18,12);
        SetSprite(walk);
        int size = 100;
        setSize(new Vector2(size,size));

    }

    @Override
    public void attack(float dt, GameScene gameScene, LivingEntity ge) {
        super.attack(dt, gameScene, ge);
            _animatedSprite.setLopping(false);
            if (_animatedSprite.hasFinished()){
                ge.SetHealth(ge.getHealth() - 1);
                currentState = State.WALK;
            }

        currentState = State.ATTACK;

    }

    @Override
    public void onUpdate(float dt, GameScene gamescene) {
        super.onUpdate(dt, gamescene);
    }

    @Override
    public void onRender(Canvas canvas) {
        super.onRender(canvas);
    }


}
