package com.saturn.test.lab.jcip.CyclicBarrier;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

class Horse implements Runnable {
    private static int counter = 0;
    private final int id = counter++;
    private int strides = 0;
    private static Random rand = new Random(47);
    private static CyclicBarrier barrier;
    public Horse(CyclicBarrier b) { barrier = b; }
    public synchronized int getStrides() { return strides; }
    public void run() {
        try {
            while(!Thread.interrupted()) {
                synchronized(this) {
                    strides += rand.nextInt(3); // Produces 0, 1 or 2
                }
                barrier.await();

                //实质上是每个时间段结束时, 比如200 ms ，计数每个horse跑的路程。即所有horse都 跑了200ms后。重新计数.

                //多个horse 线程全都到barrier了时，继续执行，否则等待
            }
        } catch(InterruptedException e) {

            System.out.println("interrupted");
            e.printStackTrace();
            // A legitimate way to exit
        } catch(BrokenBarrierException e) {

            e.printStackTrace();
            // This one we want to know about
            throw new RuntimeException(e);
        }
    }

    public String toString() { return "Horse " + id + " "; }
    public String tracks() {
        StringBuilder s = new StringBuilder();
        for(int i = 0; i < getStrides(); i++)
            s.append("*");
        s.append(id);
        return s.toString();
    }
}