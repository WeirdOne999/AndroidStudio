package com.example.myfirstjava.main;

import com.example.myfirstjava.mgp2d.core.Vector2;

public class CreeperFactory implements ObjectPool.ObjectFactory<Creeper>{
    public Vector2 defaultPos;
    public int defaultLayer;

    public CreeperFactory(){

    }

    public Creeper ON(Vector2 defaultPos, int defaultLayer) {
        this.defaultPos = defaultPos;
        this.defaultLayer = defaultLayer;
        return create();
    }

    @Override
    public Creeper create() {
        return new Creeper(defaultPos, defaultLayer);
    }
}

