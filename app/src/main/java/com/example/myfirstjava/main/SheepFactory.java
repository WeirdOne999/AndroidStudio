package com.example.myfirstjava.main;

import com.example.myfirstjava.mgp2d.core.Vector2;

public class SheepFactory implements ObjectPool.ObjectFactory<Sheep>{
    public Vector2 defaultPos;
    public int defaultLayer;

    public SheepFactory(){

    }

    public Sheep ON(Vector2 defaultPos, int defaultLayer) {
        this.defaultPos = defaultPos;
        this.defaultLayer = defaultLayer;
        return create();
    }

    @Override
    public Sheep create() {
        return new Sheep(defaultPos, defaultLayer);
    }
}

