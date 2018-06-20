package com.saturn.test.lab.jcip;

import com.saturn.lab.jcip.ApplyingThreadPools.TimingThreadPool;
import org.junit.Test;

import java.util.concurrent.ThreadPoolExecutor;

public class TimingThreadPoolTest {

    @Test
    public void test1() {

        try {
            TimingThreadPool executor = new TimingThreadPool();

            for(int i=0;i<1;i++) {
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("hi");
                    }
                });
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
