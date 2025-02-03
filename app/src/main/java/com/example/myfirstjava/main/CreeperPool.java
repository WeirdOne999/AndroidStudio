package com.example.myfirstjava.main;


import com.example.myfirstjava.mgp2d.core.Vector2;

public class CreeperPool {
    private static ObjectPool<Creeper> pool;

    public static void initializePool(int maxSize) {
        pool = new ObjectPool<>(maxSize, new CreeperFactory());
    }

    public static Creeper acquire(Vector2 pos, int layer) {
        Creeper creeper = pool.acquire();
        creeper.ON(pos, layer); // Reset zombie state
        return creeper;
    }

    public static void release(Creeper creeper) {
        pool.release(creeper);
    }
}
