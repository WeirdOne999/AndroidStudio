package com.example.myfirstjava.main;

import com.example.myfirstjava.mgp2d.core.Vector2;

public class ChickenFactory implements ObjectPool.ObjectFactory<Chicken>{
    public Vector2 defaultPos;
    public int defaultLayer;

    public ChickenFactory(){

    }

    public Chicken ON(Vector2 defaultPos, int defaultLayer) {
        this.defaultPos = defaultPos;
        this.defaultLayer = defaultLayer;
        return create();
    }

    @Override
    public Chicken create() {
        return new Chicken(defaultPos, defaultLayer);
    }
}

