package com.saturn.common;

import com.google.gson.Gson;
import com.saturn.common.peak.PeakFinder;
import org.junit.Assert;
import org.junit.Test;

public class PeakFinderTest {


    @Test
    public void PeakFinderTest1() {

        //int[] a = {1, 2, 3, 3, 2, 1};
        //
        //int[] a = {1, 2, 3, 3, 4, 4, 5, 2, 1,1,7,7,1};
        //int[] a = {1, 2,2, 3, 3, 3,2, 1, 1, 1};
        int[] a = {1, 2,3,4,3,2,1};
        PeakFinder finder = new PeakFinder();
//        int result = finder.getPeak1(a);
//        System.out.println("result:" + result);

        int result = finder.getPeakBin(a);
        System.out.println("result:" + result);

    }

    @Test
    public void PeakFinderTest0() {

        int[] a = {1, 2, 2, 3, 3, 1, 4, 1, 5, 1, 6, 1};
        PeakFinder finder = new PeakFinder();
//        int result = finder.getPeak1(a);
//        System.out.println("result:" + result);

        int result = finder.getPeakLinear(a);
        System.out.println("result:" + result);

    }

    @Test
    public void PeakFinderRecursiveTest() {

        //int [] a={1,2,2,3,3,1,4,1,5,1,6,1};
        int[] a = {1, 2, 3, 1};
        PeakFinder finder = new PeakFinder();
//        int result = finder.getPeak1(a);
//        System.out.println("result:" + result);

        int result = finder.findPeakElement(a);
        System.out.println("result:" + result);

    }

    @Test
    public void test4() {
        int[] a = {2, 7, 11, 15};
        PeakFinder finder = new PeakFinder();
        int[] result = finder.twoSum(a, 18);
        System.out.println("result:" + new Gson().toJson(result));


    }

    @Test
    public void test5() {
        int[] a = {2, 7, 11, 15};
        PeakFinder finder = new PeakFinder();
        int[] result = finder.twoSumMap(a, 26);

        System.out.println("result:" + new Gson().toJson(result));
    }

    @Test
    public void test6() {
        int[] a = {2, 7, 11, 15};
        PeakFinder finder = new PeakFinder();
        int[] result = finder.twoSumOnePass(a, 26);

        Assert.assertTrue(result.length == 2 && result[0] == 2 && result[1] == 3);

        System.out.println("result:" + new Gson().toJson(result));
    }
}
