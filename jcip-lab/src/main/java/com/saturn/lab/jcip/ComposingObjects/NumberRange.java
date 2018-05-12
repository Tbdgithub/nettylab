package com.saturn.lab.jcip.ComposingObjects;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 有问题
 * 因为lower ,upper 不是独立的,两者相互依赖
 * 必须把两个变量都 加锁才能线程安全
 */
public class NumberRange {

    // INVARIANT: lower <= upper
    private final AtomicInteger lower = new AtomicInteger(0);
    private final AtomicInteger upper = new AtomicInteger(0);

    public void setLower(int i) {
        // Warning -- unsafe check-then-act
        if (i > upper.get()) {
            throw new IllegalArgumentException("can't set lower to " + i + " > upper");
        }

        lower.set(i);
    }

    public void setUpper(int i) {
        // Warning -- unsafe check-then-act
        if (i < lower.get())
            throw new IllegalArgumentException("can't set upper to " + i + " < lower");
        upper.set(i);
    }

    public boolean isInRange(int i) {
        return (i >= lower.get() && i <= upper.get());
    }
}