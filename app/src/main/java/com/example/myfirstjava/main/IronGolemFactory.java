package com.example.myfirstjava.main;

import com.example.myfirstjava.mgp2d.core.Vector2;

public class IronGolemFactory implements ObjectPool.ObjectFactory<IronGolem>{
    public Vector2 defaultPos;
    public int defaultLayer;

    public IronGolemFactory(){

    }

    public IronGolem ON(Vector2 defaultPos, int defaultLayer) {
        this.defaultPos = defaultPos;
        this.defaultLayer = defaultLayer;
        return create();
    }

    @Override
    public IronGolem create() {
        return new IronGolem(defaultPos, defaultLayer);
    }
}

