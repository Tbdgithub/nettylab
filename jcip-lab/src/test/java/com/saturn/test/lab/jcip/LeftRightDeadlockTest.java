package com.saturn.test.lab.jcip;

import com.saturn.lab.jcip.AvoidLivenessHazards.LeftRightDeadlock;
import org.junit.Test;

import java.util.concurrent.atomic.LongAdder;

public class LeftRightDeadlockTest {


    @Test
    public void test1()
    {
        LongAdder leftAdder=new LongAdder();
        LongAdder rightAdder=new LongAdder();
        LeftRightDeadlock obj=new LeftRightDeadlock();

        Thread t1=new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    for(int i=0;i<1000;i++)
                    {
                        obj.leftRight();
                        leftAdder.increment();
                        System.out.println("leftAdder:"+leftAdder.longValue());
                        Thread.sleep(100);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        t1.start();

        Thread t2=new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    for(int i=0;i<1000;i++)
                    {
                        obj.rightLeft();
                        rightAdder.increment();
                        System.out.println("rightAdder:"+rightAdder.longValue());
                        Thread.sleep(100);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        t2.start();

        try {
            Thread.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
