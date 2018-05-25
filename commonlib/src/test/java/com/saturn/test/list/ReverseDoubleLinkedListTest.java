package com.saturn.test.list;

import com.saturn.common.peak.DoubleLinkedList.DoubleLinkedList;
import com.saturn.common.peak.DoubleLinkedList.DoubleLinkedListHelper;
import com.saturn.common.peak.DoubleLinkedList.ListNode;
import org.junit.Assert;
import org.junit.Test;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class ReverseDoubleLinkedListTest {


    @Test
    public void test1() {

        int checkSize = 3;
        DoubleLinkedList list = DoubleLinkedListHelper.build(checkSize);

        System.out.println("Before reverse:");
        list.print();

        System.out.println("After reverse:");
        list.reverse();
        list.print();

    }


    @Test
    public void test2() {

        int checkSize = 6;

        DoubleLinkedList list1 = DoubleLinkedListHelper.build(checkSize);

        DoubleLinkedList list2 = DoubleLinkedListHelper.build(checkSize);
        list2.reverse();

        ListNode walker1 = list1.getHead();
        Queue<Integer> queue = new LinkedList<>();
        while (walker1 != null) {
            queue.add(walker1.getVal());
            walker1 = walker1.getNext();
        }

        ListNode walker2 = list2.getHead();
        Stack<Integer> stack = new Stack<>();
        while (walker2 != null) {

            stack.add(walker2.getVal());
            walker2 = walker2.getNext();
        }


        Assert.assertTrue(queue.size() == stack.size());

        while (queue.size() > 0) {
            int val1 = queue.remove();

            int val2 = stack.pop();

            Assert.assertTrue(val1 == val2);
        }

        System.out.println("All succeed");


    }
}
