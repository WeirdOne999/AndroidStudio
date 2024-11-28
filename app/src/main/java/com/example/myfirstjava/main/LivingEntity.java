package com.example.myfirstjava.main;

import com.example.myfirstjava.mgp2d.core.GameEntity;
import com.example.myfirstjava.mgp2d.core.GameScene;

public class LivingEntity extends GameEntity {
    private float Health = 0;

    public void SetHealth(int health){
        Health = health;
    }

    @Override
    public void onUpdate(float dt, GameScene gamescene) {
        super.onUpdate(dt, gamescene);
        if (Health <= 0){
            destroy();
        }
    }
}
