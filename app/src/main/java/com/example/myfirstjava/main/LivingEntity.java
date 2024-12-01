package com.example.myfirstjava.main;

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
        if (Health <= 0){
            destroy();
        }
    }
}
