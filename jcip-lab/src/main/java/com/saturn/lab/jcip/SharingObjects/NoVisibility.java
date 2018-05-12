package com.saturn.lab.jcip.SharingObjects;


/**
 * 为什么没能验证？和机器有关？？？
 */
public class NoVisibility {
//    private static boolean ready;

//    private static int number;

    private boolean ready;
    private int number;
//
//    private static class ReaderThread extends Thread {
//        public void run() {
//            while (!ready) {
//                Thread.yield();
//            }
//            System.out.println(number);
//        }
//    }

    private class ReaderThread extends Thread {
        public void run() {
            while (!ready) {
                Thread.yield();
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
            }
            System.out.println(number);
        }
    }

    public void start() {
        try {
            new ReaderThread().start();

            Thread.sleep(1000);
            number = 43;
            System.out.println("set number:");
            ready = true;
            System.out.println("set ready:");

            Thread.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {

        NoVisibility noVisibility = new NoVisibility();
        noVisibility.start();
        // new ReaderThread().start();
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

//        number = 43;
//        System.out.println("set number:");
//        ready = true;
//        System.out.println("set ready:");


//        Thread t1=new Thread(new Runnable() {
//            @Override
//            public void run() {
//
//                while (true) {
//
//                    System.out.println("number"+number);
//                    if(ready)
//                    {
//                        System.out.println("i am ready:"+ready);
//                        break;
//                    }
//                    else {
//                        System.out.println("ready:"+ready);
//                        Thread.yield();
//                    }
//                }
//
//
//            }
//        });


//        Thread t2=new Thread(new Runnable() {
//            @Override
//            public void run() {
//
//                try {
//                   // Thread.sleep(1000);
//
//                    number=43;
//                    ready=true;
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//
//        t2.start();
//
//        t1.start();


    }
}