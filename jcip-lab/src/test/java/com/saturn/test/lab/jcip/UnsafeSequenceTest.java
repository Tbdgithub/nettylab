package com.saturn.test.lab.jcip;

import com.saturn.lab.jcip.introduction.UnsafeSequence;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

public class UnsafeSequenceTest {


    @Test
    public void test1() {
        UnsafeSequence unsafeSequence = new UnsafeSequence();

        int TestCount = 1000000;
        int threadCount = 1;
        //  Integer lastValue=0;

        AtomicInteger finished = new AtomicInteger(0);

        for (int i = 0; i < threadCount; i++) {
            Thread t1 = new Thread(new Runnable() {
                @Override
                public void run() {

                    for (int i = 0; i < TestCount; i++) {
                        unsafeSequence.getNext();
                    }

                    finished.addAndGet(1);

                    //System.out.println(next);
                }
            });

            t1.start();

        }

        try {
            while (finished.get() < threadCount) {
                Thread.sleep(100);
            }


            System.out.println("nextvalue:" + unsafeSequence.getNext());

            Thread.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

}
