package com.saturn.test.lab.election.leaderlatch;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderLatch;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class LeaderLatchTest1 {


    @Test
    public void test1() {
        try {

            String PATH = "/leader_latch";
            int COUNT = 3;
            String zookeeperConnectionString = "localhost:2181";
            RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);

            CuratorFramework client = CuratorFrameworkFactory.newClient(zookeeperConnectionString, retryPolicy);
            client.start();


            List<LeaderLatch> list = new ArrayList<>();

            for (int i = 1; i <= COUNT; i++) {
                LeaderLatch example = new LeaderLatch(client, PATH, "Client #" + i);
                example.start();
                list.add(example);
            }

            TimeUnit.SECONDS.sleep(2);

            LeaderLatch leader = null;
            for (LeaderLatch ll : list) {
                if (ll.hasLeadership()) {
                    leader = ll;
                }
                System.out.println(ll.getId() + "\t" + ll.hasLeadership());
            }

            TimeUnit.SECONDS.sleep(2);

            list.remove(leader);
            leader.close();

            TimeUnit.SECONDS.sleep(2);

            System.out.println("========================");

            for (LeaderLatch ll : list) {
                System.out.println(ll.getId() + "\t" + ll.hasLeadership());
            }

            for (LeaderLatch ll : list) {
                ll.close();
            }

            client.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
