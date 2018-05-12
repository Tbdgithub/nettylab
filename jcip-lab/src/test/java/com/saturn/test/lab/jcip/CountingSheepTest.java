package com.saturn.test.lab.jcip;

import com.saturn.lab.jcip.SharingObjects.CountingSheep;
import org.junit.Test;

public class CountingSheepTest {

    @Test
    public void test1() {
        CountingSheep countingSheep = new CountingSheep();


        for (int i = 0; i < 10; i++) {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {

                    countingSheep.tryToSleep();
                }
            });

            t.start();
        }

        try {
            Thread.sleep(100);

            countingSheep.notifyMe();


            Thread.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}
