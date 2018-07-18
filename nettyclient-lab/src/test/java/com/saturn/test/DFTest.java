package com.saturn.test;

import org.junit.Test;

public class DFTest {

    //Diffle-hellman algorithm simple demo
    @Test
    public void test1() {
        System.out.println("let me test");

        long p = 11;
        long g = 7;
        //alice:
        long x = 3;
        long x1 = (long) (Math.pow(g, x) % p);
        System.out.println("send to bob x1:" + x1);

        //bob:
        long y = 6;
        long y1 = (long) (Math.pow(g, y) % p);
        System.out.println("send to alice y1:" + y1);

        //common known secret
        long a1 = (long) (Math.pow(y1, x) % p);
        System.out.println("alice know a1:" + a1);


        long b1 = (long) (Math.pow(x1, y) % p);
        System.out.println("bob know b1:" + b1);

        System.out.println("The common secret is a1 == b1 :" + a1);


    }
}
