package com.saturn.test.common;

import com.saturn.common.peak.Binsearcher;
import org.junit.Test;

public class BinsearcherTest {


    @Test
    public void BinsearcherTest1() {
        int[] a = {1, 2, 3, 4, 5, 6, 7};
        Binsearcher binsearcher = new Binsearcher();
        int result = binsearcher.find(a, 5);
        if(result!=-1) {
            System.out.println("index:" + result + " data:" + a[result]);
        }
        else
        {
            System.out.println("index:"+result+" data not find");
        }
    }

    @Test
    public void BinsearcherTest2() {
        int[] a = {1, 2};
        Binsearcher binsearcher = new Binsearcher();
        int result = binsearcher.find(a, 3);

        if(result!=-1) {
            System.out.println("index:" + result + " data:" + a[result]);
        }
        else
        {
            System.out.println("index:"+result+" data not find");
        }
    }

}
