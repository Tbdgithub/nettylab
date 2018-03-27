package com.saturn.common.sort;

import org.junit.Test;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadPoolTest {


    @Test
    public void test1() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 5,
                1000, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<Runnable>(1),
                new ConsumerThreadFactory(), new CustomRejectedExecutionHandler());


        for (int i = 0; i < 8; i++) {

            try {

                executor.execute(new Runnable() {
                    @Override
                    public void run() {

                        System.out.println("hi");
                        try {
                            Thread.sleep(5000 );
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                });
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        try {
            Thread.sleep(1000000);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private class CustomRejectedExecutionHandler implements RejectedExecutionHandler {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            try {
                //workQueue put blocking
                System.out.println("rejected");
                executor.getQueue().put(r);
               // System.out.println("put queue");
//            } catch (InterruptedException e) {
//                e.printStackTrace();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private class ConsumerThreadFactory implements ThreadFactory {


        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r);
            String threadName = "hithread";
            t.setName(threadName);
            return t;
        }
    }
}
