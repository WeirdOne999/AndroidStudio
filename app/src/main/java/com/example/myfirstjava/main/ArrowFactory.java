package com.example.myfirstjava.main;

import com.example.myfirstjava.mgp2d.core.GameEntity;
import com.example.myfirstjava.mgp2d.core.Vector2;

public class ArrowFactory implements ObjectPool.ObjectFactory<Arrow>{
    public GameEntity defaultGE;

    public ArrowFactory(GameEntity defaultParent){
        this.defaultGE = defaultParent;

    }

    public Arrow ON(GameEntity ge) {
        defaultGE = ge;
        return create();
    }

    @Override
    public Arrow create() {
        return new Arrow(defaultGE);
    }
}

