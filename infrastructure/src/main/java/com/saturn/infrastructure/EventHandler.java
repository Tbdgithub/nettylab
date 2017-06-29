package com.saturn.infrastructure;

/**
 * Created by john.y on 2017-6-29.
 */
public interface EventHandler<E>
{
    void run(Object sender, E e);
}