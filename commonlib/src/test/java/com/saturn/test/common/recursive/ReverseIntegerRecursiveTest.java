package com.saturn.test.common.recursive;

import com.saturn.common.recursive.ReverseIntegerRecursive;
import org.junit.Assert;
import org.junit.Test;

public class ReverseIntegerRecursiveTest {

    @Test
    public void test1() {

        ReverseIntegerRecursive fiLoPrinter = new ReverseIntegerRecursive();
        String output = fiLoPrinter.print(123456);
        System.out.println(output);

        Assert.assertTrue(output.equals("654321"));
    }
}
