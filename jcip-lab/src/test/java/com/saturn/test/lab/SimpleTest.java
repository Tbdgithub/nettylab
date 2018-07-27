package com.saturn.test.lab;

import org.junit.Assert;
import org.junit.Test;

public class SimpleTest {

    @Test
    public void hashCollisionTest() {
        String a1 = "Aa";
        String a2 = "BB";

        //BBAa”, “BBBB

        System.out.println(a1 + " :" + a1.hashCode());
        System.out.println(a2 + " :" + a2.hashCode());
        Assert.assertTrue(a1.hashCode()==a2.hashCode());

    }

}
