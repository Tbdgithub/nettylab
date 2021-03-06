package com.saturn.test.list;

import com.saturn.common.peak.DLinkedList.DLinkedList;
import org.junit.Assert;
import org.junit.Test;

import java.util.ConcurrentModificationException;
import java.util.concurrent.CountDownLatch;

public class DLinkedListTest {

    final String separator = "->";


    @Test
    public void correctnessTest1() {

        int checkSize = 3;
        DLinkedList<Integer> list = getMockedList(checkSize);

        System.out.println("Before reverse:");


        System.out.println("head->tail:" + list.print());
        System.out.println("tail->head:" + list.printNegativeDirection());

        String original = "1->2->3";
        String actual = list.print();
        Assert.assertTrue(original.equals(actual));

        list.removeAtHead();

        actual = list.print();
        Assert.assertTrue("2->3".equals(actual));

        actual = list.printNegativeDirection();
        Assert.assertTrue("3->2".equals(actual));

        list.addBeforeHead(1);
        actual = list.print();
        Assert.assertTrue("1->2->3".equals(actual));

        actual = list.printNegativeDirection();
        Assert.assertTrue("3->2->1".equals(actual));

        list.removeAtTail();
        actual = list.print();
        Assert.assertTrue("1->2".equals(actual));

        actual = list.printNegativeDirection();
        Assert.assertTrue("2->1".equals(actual));

        list.addAfterTail(3);
        actual = list.print();
        Assert.assertTrue("1->2->3".equals(actual));

        actual = list.printNegativeDirection();
        Assert.assertTrue("3->2->1".equals(actual));

        System.out.println("After reverse:");
        list.reverse();
        actual = list.print();
        System.out.println("head->tail:"+actual);

        actual = list.printNegativeDirection();
        System.out.println("tail->head:"+actual);

        String expected = "3->2->1";
        actual = list.print();
        Assert.assertTrue(expected.equals(actual));

        System.out.println("finished");
    }

    @Test
    public void randomCorrectnessTest1() {

        int range = 100;

        for (int i = 0; i < range; i++) {
            DLinkedList<Integer> list = getMockedList(i);

            //  System.out.println("Before reverse:");
            // System.out.println(list);
            String original = correctAnswer(i, separator, true);

            String actual = list.print();
            Assert.assertTrue(original.equals(actual));
            // System.out.println("After reverse:");
            list.reverse();
            String expected = correctAnswer(i, separator, false);
            actual = list.print();
            Assert.assertTrue(expected.equals(actual));
            //System.out.println(list);
        }

        System.out.println("finished");

    }

    private String correctAnswer(int size, String separator, boolean asc) {
        StringBuilder stringBuilder = new StringBuilder(size);

        for (int i = 0; i < size; i++) {
            if (stringBuilder.length() > 0) {
                stringBuilder.append(separator);
            }

            if (asc) {
                stringBuilder.append(i + 1);
            } else {
                stringBuilder.append(size - i);
            }
        }

        return stringBuilder.toString();
    }

    @Test
    public void performanceTest1() {

        int checkSize = 3;
        long totalCount = 10000000;
        long costNano = 0;
        final String expected = "3->2->1";
        for (int i = 0; i < totalCount; i++) {
            DLinkedList<Integer> list = getMockedList(checkSize);
            long begin = System.nanoTime();
            list.reverse();

            costNano += System.nanoTime() - begin;
            String actual = list.print();
            Assert.assertTrue(expected.equals(actual));
        }

        long costMilliseconds = (long) (costNano / 1e6);
        long tps = 0;
        if (costMilliseconds > 0) {
            tps = (long) (totalCount * 1e3 / costMilliseconds);
        }

        System.out.println("cost milliseconds:" + costMilliseconds + " tps:" + tps);

    }

    @Test
    public void multiThreadTest1() {

        int checkSize = 10;
        int runCount = 1000;
        int threadCount = 1;

        DLinkedList<Integer> list = getMockedList(checkSize);
        final boolean[] hasEx = new boolean[1];
        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            Thread t1 = new Thread(new Runnable() {
                @Override
                public void run() {

                    try {
                        for (int i = 0; i < runCount; i++) {

                            list.print();
                            Thread.sleep(1);
                        }

                    } catch (InterruptedException ex) {

                    } catch (ConcurrentModificationException ex) {
                        hasEx[0] = true;
                    } finally {
                        latch.countDown();
                    }

                }
            });

            t1.start();
        }

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    for (int i = 0; i < runCount; i++) {
                        list.addBeforeHead(-1);
                        list.removeAtHead();

                        list.addAfterTail(5);
                        list.removeAtTail();

                        Thread.sleep(1);

                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        t2.start();


        try {
            latch.await();
            System.out.println("finished");
        } catch (InterruptedException ex1) {
            ex1.printStackTrace();
        }

        Assert.assertTrue(hasEx[0]);
    }

    public DLinkedList getMockedList(int size) {

        DLinkedList<Integer> list = new DLinkedList<>("->");
        for (int i = 0; i < size; i++) {

            list.addBeforeHead(size - i);
        }

        return list;

    }

}
