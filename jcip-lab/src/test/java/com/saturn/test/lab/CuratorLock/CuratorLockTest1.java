package com.saturn.test.lab.CuratorLock;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.junit.Test;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;

public class CuratorLockTest1 {

    @Test
    public void test1() {
        String zookeeperConnectionString = "mred2:2181";
        String lockPath = "/curator_lock";
        long maxWait=2000;
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);

        CuratorFramework client = CuratorFrameworkFactory.newClient(zookeeperConnectionString, retryPolicy);
        client.start();

        InterProcessMutex lock = new InterProcessMutex(client, lockPath);


       long begin=System.nanoTime();
       int count=1000;
        try {
            for(int i=0;i<count;i++) {
                if (lock.acquire(maxWait, TimeUnit.MILLISECONDS)) {
                    try {
                        // do some work inside of the critical section here
                       // System.out.println(new Date() + " client1  do some work");
                    } finally {
                        lock.release();
                    }
                }
                else
                {
                    System.out.println("acquire failed");
                }

            }

            long costMs= (long)((System.nanoTime()-begin)/1e6);
            long tps=count*1000/costMs;
            if(costMs>0)
            {
                System.out.println("cost Ms:"+costMs+" tps:"+tps);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
