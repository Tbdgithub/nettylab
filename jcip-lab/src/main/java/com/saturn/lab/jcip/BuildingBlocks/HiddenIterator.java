package com.saturn.lab.jcip.BuildingBlocks;

import net.jcip.annotations.GuardedBy;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class HiddenIterator {

    @GuardedBy("this") private final Set<Integer> set = new HashSet<Integer>();

    public synchronized void add(Integer i) {
        set.add(i);
    }

    public synchronized void remove(Integer i) {
        set.remove(i);
    }

    public void addTenThings() {
        Random r = new Random();
        for (int i = 0; i < 10; i++) {
            add(r.nextInt());
        }
        System.out.println("DEBUG: added ten elements to " + set);
    }

    public static void main(String [] args)
    {
        AtomicInteger errCount=new AtomicInteger(0);
        try {
            HiddenIterator worker=new HiddenIterator();

            for(int i=0;i<5;i++) {
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            for(int i=0;i<2;i++) {
                                worker.addTenThings();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            errCount.incrementAndGet();
                        }
                    }
                });

                t.start();
            }

            Thread.sleep(20*1000);

            System.out.println("errCount:"+errCount);

            Thread.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}