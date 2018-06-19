package com.saturn.lab.jcip.ApplyingThreadPools;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.*;
import java.util.concurrent.atomic.LongAdder;

@ThreadSafe
public class BoundedExecutor {
    private Executor exec = null;
    private Semaphore semaphore = null;

    private LongAdder counter = new LongAdder();


    private class ConsumerThreadFactory implements ThreadFactory {


        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r);
            String threadName = "hithread";
            t.setName(threadName);
            return t;
        }
    }

    private class CustomRejectedExecutionHandler implements RejectedExecutionHandler {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
//            try {
//                //workQueue put blocking
//                System.out.println("rejected");
//              //  executor.getQueue().put(r);
//                // System.out.println("put queue");
////            } catch (InterruptedException e) {
////                e.printStackTrace();
//            } catch (Exception ex) {
//                ex.printStackTrace();
//            }
            throw new RejectedExecutionException("rejected");

        }
    }

    public static void main(String[] args) {

        BoundedExecutor obj = new BoundedExecutor();
        obj.start();
    }


    public void start() {

        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1,
                1000, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<Runnable>(2),
                new ConsumerThreadFactory(), new CustomRejectedExecutionHandler());
        try {


            BoundedExecutor obj = new BoundedExecutor(executor, 2);


            for (int i = 0; i < 100; i++) {
                obj.submitTask(new Runnable() {
                    @Override
                    public void run() {

                        System.out.println("running...");
                        try {
                            Thread.sleep(10);

                            System.out.println("run finished...");
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });

            }

            Thread.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }


    public BoundedExecutor() {

    }

    public BoundedExecutor(Executor exec, int bound) {
        this.exec = exec;
        this.semaphore = new Semaphore(bound);
    }

    public void submitTask(final Runnable command)
            throws InterruptedException {
        semaphore.acquire();

        try {
            counter.increment();
            System.out.println("counter:" + counter.sum());
            exec.execute(new Runnable() {
                public void run() {
                    try {
                        command.run();
                    } finally {
                        semaphore.release();
                        counter.decrement();
                        System.out.println("counter:" + counter.sum());
                    }
                }
            });
        } catch (RejectedExecutionException e) {
            e.printStackTrace();
            semaphore.release();
            counter.decrement();
            System.out.println("counter:" + counter.sum());
        }
    }
}

