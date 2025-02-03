package com.example.myfirstjava.main;

import com.example.myfirstjava.mgp2d.core.Vector2;

public class EndermanFactory implements ObjectPool.ObjectFactory<Enderman>{
    public Vector2 defaultPos;
    public int defaultLayer;

    public EndermanFactory(){

    }

    public Enderman ON(Vector2 defaultPos, int defaultLayer) {
        this.defaultPos = defaultPos;
        this.defaultLayer = defaultLayer;
        return create();
    }

    @Override
    public Enderman create() {
        return new Enderman(defaultPos, defaultLayer);
    }
}

