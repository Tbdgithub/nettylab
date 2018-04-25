package com.saturn.common.peak.DoubleLinkedList;

public class DoubleLinkedList {

    private ListNode head;


    private String pathSeparator = ">";

    public ListNode getHead() {
        return head;
    }

    public void addBefore(ListNode newNode) {
        if (newNode == null) {
            return;
        }

        newNode.setNext(head);
        if (head != null) {
            head.setParent(newNode);
        }

        head = newNode;


    }

    public void reverse()
    {

        ListNode prev=null;
        ListNode current=head;

        while (current!=null)
        {
            ListNode next=current.getNext();
            current.setNext(prev);//
            current.setParent(next);

            //move one step
            prev=current;
            current=next;

        }

        head=prev;

    }

    public void print() {
        ListNode walker = head;

        while (walker != null) {
            System.out.print(walker.getVal());

            if (walker.getNext() != null) {
                System.out.print(pathSeparator);
            }
            walker = walker.getNext();
        }

        System.out.println();
    }

    public void printFromTail()
    {
        ListNode walker = head;

        while (walker!=null && walker.getNext()!=null) {

            walker = walker.getNext();
        }


        while (walker != null) {
            System.out.print(walker.getVal());

            if (walker.getParent() != null) {
                System.out.print(pathSeparator);
            }

            walker = walker.getParent();
        }

        System.out.println();

    }


}
