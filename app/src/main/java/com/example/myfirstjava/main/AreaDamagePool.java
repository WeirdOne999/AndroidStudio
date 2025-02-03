package com.example.myfirstjava.main;


import com.example.myfirstjava.mgp2d.core.GameEntity;
import com.example.myfirstjava.mgp2d.core.Vector2;

public class AreaDamagePool {
    private static ObjectPool<AreaDamage> pool;

    public static void initializePool(int maxSize) {
        pool = new ObjectPool<>(maxSize, new AreaDamageFactory());
    }

    public static AreaDamage acquire(float size, Vector2 position, float damage,boolean targetEnemy) {
        AreaDamage areaDamage = pool.acquire();
        areaDamage.ON(size,position,damage,targetEnemy); // Reset zombie state
        return areaDamage;
    }

    public static void release(AreaDamage areaDamage) {
        pool.release(areaDamage);
    }
}
