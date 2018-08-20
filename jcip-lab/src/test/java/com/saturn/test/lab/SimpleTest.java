package com.saturn.test.lab;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SimpleTest {

    @Test
    public void hashCollisionTest() {
        String a1 = "Aa";
        String a2 = "BB";

        //BBAa”, “BBBB

        System.out.println(a1 + " :" + a1.hashCode());
        System.out.println(a2 + " :" + a2.hashCode());
        Assert.assertTrue(a1.hashCode() == a2.hashCode());

    }

    @Test
    public void ComuteIfAbsentTest() {

        HashMap<String, List<String>> dic = new HashMap<>();
        List<String> oldList = new ArrayList<>();
        System.out.println(oldList.hashCode());
        // dic.putIfAbsent("a", oldList);

        List<String> list = dic.computeIfAbsent("a", a ->

                {
                    //return new ArrayList<>();
                    return oldList;
                }
        );

        System.out.println(list.hashCode());
        System.out.println(list);

    }

}
