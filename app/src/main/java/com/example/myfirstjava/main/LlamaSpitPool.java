package com.example.myfirstjava.main;


import com.example.myfirstjava.mgp2d.core.GameEntity;

public class LlamaSpitPool {
    private static ObjectPool<LlamaSpit> pool;

    public static void initializePool(int maxSize, GameEntity defaultParent) {
        pool = new ObjectPool<>(maxSize, new LlamaSpitFactory(defaultParent));
    }

    public static LlamaSpit acquire(GameEntity ge, int DamageMultiplier) {
        if(ge == null) return null;
        LlamaSpit llamaSpit = pool.acquire();
        llamaSpit.ON(ge, DamageMultiplier); // Reset zombie state
        return llamaSpit;
    }

    public static void release(LlamaSpit llamaSpit) {
        pool.release(llamaSpit);
    }
}
