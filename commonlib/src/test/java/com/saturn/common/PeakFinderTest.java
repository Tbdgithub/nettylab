package com.saturn.common;

import com.saturn.common.peak.PeakFinder;
import org.junit.Test;

public class PeakFinderTest {


    @Test
    public void PeakFinderTest1() {

        //int[] a = {1, 2, 3, 3, 2, 1};
        //
        int[] a = {1, 2, 3, 3, 4, 4, 5, 2, 1,7,1};
        PeakFinder finder = new PeakFinder();
        int result = finder.getPeak1(a);
        System.out.println("result:" + result);

        result = finder.getPeakBin(a);
        System.out.println("result:" + result);

    }
}
