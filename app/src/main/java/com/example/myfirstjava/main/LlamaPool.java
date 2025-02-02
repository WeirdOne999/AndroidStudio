package com.example.myfirstjava.main;


import com.example.myfirstjava.mgp2d.core.Vector2;

public class LlamaPool {
    private static ObjectPool<Llama> pool;

    public static void initializePool(int maxSize) {
        pool = new ObjectPool<>(maxSize, new LlamaFactory());
    }

    public static Llama acquire(Vector2 pos, int layer) {
        Llama llama = pool.acquire();
        llama.ON(pos, layer); // Reset zombie state
        return llama;
    }

    public static void release(Llama llama) {
        pool.release(llama);
    }
}
