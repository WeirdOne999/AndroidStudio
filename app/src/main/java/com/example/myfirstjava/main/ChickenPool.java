package com.example.myfirstjava.main;


import com.example.myfirstjava.mgp2d.core.Vector2;

public class ChickenPool {
    private static ObjectPool<Chicken> pool;

    public static void initializePool(int maxSize) {
        pool = new ObjectPool<>(maxSize, new ChickenFactory());
    }

    public static Chicken acquire(Vector2 pos, int layer) {
        Chicken chicken = pool.acquire();
        chicken.ON(pos, layer); // Reset zombie state
        return chicken;
    }

    public static void release(Chicken chicken) {
        pool.release(chicken);
    }
}
