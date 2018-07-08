package com.saturn.test.lab.jcip.CyclicBarrier;

import org.junit.Test;
import java.util.Date;

public class JoinTest {

    @Test
    public void test1()
    {

        try {

            System.out.println(new Date()+" start");
            Thread t1=new Thread(new Runnable() {
                @Override
                public void run() {

                    for(int i=0;i<10;i++)
                    {
                        System.out.println(new Date()+" t1 execute "+i);
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {

                            e.printStackTrace();
                            break;
                        }
                    }
                }
            });

            t1.start();



            Thread tInter=new Thread(new Runnable() {
                @Override
                public void run() {

                    try {
                        System.out.println(new Date()+ " tInter begin");
                        Thread.sleep(5000);
                        System.out.println(new Date()+ " tInter interrupte t1");
                        t1.interrupt();
                    } catch (InterruptedException e) {
                        System.out.println(new Date()+" tInter:"+e.toString());
                        e.printStackTrace();
                    }
                }
            });

            tInter.start();

            System.out.println(new Date()+" t1 begin join");
            t1.join();

            System.out.println(new Date()+" t1 finished");

            Thread.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {

            System.out.println(new Date()+"  other ex:"+e.toString());
            e.printStackTrace();
        }

    }
}
