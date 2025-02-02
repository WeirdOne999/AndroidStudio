package com.example.myfirstjava.main;


import com.example.myfirstjava.mgp2d.core.Vector2;

public class IronGolemPool {
    private static ObjectPool<IronGolem> pool;

    public static void initializePool(int maxSize) {
        pool = new ObjectPool<>(maxSize, new IronGolemFactory());
    }

    public static IronGolem acquire(Vector2 pos, int layer) {
        IronGolem irongolem = pool.acquire();
        irongolem.ON(pos, layer); // Reset zombie state
        return irongolem;
    }

    public static void release(IronGolem irongolem) {
        pool.release(irongolem);
    }
}
