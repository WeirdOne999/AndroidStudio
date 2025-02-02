package com.example.myfirstjava.main;

import com.example.myfirstjava.R;
import com.example.myfirstjava.mgp2d.core.GameEntity;
import com.example.myfirstjava.mgp2d.core.Vector2;

public class Arrow extends ProjectileEntity{

    private final float damage = 20;

    public Arrow(GameEntity parent) {
        super(parent, new Vector2(-1,0),20,false);
        SpriteSet(R.drawable.arrow,15);
    }

    public void ON(GameEntity parent) {
        super.ON(parent, new Vector2(-1,0),20,false);
    }

    @Override
    public void touchPlant(LivingEntity plant) {
        super.touchEnemy(plant);
        plant.SetHealth(plant.getHealth() - damage);
        destroy();
    }
}
