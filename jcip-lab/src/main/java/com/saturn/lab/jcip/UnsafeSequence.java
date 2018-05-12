package com.saturn.lab.jcip;

import net.jcip.annotations.NotThreadSafe;

@NotThreadSafe
public class UnsafeSequence {

    private int value;

    /**
     * Returns a unique value.
     */
    public int getNext() {
        return value++;
    }
}
