package com.example.myfirstjava.main;

import com.example.myfirstjava.mgp2d.core.GameEntity;

public class LlamaSpitFactory implements ObjectPool.ObjectFactory<LlamaSpit>{
    public GameEntity defaultGE;
    public int defaultMultiplier;

    public LlamaSpitFactory(GameEntity defaultParent){
        this.defaultGE = defaultParent;

    }

    public LlamaSpit ON(GameEntity ge,int defaultMultiplier) {
        defaultGE = ge;
        this.defaultMultiplier = defaultMultiplier;

        return create();
    }

    @Override
    public LlamaSpit create() {
        return new LlamaSpit(defaultGE,defaultMultiplier);
    }
}

