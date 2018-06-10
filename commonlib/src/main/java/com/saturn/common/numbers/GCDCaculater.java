package com.saturn.common.numbers;

import org.junit.Assert;
import org.junit.Test;

import java.beans.Transient;

public class GCDCaculater {

    public long calc(long a, long b) throws IllegalArgumentException {

        if (a <= 0 || b <= 0) {
            throw new ArithmeticException("not support negative number or zero");
        }

        if (a < b) {
            return calc(b, a);
        }

        long remain = a % b;

        if (remain == 0) {
            return b;
        } else {
            return calc(b, remain);
        }
    }


    @Test
    public void calcTest() {

        GCDCaculater caculater = new GCDCaculater();
        long a = caculater.calc(1, 1);
        Assert.assertTrue(a == 1);

        a = caculater.calc(12, 6);
        Assert.assertTrue(a == 6);

        a = caculater.calc(12, 8);
        Assert.assertTrue(a == 4);

        a = caculater.calc(16, 12);
        Assert.assertTrue(a == 4);


        a = caculater.calc(12, 16);
        Assert.assertTrue(a == 4);

        System.out.println("all succeed");

    }
}
