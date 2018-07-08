package com.saturn.test.lab.jcip.CyclicBarrier;

import org.junit.Test;


import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.Date;

public class BarrierTest {


    @Test
    public void test2() {
        CyclicBarrier barrier = new CyclicBarrier(2, new Runnable() {
            @Override
            public void run() {

                System.out.println(new Date() + " I run");

            }
        });

        try {

            Thread t1 = new Thread(new Runnable() {
                @Override
                public void run() {

                    try {

                        for (int i = 0; i < 3; i++) {
                            barrier.await();
                            System.out.println(new Date() + " t1 await 1");
                            Thread.sleep(2000);
                        }


                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                }
            });

            t1.start();

//
//            Thread.sleep(2000);
//
//            Thread t2=new Thread(new Runnable() {
//                @Override
//                public void run() {
//
//                    try {
//                        barrier.await();
//                        System.out.println(new Date()+" await 2");
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    } catch (BrokenBarrierException e) {
//                        e.printStackTrace();
//                    }
//                }
//            });
//
//            t2.start();

            for(int i=0;i<5;i++) {
                barrier.await();
                System.out.println(new Date() + " await main");
            }

            Thread.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }

    }


    @Test
    public void test1() {
        try {
            CyclicBarrier cyclicBarrier = new CyclicBarrier(2);

            Thread t1 = new Thread(new Runnable() {
                @Override
                public void run() {

                    try {
                        System.out.println(new Date() + " t1 barrier wait");
                        cyclicBarrier.await();

                        System.out.println(new Date() + " t1 execute");
                        Thread.sleep(1000);


                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }


                }
            });

            t1.start();

            Thread t2 = new Thread(new Runnable() {
                @Override
                public void run() {

                    try {
                        System.out.println(new Date() + " t2 barrier wait");
                        Thread.sleep(5000);
                        cyclicBarrier.await();
                        System.out.println(new Date() + " t2 barrier open");

                        System.out.println(new Date() + " t2 execute");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                }
            });


            t2.start();

            System.out.println("wait door open");
            Thread.sleep(Integer.MAX_VALUE);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
