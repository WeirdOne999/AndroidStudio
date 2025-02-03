package com.example.myfirstjava.main;


import com.example.myfirstjava.mgp2d.core.Vector2;

public class EndermanPool {
    private static ObjectPool<Enderman> pool;

    public static void initializePool(int maxSize) {
        pool = new ObjectPool<>(maxSize, new EndermanFactory());
    }

    public static Enderman acquire(Vector2 pos, int layer) {
        Enderman enderman = pool.acquire();
        enderman.ON(pos, layer); // Reset zombie state
        return enderman;
    }

    public static void release(Enderman enderman) {
        pool.release(enderman);
    }
}
