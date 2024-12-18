package com.example.myfirstjava.main;

import android.util.Log;

import com.example.myfirstjava.mgp2d.core.GameEntity;
import com.example.myfirstjava.mgp2d.core.GameScene;

public class LivingEntity extends PhysicsEntity {
    private float Health = 0;
    public Holder onHolder;
    public void SetHealth(float health){
        Health = health;
    }
    public float getHealth(){return Health;}
    public boolean isEnemy;
    @Override
    public void onUpdate(float dt, GameScene gamescene) {
        super.onUpdate(dt, gamescene);
        Log.d("CHIKCENTEST","LIVINGENTITY");
        if (Health <= 0){
            if (!isEnemy){
                onHolder._mob = null;
            }
            destroy();
        }
    }
}
