package com.saturn.common.radical;

import com.saturn.common.radical.RadicalBinary;
import com.saturn.common.radical.RadicalBuilder;
import com.saturn.common.radical.RadicalNewton;
import org.junit.Test;

public class SqrtBinaryTest {

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
