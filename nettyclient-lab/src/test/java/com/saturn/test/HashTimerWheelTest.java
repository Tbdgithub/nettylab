package com.saturn.test;

import io.netty.util.HashedWheelTimer;
import io.netty.util.Timeout;
import io.netty.util.TimerTask;
import org.junit.Test;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class HashTimerWheelTest {


    @Test
    public void test1()
    {
        System.out.println(new Date()+" start");

        HashedWheelTimer timer=new HashedWheelTimer();
        timer.newTimeout(new TimerTask() {
            @Override
            public void run(Timeout timeout) throws Exception {

                System.out.println(new Date()+" time out");

            }
        },5000,TimeUnit.MILLISECONDS);

        timer.start();

        try {
            Thread.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
