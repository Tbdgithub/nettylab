package com.saturn.test.lab.GoodLock;

import com.saturn.lab.jcip.recipes.lock.LockListener;
import com.saturn.lab.jcip.recipes.lock.WriteLock;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.junit.Test;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.LongAdder;

public class GoodLockTest2 {

    @Test
    public void test2() {
        try {
            String connStr = "mred2:2181";
            String rootDir = "/goodlockroot";
            int sessionTimeout = 60 * 1000;//30s
            CountDownLatch connLatch = new CountDownLatch(1);
            ConnWatcher connWatcher = new ConnWatcher(connLatch);

            ZooKeeper zk = new ZooKeeper(connStr, sessionTimeout, connWatcher);
            connLatch.await();

            System.out.println("connected");
            WriteLock writeLock = new WriteLock(zk, rootDir, null);
            writeLock.setLockListener(new LockListener() {
                @Override
                public void lockAcquired() {
                  //  System.out.println("listener:lockAcquired");
                }

                @Override
                public void lockReleased()
                {
                   // System.out.println("listener:lockReleased");
                }
            });

            LongAdder longAdder=new LongAdder();
            Random random=new Random();
            for(int i=0;i<10000;i++) {
                boolean succ = writeLock.lock();

                try {
                    if(succ)
                    {
                        longAdder.increment();
                    }
                    System.out.println("get lock:" + succ+" i:"+i);
                    Thread.sleep(random.nextInt(100));

                } finally {
                    if (writeLock != null) {
                        writeLock.unlock();
                    }
                }

                Thread.sleep(1);
            }

            System.out.println("Get lock succ count:"+longAdder.longValue());
            Thread.sleep(Integer.MAX_VALUE);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private class ConnWatcher implements Watcher {

        private CountDownLatch latch;

        public void process(WatchedEvent event) {
            // lets either become the leader or watch the new/updated node
            System.out.println("Watcher fired on path: " + event.getPath() + " state: " +
                    event.getState() + " type " + event.getType());
            latch.countDown();

        }


        public ConnWatcher(CountDownLatch latch) {
            this.latch = latch;
        }
    }

}
