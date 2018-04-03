package com.saturn.common.peak.AddTwoNumbers;

import java.util.List;

public class AddTwoNumbers {

    public class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

//    Input: (2 -> 4 -> 3) + (5 -> 6 -> 4)
//    Output: 7 -> 0 -> 8
//    Explanation: 342 + 465 = 807.

//    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
//
//
//        //ListNode r1 = reverse(l1);
//        // ListNode r2 = reverse(l2);
//        ListNode added = add(l1, l2);
//        //   ListNode result = reverse(added);
//
//        return added;
//    }

    public void start() {
        //ListNode node1
        //1.
        ListNode x1 = new ListNode(2);
        ListNode x2 = new ListNode(4);
        ListNode x3 = new ListNode(3);

        x1.next = x2;
        x2.next = x3;

        ListNode y1 = new ListNode(5);
        ListNode y2 = new ListNode(6);
        ListNode y3 = new ListNode(4);

        y1.next = y2;
        y2.next = y3;

        print(x1);
        print(y1);
        System.out.println("after added:");
        // ListNode r1 = reverse(x1);
        //  print(r1);

        //   ListNode r2 = reverse(y1);
        // print(r2);

        //  System.out.println("after add");
        //  ListNode sum = add(r1, r2);
        // ListNode reversedSum = reverse(sum);
        //  print(reversedSum);


        ListNode sum = addTwoNumbers(x1, y1);
        print(sum);
        sum = addTwoNumbers_good(x1, y1);
        print(sum);

        //2.

    }

    /**
     * * simple
     */
    public ListNode addTwoNumbers(ListNode x, ListNode y) {
        ListNode result = null;

        ListNode m = x;
        ListNode n = y;

        int next = 0;
        int current = 0;
        ListNode tail = null;

        while (m != null || n != null) {
            //
            int mval = 0;
            int nval = 0;
            if (m != null) {
                mval = m.val;
            } else {
                mval = 0;
            }

            if (n != null) {
                nval = n.val;
            } else {
                nval = 0;
            }

            int sum = mval + nval + next;

            //关键:next 为进位
            next = sum / 10;
            current = sum % 10;
            ListNode temp = new ListNode(current);

            if (result == null) {
                //根节点
                result = temp;
                tail = result;
            } else {
                tail.next = temp;
                tail = temp;
            }

            if (m != null) {
                m = m.next;
            }

            if (n != null) {
                n = n.next;
            }
        }

        if (next > 0) {
            ListNode temp = new ListNode(next);
            tail.next = temp;
        }

        return result;
    }

    public ListNode addTwoNumbers_good(ListNode l1, ListNode l2) {
        ListNode dummyHead = new ListNode(0); //亮点,dummyHead
        ListNode p = l1, q = l2, curr = dummyHead;

        int carry = 0; //进位
        while (p != null || q != null)

        {
            int x = (p != null) ? p.val : 0;
            //节点为空时，值为0
            int y = (q != null) ? q.val : 0;
            //节点为空时，值为空
            int sum = carry + x + y; //结果

            carry = sum / 10; //下一步进位
            curr.next = new ListNode(sum % 10);//结果用新节点存; 当前值为 sum%10
            curr = curr.next;// 当前节点后移 curr 一直在新链表的尾部

            //每步后移一个
            if (p != null) {
                p = p.next;
            }

            //每步后移一个
            if (q != null) {
                q = q.next;
            }

        }

        //有多的进位，再加一个节点
        if (carry > 0) {
            curr.next = new ListNode(carry);
        }

        return dummyHead.next; //dummyHead.next为头
    }

    public void print(ListNode root) {
        ListNode temp = root;
        while (temp != null) {
            System.out.print(temp.val);
            if (temp.next != null) {
                System.out.print("->");
            }

            temp = temp.next;
        }

        System.out.println();
    }



    public static void main(String[] args) {

        AddTwoNumbers addTwoNumbers = new AddTwoNumbers();
        addTwoNumbers.start();


    }

}
