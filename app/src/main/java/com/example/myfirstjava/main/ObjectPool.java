package com.example.myfirstjava.main;

import java.util.LinkedList;
import java.util.Queue;

public class ObjectPool<T> {
    private final Queue<T> pool;
    private final ObjectFactory<T> factory;
    private final int maxPoolSize;

    public ObjectPool(int maxPoolSize, ObjectFactory<T> factory) {
        this.maxPoolSize = maxPoolSize;
        this.factory = factory;
        pool = new LinkedList<>();
    }

    // Get an object from the pool
    public T acquire() {
        return pool.isEmpty() ? factory.create() : pool.poll();
    }

    // Return an object to the pool
    public void release(T object) {
        if (pool.size() < maxPoolSize) {
            pool.offer(object);
        }
    }

    // Interface to create new objects
    public interface ObjectFactory<T> {
        T create();
    }
}
