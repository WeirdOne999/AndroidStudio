package com.example.myfirstjava.main;

import com.example.myfirstjava.mgp2d.core.Vector2;

public class SkeletonFactory implements ObjectPool.ObjectFactory<Skeleton>{
    public Vector2 defaultPos;
    public int defaultLayer;

    public SkeletonFactory(){

    }

    public Skeleton ON(Vector2 defaultPos, int defaultLayer) {
        this.defaultPos = defaultPos;
        this.defaultLayer = defaultLayer;
        return create();
    }

    @Override
    public Skeleton create() {
        return new Skeleton(defaultPos, defaultLayer);
    }
}

