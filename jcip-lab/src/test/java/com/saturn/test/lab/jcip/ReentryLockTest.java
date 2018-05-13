package com.saturn.test.lab.jcip;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class ReentryLockTest {


    @Test
    public void lockTest() {
        ReentrantLock reentrantLock = new ReentrantLock();
        AtomicInteger accessCount = new AtomicInteger(0);
        long begin=System.nanoTime();

        for (int i = 0; i < 5; i++) {
            Thread t1 = new Thread(new Runnable() {
                @Override
                public void run() {

                    while (true) {
                        //System.out.println("t1 locking...");
                        reentrantLock.lock();

                        try {
                            //System.out.println("t1 got lock");
                            //Thread.sleep(1);
                            accessCount.incrementAndGet();
                        }
//                        catch (InterruptedException ex) {
//                            Thread.currentThread().interrupt();
//                        }
                        finally {
                            reentrantLock.unlock();
                            //  System.out.println("t1 unlock lock");
                        }
                    }
                }
            });

            t1.start();
        }

        for (int j = 0; j < 5; j++) {
            Thread t2 = new Thread(new Runnable() {
                @Override
                public void run() {

                    while (true) {
                        //  System.out.println("t2 locking...");
                        reentrantLock.lock();

                        try {
                            //  System.out.println("t2 got lock");
                            // Thread.sleep(1);
                            accessCount.incrementAndGet();
                        }
//                        catch (InterruptedException ex) {
//                            Thread.currentThread().interrupt();
//                        }
                        finally {
                            reentrantLock.unlock();
                            // System.out.println("t1 unlock lock");
                        }
                    }
                }
            });

            t2.start();

        }

        try {

            Thread tMonitor=new Thread(new Runnable() {
                @Override
                public void run() {

                    while (true) {
                        long costNano = System.nanoTime() - begin;

                        long costSecond=(long)(costNano/1e9);

                        if (costNano > 0) {

                            long tps = (long)(accessCount.get() * 1e9 / costNano);
                            System.out.println("tps:" + tps + " accessCount:" + accessCount.get()+" costSecond:"+costSecond);
                        }

                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

            tMonitor.start();


            Thread.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}
