package com.example.myfirstjava.main;

import com.example.myfirstjava.mgp2d.core.Vector2;

public class ZombieFactory implements ObjectPool.ObjectFactory<Zombie>{
    public Vector2 defaultPos;
    public int defaultLayer;

    public ZombieFactory(){

    }

    public Zombie ON(Vector2 defaultPos, int defaultLayer) {
        this.defaultPos = defaultPos;
        this.defaultLayer = defaultLayer;
        return create();
    }

    @Override
    public Zombie create() {
        return new Zombie(defaultPos, defaultLayer);
    }
}

