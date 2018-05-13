package com.saturn.lab.jcip.BuildingBlocks;

import java.util.concurrent.*;

/**
 * 线程安全，
 * 高性能
 * @param <A>
 * @param <V>
 */
public class Memoizer <A, V> implements Computable<A, V> {
    private final ConcurrentMap<A, Future<V>> cache
            = new ConcurrentHashMap<A, Future<V>>();
    private final Computable<A, V> c;

    public Memoizer(Computable<A, V> c) {
        this.c = c;
    }

    public V compute(final A arg) throws InterruptedException {
        while (true) {
            Future<V> f = cache.get(arg);
            if (f == null) {
                Callable<V> eval = new Callable<V>() {
                    public V call() throws InterruptedException {
                        return c.compute(arg);
                    }
                };
                FutureTask<V> ft = new FutureTask<V>(eval);

                //只有一次能成功，如果第一次放，f 为null
                f = cache.putIfAbsent(arg, ft);
                if (f == null) {
                    f = ft;
                    ft.run();
                }
                //如果 f 不为null ，说明arg已put过了，不会重复；但会多生成ft ??
            }

            try {

                //此时f 不为空
                return f.get();

                //总会得到结果
            } catch (CancellationException e) {
                cache.remove(arg, f);
            } catch (ExecutionException e) {
                throw LaunderThrowable.launderThrowable(e.getCause());
            }
        }
    }
}