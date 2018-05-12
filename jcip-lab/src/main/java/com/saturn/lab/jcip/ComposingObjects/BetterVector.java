package com.saturn.lab.jcip.ComposingObjects;

import net.jcip.annotations.ThreadSafe;

import java.util.Vector;

@ThreadSafe
public class BetterVector <E> extends Vector<E> {

    // When extending a serializable class, you should redefine serialVersionUID
    static final long serialVersionUID = -3963416950630760754L;

    /**
     * 两步必需为原子操作
     * 1.检查是否在容器里
     * 2.将元素加入容器
     * @param x
     * @return
     */
    public synchronized boolean putIfAbsent(E x) {
        boolean absent = !contains(x);
        if (absent) {
            add(x);
        }

        return absent;
    }
}