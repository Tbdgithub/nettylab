package com.saturn.test.lab.BadDistributeLock;

import com.saturn.lab.jcip.zk.DistributedLock;
import org.junit.Test;

public class DistributedLockTest1 {

    @Test
    public void test1() {

        DistributedLock lock = new DistributedLock("mred2:2181", "lock");
        System.out.println("begin get lock1");
        lock.lock();
//共享资源
        try {
            System.out.println("got lock1");
            try {
                Thread.sleep(15000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        } finally {


            if (lock != null) {
                lock.unlock();
            }
        }

    }
}
