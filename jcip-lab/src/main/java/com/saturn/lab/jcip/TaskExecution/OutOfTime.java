package com.saturn.lab.jcip.TaskExecution;


import java.util.Timer;
import java.util.TimerTask;

public class OutOfTime {
    public static void main(String[] args) throws Exception {
        System.out.println("start:"+new java.util.Date());
        Timer timer = new Timer();
        timer.schedule(new ThrowTask(), 10*1000);
        SECONDS.sleep(5);



        timer.schedule(new ThrowTask(), 10*1000);
        SECONDS.sleep(10000);
    }

    static class ThrowTask extends TimerTask {
        public void run() {

            //throw new RuntimeException();
            System.out.println(new java.util.Date());
        }
    }

   static class SECONDS
    {

        public static   void sleep(int seconds)
        {
            try {
                Thread.sleep(seconds*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

