package com.saturn.server.rdate;

import java.util.Date;

/**
 * Created by john.y on 2017-6-23.
 */
public class UnixTime {

    private long value;

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public UnixTime() {
        this(System.currentTimeMillis() / 1000L + 2208988800L);
    }

    public UnixTime(long value) {
        this.value = value;
    }


    @Override
    public String toString() {
        return new Date((getValue() - 2208988800L) * 1000L).toString();
    }

}