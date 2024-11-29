package com.example.myfirstjava.main;

import android.graphics.Canvas;

import com.example.myfirstjava.R;
import com.example.myfirstjava.mgp2d.core.GameScene;
import com.example.myfirstjava.mgp2d.core.Vector2;

public class Zombie extends EnemyEntity{
    public Zombie(Vector2 pos,int layer){
        super(100,5,1.5f);
        setLayer(layer);
        setPosition(pos);
        walk.setAnimationHolder(R.drawable.zombiewalk,4,4,4,7,12);
        attack.setAnimationHolder(R.drawable.zombieattack,4,5,5,9,0);
        SetSprite(walk);
        int size = 100;
        setSize(new Vector2(size,size));
        _animatedSprite.offset = new Vector2(0,getScreenHeight() / 12);
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
