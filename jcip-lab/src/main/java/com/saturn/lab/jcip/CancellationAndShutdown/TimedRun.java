package com.saturn.lab.jcip.CancellationAndShutdown;

import java.util.concurrent.*;

import static com.saturn.lab.jcip.BuildingBlocks.LaunderThrowable.launderThrowable;

public class TimedRun {

    private static final ExecutorService taskExec = Executors.newCachedThreadPool();

    public static void timedRun(Runnable r,
                                long timeout, TimeUnit unit)
            throws InterruptedException {
        Future<?> task = taskExec.submit(r);
        try {
            task.get(timeout, unit);
        } catch (TimeoutException e) {

            System.out.println("time out");
            e.printStackTrace();
            // task will be cancelled below
        } catch (ExecutionException e) {

            e.printStackTrace();
            // exception thrown in task; rethrow
            throw launderThrowable(e.getCause());
        } finally {
            // Harmless if task already completed

            System.out.println("begin cancel"+new java.util.Date());
            task.cancel(true); // interrupt if running
        }
    }

    public static void main(String [] args)
    {
        Runnable runnable=new Runnable() {
            @Override
            public void run() {

                try {
                    while (!Thread.currentThread().isInterrupted())
                    {
                        System.out.println("I am running");
                        Thread.sleep(1000);
                    }
                } catch (InterruptedException e) {

                    System.out.println("cancelled"+ new java.util.Date() );
                    Thread.currentThread().interrupt();
                    e.printStackTrace();
                    //  throw new Throwable(e);
                    //throw  new RuntimeException("xxx");

                }
            }
        };


        try {
            timedRun(runnable,5000,TimeUnit.MILLISECONDS);

            Thread.sleep(Integer.MAX_VALUE);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}