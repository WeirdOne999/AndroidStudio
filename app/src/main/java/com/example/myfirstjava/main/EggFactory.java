package com.example.myfirstjava.main;

import com.example.myfirstjava.mgp2d.core.Vector2;

public class EggFactory implements ObjectPool.ObjectFactory<Egg>{
    public Vector2 defaultPos;
    public int defaultLayer;

    public EggFactory(){

    }

    public Egg ON(Vector2 defaultPos, int defaultLayer) {
        this.defaultPos = defaultPos;
        this.defaultLayer = defaultLayer;
        return create();
    }

    @Override
    public Egg create() {
        return new Egg(defaultLayer, defaultPos);
    }
}

