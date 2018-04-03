package com.saturn.common.peak;


public class ReverseLinklist {

    class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    public static void main(String[] args) {
        ReverseLinklist job = new ReverseLinklist();
        job.start();
    }

    public void start() {
        ListNode n1 = new ListNode(1);
        ListNode n2 = new ListNode(2);
        ListNode n3 = new ListNode(3);

        n1.next = n2;
        n2.next = n3;
        n3.next = null;

        print(n1);
//        ListNode reversed = reverse(n1);
//        print(reversed);
        ListNode reversed = reverse_recursive(n1);
        print(reversed);

    }

    private void print(ListNode root) {
        while (root != null) {
            System.out.print(root.val);
            root = root.next;
            if (root != null) {
                System.out.print("->");
            }
        }

        System.out.println();
    }

    public ListNode reverse(ListNode root) {

        //1. 拆一个
        //2.反连一个
        ListNode before = null;
        ListNode current = root;

        while (current != null) {
            ListNode currentTemp = current.next;
            current.next = before;
            before = current;
            current = currentTemp;
        }

        return before;
    }

    public ListNode reverse_recursive(ListNode root) {

        if(root==null  ||root.next==null)
        {
            return root;
        }

        ListNode reversed=reverse_recursive(root.next);

        //关键
        root.next.next=root;
        root.next=null;
        //避免循环

        return reversed;

    }
}
