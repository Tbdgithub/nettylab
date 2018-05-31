package com.saturn.test.common.thread;

import org.junit.Test;
import java.util.Date;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class InterruptTest {


    @Test
    public void test1()
    {

        try {

            BlockingQueue<String> queue=new ArrayBlockingQueue<String>(2);

            Thread t1=new Thread(new Runnable() {
                @Override
                public void run() {

                     while (!Thread.currentThread().isInterrupted())
                     { System.out.println("interrupted?:"+Thread.currentThread().isInterrupted());
                         System.out.println(new Date()+ " I am ok");
                         try {
                             //Thread.sleep(1000);
                             queue.take();
                             System.out.println(new Date()+ " take queue");
                         } catch (InterruptedException e) {
                             e.printStackTrace();
                             //catch InterruptedException 后当前线程的中断状态就被变为正常
                             //标识中断状态，避免死循环
                             Thread.currentThread().interrupt();
                             Thread.currentThread().interrupt();
                             Thread.currentThread().interrupt();
                         }
                     }

                     System.out.println("interrupted?:"+Thread.currentThread().isInterrupted());
                     System.out.println(new Date()+" I am quit");
                }
            });

            t1.start();
            Thread.sleep(5000);

            t1.interrupt();

            Thread.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}
