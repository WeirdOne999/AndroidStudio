package com.example.myfirstjava.main;

import com.example.myfirstjava.mgp2d.core.Vector2;

public class LlamaFactory implements ObjectPool.ObjectFactory<Llama>{
    public Vector2 defaultPos;
    public int defaultLayer;

    public LlamaFactory(){

    }

    public Llama ON(Vector2 defaultPos, int defaultLayer) {
        this.defaultPos = defaultPos;
        this.defaultLayer = defaultLayer;
        return create();
    }

    @Override
    public Llama create() {
        return new Llama(defaultPos, defaultLayer);
    }
}

