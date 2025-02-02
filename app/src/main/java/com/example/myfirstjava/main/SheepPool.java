package com.example.myfirstjava.main;


import com.example.myfirstjava.mgp2d.core.Vector2;

public class SheepPool {
    private static ObjectPool<Sheep> pool;

    public static void initializePool(int maxSize) {
        pool = new ObjectPool<>(maxSize, new SheepFactory());
    }

    public static Sheep acquire(Vector2 pos, int layer) {
        Sheep sheep = pool.acquire();
        sheep.ON(pos, layer); // Reset zombie state
        return sheep;
    }

    public static void release(Sheep sheep) {
        pool.release(sheep);
    }
}
