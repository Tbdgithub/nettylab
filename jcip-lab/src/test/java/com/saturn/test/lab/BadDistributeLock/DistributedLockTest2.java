package com.saturn.test.lab.BadDistributeLock;

import com.saturn.lab.jcip.zk.DistributedLock;
import org.junit.Test;

public class DistributedLockTest2 {

    @Test
    public void test2() {

        DistributedLock lock = new DistributedLock("mred2:2181", "lock");
        System.out.println("begin get lock2");
        lock.lock();
//共享资源
        try {
            System.out.println("got lock 2");
            try {
                Thread.sleep(1000);
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
