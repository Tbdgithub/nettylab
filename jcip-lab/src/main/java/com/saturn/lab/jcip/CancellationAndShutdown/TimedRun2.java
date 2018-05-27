package com.saturn.lab.jcip.CancellationAndShutdown;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static com.saturn.lab.jcip.BuildingBlocks.LaunderThrowable.launderThrowable;
import static java.util.concurrent.Executors.newScheduledThreadPool;

public class TimedRun2 {
    private static final ScheduledExecutorService cancelExec = newScheduledThreadPool(1);

    public static void timedRun(final Runnable r,
                                long timeout, TimeUnit unit)
            throws InterruptedException {

        class RethrowableTask implements Runnable {

            private volatile Throwable t;

            public void run() {
                try {
                    r.run();
                } catch (Throwable t) {
                    this.t = t;
                }
            }

            void rethrow() {
                if (t != null) {

                    t.printStackTrace();
                    throw launderThrowable(t);
                }
            }
        }

        RethrowableTask task = new RethrowableTask();
        final Thread taskThread = new Thread(task);
        taskThread.start();

        cancelExec.schedule(new Runnable() {
            public void run() {
                taskThread.interrupt();
            }
        }, timeout, unit);

        taskThread.join(unit.toMillis(timeout*2));
        task.rethrow();
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
                        Thread.sleep(2000);
                    }
                } catch (InterruptedException e) {
                    //Thread.currentThread().interrupt();
                    e.printStackTrace();
                  //  throw new Throwable(e);
                    throw  new RuntimeException("xxx");

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