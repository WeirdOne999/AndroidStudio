package com.example.myfirstjava.main;


import com.example.myfirstjava.mgp2d.core.Vector2;

public class EggPool {
    private static ObjectPool<Egg> pool;

    public static void initializePool(int maxSize) {
        pool = new ObjectPool<>(maxSize, new EggFactory());
    }

    public static Egg acquire(Vector2 pos, int layer) {
        Egg egg = pool.acquire();
        egg.ON(layer, pos); // Reset zombie state
        return egg;
    }

    public static void release(Egg egg) {
        pool.release(egg);
    }
}
