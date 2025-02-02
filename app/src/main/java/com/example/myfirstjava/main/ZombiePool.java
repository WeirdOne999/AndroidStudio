package com.example.myfirstjava.main;


import com.example.myfirstjava.mgp2d.core.Vector2;

public class ZombiePool {
    private static ObjectPool<Zombie> pool;

    public static void initializePool(int maxSize) {
        pool = new ObjectPool<>(maxSize, new ZombieFactory());
    }

    public static Zombie acquire(Vector2 pos, int layer) {
        Zombie zombie = pool.acquire();
        zombie.ON(pos, layer); // Reset zombie state
        return zombie;
    }

    public static void release(Zombie zombie) {
        pool.release(zombie);
    }
}
