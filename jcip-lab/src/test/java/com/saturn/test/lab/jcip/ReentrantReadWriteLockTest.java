package com.saturn.test.lab.jcip;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReentrantReadWriteLockTest

{


    /**
     * 500w/s reader tps, 300/s writer tps ; reader 50 ,writer  2 居然少于reentrylock??
     * 1.3kw /s tps reader 10;writer 5
     */

    @Test
    public void lockTest() {
        ReentrantReadWriteLock reentrantLock = new ReentrantReadWriteLock();

        AtomicInteger readerCounter = new AtomicInteger(0);
        AtomicInteger writerCounter=new AtomicInteger(0);
        long begin=System.nanoTime();

        for (int i = 0; i < 10; i++) {
            Thread t1 = new Thread(new Runnable() {
                @Override
                public void run() {

                    while (true) {
                        //System.out.println("t1 locking...");
                        reentrantLock.readLock().lock();

                        try {
                            //System.out.println("t1 got lock");
                            //Thread.sleep(1);
                            readerCounter.incrementAndGet();
                        }
//                        catch (InterruptedException ex) {
//                            Thread.currentThread().interrupt();
//                        }
                        finally {
                            reentrantLock.readLock().unlock();
                            //  System.out.println("t1 unlock lock");
                        }
                    }
                }
            });

            t1.start();
        }

        for (int j = 0; j < 1; j++) {
            Thread t2 = new Thread(new Runnable() {
                @Override
                public void run() {

                    while (true) {
                        //  System.out.println("t2 locking...");
                        reentrantLock.writeLock().lock();

                        try {
                            //  System.out.println("t2 got lock");
                            // Thread.sleep(1);
                            writerCounter.incrementAndGet();
                        }
//                        catch (InterruptedException ex) {
//                            Thread.currentThread().interrupt();
//                        }
                        finally {
                            reentrantLock.writeLock().unlock();
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

                            long tps = (long)(readerCounter.get() * 1e9 / costNano);
                            System.out.println("readerCounter tps:" + tps + " accessCount:" + readerCounter.get()+" costSecond:"+costSecond);

                             tps = (long)(writerCounter.get() * 1e9 / costNano);
                            System.out.println("writerCounter tps:" + tps + " accessCount:" + writerCounter.get()+" costSecond:"+costSecond);
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
