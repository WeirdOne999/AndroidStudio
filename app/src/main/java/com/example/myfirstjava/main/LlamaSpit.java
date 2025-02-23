package com.example.myfirstjava.main;

import android.util.Log;

import com.example.myfirstjava.R;
import com.example.myfirstjava.mgp2d.core.GameEntity;
import com.example.myfirstjava.mgp2d.core.Vector2;

public class LlamaSpit extends ProjectileEntity{

    private float damage = 5;

    public LlamaSpit(GameEntity parent,int damageMultiplier) {
        super(parent, new Vector2(1,0),15,false);
        SpriteSet(R.drawable.llamaspit,15);
        damage *= damageMultiplier;
    }

    public void ON(GameEntity parent,int damageMultiplier) {
        super.ON(parent, new Vector2(1,0),15,false);
        SpriteSet(R.drawable.llamaspit,15);
        damage *= damageMultiplier;
    }

    @Override
    public void touchEnemy(LivingEntity enemy) {
        super.touchEnemy(enemy);
        enemy.SetHealth(enemy.getHealth() - damage);
        Log.d("LLAMASPIT",damage + " " + enemy.getHealth());
        destroy();
    }
}
