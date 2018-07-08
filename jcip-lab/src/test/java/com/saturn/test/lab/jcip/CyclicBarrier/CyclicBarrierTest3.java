package com.saturn.test.lab.jcip.CyclicBarrier;

import java.util.concurrent.BrokenBarrierException;

import java.util.concurrent.CyclicBarrier;
import java.util.Date;

public class CyclicBarrierTest3 {

    static CyclicBarrier c = new CyclicBarrier(3);

    public static void main(String[] args) throws InterruptedException, BrokenBarrierException {
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    c.await();
                    System.out.println(new Date() + "thread 1 execute");

                } catch (Exception e) {
                    System.out.println(new Date() + "thread 1 broken:"+e.toString());
                    e.printStackTrace();
                }
            }
        });
        thread.start();

        System.out.println(new Date() + " t1 started");



        Thread t3 = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    c.await();
                    System.out.println(new Date() + "thread 3 execute");

                } catch (Exception e) {
                    System.out.println(new Date() + "thread 3 broken:"+e.toString());
                    e.printStackTrace();
                }
            }
        });
        t3.start();

        Thread.sleep(5000);
        thread.interrupt();

        System.out.println(new Date() + " interrupted");
        try {
            c.await();
            System.out.println(new Date() + "thread 2 execute");
        } catch (Exception e) {
            System.out.println(new Date() + " thread 2  broken status:" + c.isBroken()+" ex:"+e.toString());
        }

        Thread.sleep(Integer.MAX_VALUE);
    }
}

//todo thread join