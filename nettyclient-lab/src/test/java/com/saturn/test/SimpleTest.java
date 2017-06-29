package com.saturn.test;

import org.junit.Test;

/**
 * Created by john.y on 2017-6-29.
 */
public class SimpleTest {

    @Test
    public void test1() {

        Integer a = -2147483642;

       String hex= a.toHexString(a);

        System.out.println("hex:"+hex);
    }
}
