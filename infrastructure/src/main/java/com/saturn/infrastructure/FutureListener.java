package com.saturn.infrastructure;

/**
 * Created by john.y on 2017-6-29.
 */
public interface FutureListener<V> {
    void run(Result<V> result);
}

