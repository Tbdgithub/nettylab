package com.saturn.test.lab.election.leaderlatch;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderLatch;
import org.apache.curator.framework.recipes.leader.LeaderLatchListener;
import org.apache.curator.framework.recipes.leader.Participant;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.framework.state.ConnectionStateListener;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.io.EOFException;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @Author: john.yi
 * @Date: 2018/8/19 下午4:27
 */
public class Latch2 {

    String zookeeperConnectionString = "localhost:2181";
    RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
    CuratorFramework client = CuratorFrameworkFactory.newClient(zookeeperConnectionString, retryPolicy);


    public static void main(String[] args) {
        Latch2 latch2 = new Latch2();
        latch2.start();
    }


    public void start() {



        client.getConnectionStateListenable().addListener(new ConnectionStateListener() {
            @Override
            public void stateChanged(CuratorFramework client, ConnectionState state) {
                if (state == ConnectionState.LOST) {
                    //连接丢失
                    System.out.println("lost session with zookeeper");
                } else if (state == ConnectionState.CONNECTED) {
                    //连接新建
                    System.out.println("connected with zookeeper");

                } else if (state == ConnectionState.RECONNECTED) {
                    System.out.println("reconnected with zookeeper");

                    //连接重连

//                    for(ZkStateListener s:stateListeners){
//                        s.reconnected();
//                    }


                }
            }
        });

        client.start();


        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {


                try {

                    Thread.sleep(5000);


                    String id = "client#" + Thread.currentThread().getName();
                    LeaderLatch leaderLatch = new LeaderLatch(client, "/LeaderLatch2", id);
                    setLeaderLatch(leaderLatch);


                    Thread.sleep(3000);
                    printParticipants(leaderLatch);

                    awaitByLeaderLatch(leaderLatch);


                    Thread.sleep(10000);

                    quitLeadership(leaderLatch);

                    Thread.sleep(3000);

                    printParticipants(leaderLatch);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        t1.setName("tname1");
        t1.start();

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {


                try {

                    Thread.sleep(3000);

                    String id = "client#" + Thread.currentThread().getName();
                    LeaderLatch leaderLatch = new LeaderLatch(client, "/LeaderLatch2", id);
                    setLeaderLatch(leaderLatch);

                    Thread.sleep(3000);
                    printParticipants(leaderLatch);

                    awaitByLeaderLatch(leaderLatch);


                    Thread.sleep(10000);


                    quitLeadership(leaderLatch);


                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        t2.setName("tname2");
        t2.start();


        try

        {
            t1.join();

            System.out.println(t1.getName() + " finished");
            t2.join();
            System.out.println(t2.getName() + " finished");

            System.out.println("all thread finished");
        } catch (
                InterruptedException e)

        {
            e.printStackTrace();
        }

    }

    private void printParticipants(LeaderLatch leaderLatch) {
        try {

            System.out.println(new Date() + " " + leaderLatch.getId() + " participant size:" + leaderLatch.getParticipants().size());
            for (Participant participant : leaderLatch.getParticipants()) {
                System.out.println(new Date() + " " + leaderLatch.getId() + " participant:" + participant.getId());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     *  Leader Latch(群首闩)
     *  isLeader 中的方法会在实例被选为主节点后被执行, 而notLeader中的不会被执行
     *  如果主节点被失效, 会进行重新选主
     * */
    public void setLeaderLatch(LeaderLatch leaderLatch) {
        try {


            //System.out.println(new Date() + " " + leaderLatch.getId() + " begin create leader latch");

            LeaderLatchListener leaderLatchListener = new LeaderLatchListener() {
                @Override
                public void isLeader() {
                    System.out.println(new Date() + " [LeaderLatch]我是主节点, id= " + leaderLatch.getId());

                }

                @Override
                public void notLeader() {
                    System.out.println(new Date() + " [LeaderLatch]我不是主节点, id=" + leaderLatch.getId());
                }
            };
            leaderLatch.addListener(leaderLatchListener);

            leaderLatch.start();

            if (hasLeadershipByLeaderLatch(leaderLatch)) {
                System.out.println(new Date() + " " + leaderLatch.getId() + " has leadership");
            }
            // System.out.println(new Date() + " " + leaderLatch.getId() + " started leader latch");
        } catch (Exception e) {
            System.err.println("c创建LeaderLatch失败 " + Thread.currentThread().getName());
        }
    }

    /*
     *   判断实例是否是主节点
     * */
    public boolean hasLeadershipByLeaderLatch(LeaderLatch leaderLatch) {


        boolean result = leaderLatch.hasLeadership();

        //  System.out.println("I has leadership:" + result + " " + Thread.currentThread().getName());
        return result;
    }


    public void quitLeadership(LeaderLatch leaderLatch) {
        try {


            //System.out.println(new Date() + " " + Thread.currentThread().getName() + " begin quit leadership");
            leaderLatch.close();

            System.out.println(new Date() + " " + Thread.currentThread().getName() + " quited leadership");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
     *   阻塞直到获得领导权
     * */
    public void awaitByLeaderLatch(LeaderLatch leaderLatch) {
        try {

            if (!hasLeadershipByLeaderLatch(leaderLatch)) {
                // System.out.println(new Date()+" "+Thread.currentThread().getName() + " wait for leaderLatch");
                leaderLatch.await();

                System.out.println(new Date() + " " + Thread.currentThread().getName() + " got leaderLatch");
            }

        } catch (InterruptedException | EOFException e) {
            e.printStackTrace();
        }
    }

    /*
     *   尝试获得领导权并超时
     * */
    public boolean awaitByLeaderLatch(LeaderLatch leaderLatch, long timeout, TimeUnit unit) {
        boolean hasLeadership = false;
        try {
            hasLeadership = leaderLatch.await(timeout, unit);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return hasLeadership;
    }
}
