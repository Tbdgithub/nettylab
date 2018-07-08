package com.saturn.test.lab.jcip.CyclicBarrier;

import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierTest2 {

    static CyclicBarrier c = new CyclicBarrier(2, new A());

    public static void main(String[] args) {
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    c.await();

                } catch (Exception e) {

                }

                System.out.println(1);
            }
        }).start();

        try {
            c.await();
        } catch (Exception e) {

        }

        System.out.println(2);

        try {
            Thread.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 优先执行
     */
    static class A implements Runnable {

        @Override
        public void run() {
            System.out.println(3);
        }

    }

}