package com.saturn.common;

import com.saturn.common.peak.Binsearcher;
import org.junit.Test;

public class BinsearcherTest {


    @Test
    public void BinsearcherTest1() {
        int[] a = {1, 2, 3, 4, 5, 6, 7};
        Binsearcher binsearcher = new Binsearcher();
        int result = binsearcher.find(a, 5);
        System.out.println("result:" + result);
    }

}
