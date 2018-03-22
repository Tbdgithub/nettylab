package com.saturn.common.radical;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SqrtBinaryTest {


    @Before
    public void setup()
    {
        System.out.println("before");
    }

    @After
    public void after()
    {
        System.out.println("after");
    }

    @Test
    public void test1() {

        for(int i=2;i<6;i++) {

            RadicalBuilder sqrtBinary = new RadicalBinary(i);
            double result = sqrtBinary.process();
            System.out.println("result:" + result);
            System.out.println("answer:"+Math.sqrt(i));
            System.out.println("sqrtBinary:"+sqrtBinary);
        }
    }

    @Test
    public void test2() {

        for(int i=2;i<6;i++) {

            RadicalBuilder sqrtBinary = new RadicalNewton(i);
            double result = sqrtBinary.process();
            System.out.println("result:" + result);
            System.out.println("answer:"+Math.sqrt(i));
            System.out.println("sqrtBinary:"+sqrtBinary);
        }
    }
}
