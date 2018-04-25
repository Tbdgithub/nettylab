package com.saturn.common.peak.DoubleLinkedList;

public class ListNode {

    private ListNode next;
    private ListNode parent;
    private int val;

    public ListNode(int newVal) {
        this.val = newVal;
    }

    public int getVal() {
        return val;
    }

    public void setVal(int val) {
        this.val = val;
    }

    public ListNode getNext() {
        return next;
    }

    public void setNext(ListNode next) {
        this.next = next;
    }

    public ListNode getParent() {
        return parent;
    }

    public void setParent(ListNode parent) {
        this.parent = parent;
    }
}
