package com.saturn.test.lab.jcip;

import com.saturn.lab.jcip.Sequence;
import com.saturn.lab.jcip.UnsafeSequence;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

public class SequenceTest {

    @Test
    public void test1() {
        Sequence sequence = new Sequence();

        int TestCount = 1000000;
        int threadCount = 5;
        //  Integer lastValue=0;

        AtomicInteger finished = new AtomicInteger(0);

        for (int i = 0; i < threadCount; i++) {
            Thread t1 = new Thread(new Runnable() {
                @Override
                public void run() {

                    for (int i = 0; i < TestCount; i++) {
                        sequence.getNext();
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


            System.out.println("nextvalue:" + sequence.getNext());

            Thread.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}
