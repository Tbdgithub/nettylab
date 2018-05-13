package com.saturn.lab.jcip.BuildingBlocks;

import java.util.concurrent.CountDownLatch;

public class TestHarness {
    public long timeTasks(int nThreads, final Runnable task)
            throws InterruptedException {

        final CountDownLatch startGate = new CountDownLatch(1);
        final CountDownLatch endGate = new CountDownLatch(nThreads);

        for (int i = 0; i < nThreads; i++) {
            Thread t = new Thread() {
                public void run() {
                    try {
                        System.out.println(" startGate.await() begin");
                        startGate.await();
                        System.out.println(" startGate.await()");
                        try {
                            task.run();
                        } finally {
                            endGate.countDown();
                        }
                    } catch (InterruptedException ignored) {
                    }
                }
            };
            t.start();
        }

        long start = System.nanoTime();
        //预备,同时开始run task
        startGate.countDown();

        //直到所有n个task都 完成后，结束 记时
        endGate.await();
        long end = System.nanoTime();
        return end - start;
    }


    public static void main(String[] args) {
        try {
            TestHarness worker = new TestHarness();
            long costNano = worker.timeTasks(3, new Runnable() {
                @Override
                public void run() {
                    System.out.println("let me run");
                }
            });

            System.out.println("costNano:" + costNano);
            Thread.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}