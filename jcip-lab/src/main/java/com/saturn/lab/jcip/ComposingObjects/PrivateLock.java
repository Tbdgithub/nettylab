package com.saturn.lab.jcip.ComposingObjects;

import net.jcip.annotations.GuardedBy;

public class PrivateLock {
    private final Object myLock = new Object();

    @GuardedBy("myLock") Widget widget;

    void someMethod() {
        synchronized (myLock) {
            // Access or modify the state of widget
        }
    }

    class Widget
    {

    }
}