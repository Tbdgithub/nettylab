package com.saturn.test.list;

import com.saturn.common.peak.DLinkedList.DLinkedList;
import com.saturn.common.peak.DLinkedList.Node;

import org.junit.Assert;
import org.junit.Test;

public class DLinkedListTest {

    @Test
    public void correctnessTest1() {

        int checkSize = 3;
        DLinkedList<Integer> list = getMockedList(checkSize);

        System.out.println("Before reverse:");

        System.out.println(list);

        String original = "1->2->3";
        Assert.assertTrue(original.equals(list.toString()));
        System.out.println("After reverse:");
        list.reverse();

        System.out.println(list);

        String expected = "3->2->1";
        Assert.assertTrue(expected.equals(list.toString()));
    }

    @Test
    public void randomCorrectnessTest1() {

        int range = 1000;
        final String separator = "->";
        for (int i = 0; i < range; i++) {
            DLinkedList<Integer> list = getMockedList(i);

            //  System.out.println("Before reverse:");
            // System.out.println(list);
            String original = correctAnswer(i, separator, true);

            Assert.assertTrue(original.equals(list.toString()));
            // System.out.println("After reverse:");
            list.reverse();
            String expected = correctAnswer(i, separator, false);
            Assert.assertTrue(expected.equals(list.toString()));
            //System.out.println(list);
        }

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
            Assert.assertTrue(expected.equals(list.toString()));
        }

        long costMilliseconds = (long) (costNano / 1e6);
        long tps = (long) (totalCount * 1e3 / costMilliseconds);

        System.out.println("cost milliseconds:" + costMilliseconds + " tps:" + tps);

    }

    public void multiThreadTest1() {

    }

    public DLinkedList getMockedList(int size) {

        DLinkedList<Integer> list = new DLinkedList<>("->");
        for (int i = 0; i < size; i++) {

            list.addBefore(new Node(size - i));
        }

        return list;

    }

}
