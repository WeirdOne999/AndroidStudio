package com.example.myfirstjava.main;


import com.example.myfirstjava.mgp2d.core.GameEntity;
import com.example.myfirstjava.mgp2d.core.Vector2;

public class ArrowPool {
    private static ObjectPool<Arrow> pool;

    public static void initializePool(int maxSize, GameEntity defaultParent) {
        pool = new ObjectPool<>(maxSize, new ArrowFactory(defaultParent));
    }

    public static Arrow acquire(GameEntity ge) {
        if(ge == null) return null;
        Arrow arrow = pool.acquire();
        arrow.ON(ge); // Reset zombie state
        return arrow;
    }

    public static void release(Arrow arrow) {
        pool.release(arrow);
    }
}
