package com.saturn.lab.jcip.BuildingBlocks;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Memoizer2 <A, V> implements Computable<A, V> {
    private final Map<A, V> cache = new ConcurrentHashMap<A, V>();
    private final Computable<A, V> c;

    public Memoizer2(Computable<A, V> c) {
        this.c = c;
    }

    public V compute(A arg) throws InterruptedException {

        //存在重复计算可能性
        V result = cache.get(arg);
        if (result == null) {

            //可以并行计算
            result = c.compute(arg);
            cache.put(arg, result);
        }
        return result;
    }
}