package com.example.myfirstjava.main;

import com.example.myfirstjava.mgp2d.core.GameEntity;
import com.example.myfirstjava.mgp2d.core.Vector2;

public class AreaDamageFactory implements ObjectPool.ObjectFactory<AreaDamage>{
    public float size = 1;
    public float damage = 1;
    public Vector2 position = new Vector2(0,0);

    public AreaDamageFactory(){
    }

    public AreaDamage ON(float size,Vector2 position,float damage) {
        this.size = size;
        this.damage = damage;
        this.position = position;
        return create();
    }

    @Override
    public AreaDamage create() {
        return new AreaDamage(size,position,damage);
    }
}

