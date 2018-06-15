package com.saturn.test.common.queue;

import org.junit.Test;

import javax.annotation.concurrent.ThreadSafe;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class BlockingQueueTest {


    @Test
    public void test1() throws Exception {

        BlockingQueue<Integer> queue = new LinkedBlockingQueue<>(1);


        for (int i = 0; i < 2; i++) {
            Thread t1 = new Thread(new Runnable() {
                @Override
                public void run() {

                    try {
                        for (int i = 0; i < 3; i++) {
                            queue.put(i);
                            System.out.println(Thread.currentThread().getName() + " put:" + i);
                            Thread.sleep(1000);
                        }

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });

            t1.setName("producer thread " + i);
            t1.start();
        }

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {

                try {

                    Thread.sleep(10000);
                    while (true) {
                        Integer item = queue.take();
                        System.out.println(Thread.currentThread().getName()+" take:" + item);

                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        t2.setName("consumer thread 1");
        t2.start();


        Thread.sleep(Integer.MAX_VALUE);

    }
}
