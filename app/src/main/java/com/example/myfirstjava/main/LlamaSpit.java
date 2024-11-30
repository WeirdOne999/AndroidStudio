package com.example.myfirstjava.main;

import com.example.myfirstjava.R;
import com.example.myfirstjava.mgp2d.core.GameEntity;
import com.example.myfirstjava.mgp2d.core.Vector2;

public class LlamaSpit extends ProjectileEntity{

    private final float damage = 35;

    public LlamaSpit(GameEntity parent) {
        super(parent, new Vector2(1,0),15,false);
        SpriteSet(R.drawable.llamaspit,15);
    }

    @Override
    public void touchEnemy(LivingEntity enemy) {
        super.touchEnemy(enemy);
        enemy.SetHealth(enemy.getHealth() - damage);
        destroy();
    }
}
