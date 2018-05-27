package com.saturn.lab.jcip.CancellationAndShutdown;

import java.math.BigInteger;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class PrimeProducer extends Thread {
    private final BlockingQueue<BigInteger> queue;

    PrimeProducer(BlockingQueue<BigInteger> queue) {
        this.queue = queue;
    }

    public void run() {
        try {
            BigInteger p = BigInteger.ONE;
            //while (!Thread.currentThread().isInterrupted())
            while (!Thread.currentThread().isInterrupted())
            {
                try {
                    queue.put(p = p.nextProbablePrime());
                    System.out.println("prime:" + p);

                    Thread.sleep(1000);
                } catch (InterruptedException e) {

                    Thread.currentThread().interrupt();
                    e.printStackTrace();
                    //throw e;
                }
            }

        } catch (Exception consumed) {
            /* Allow thread to exit */

            System.out.println("interrupted :"+new java.util.Date());
            consumed.printStackTrace();
        }

        System.out.println("finished");
    }

    public void cancel() {
        interrupt();
    }


    public void start() {
        Thread t = new Thread(this);
        t.start();


    }

    public static void main(String[] args) throws Exception {

        BlockingQueue<BigInteger> bigIntegers = new ArrayBlockingQueue<BigInteger>(2);

       PrimeProducer primeProducer = new PrimeProducer(bigIntegers);

      //  primeProducer.start();

        Thread t1=new Thread(primeProducer );

        t1.start();

        Thread tCancel = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
               // primeProducer.cancel();

                t1.interrupt();
                System.out.println("interrupt :"+new java.util.Date());
            }
        });

        tCancel.start();

        Thread.sleep(Integer.MAX_VALUE);


    }
}