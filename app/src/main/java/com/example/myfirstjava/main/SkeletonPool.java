package com.example.myfirstjava.main;


import com.example.myfirstjava.mgp2d.core.Vector2;

public class SkeletonPool {
    private static ObjectPool<Skeleton> pool;

    public static void initializePool(int maxSize) {
        pool = new ObjectPool<>(maxSize, new SkeletonFactory());
    }

    public static Skeleton acquire(Vector2 pos, int layer) {
        Skeleton skeleton = pool.acquire();
        skeleton.ON(pos, layer); // Reset zombie state
        return skeleton;
    }

    public static void release(Skeleton skeleton) {
        pool.release(skeleton);
    }
}
