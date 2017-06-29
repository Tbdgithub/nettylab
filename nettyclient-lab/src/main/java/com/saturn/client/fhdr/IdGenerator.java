package com.saturn.client.fhdr;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by john.y on 2017-6-26.
 */
public class IdGenerator {

    private static final AtomicInteger AtomicTid = new AtomicInteger(0);

    public synchronized static int getNextTid() {
        int nextValue = AtomicTid.incrementAndGet();

        if (nextValue == Integer.MAX_VALUE) {
            AtomicTid.set(0);
        }

        return nextValue;
    }
}
