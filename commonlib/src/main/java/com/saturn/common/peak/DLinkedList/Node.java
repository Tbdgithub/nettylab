package com.saturn.common.peak.DLinkedList;


public class Node<E> {

    private Node<E> next;
    private Node<E> prev;
    private E val;

    public Node(E data)
    {
        this.val=data;

    }

    public Node<E> getNext() {
        return next;
    }

    public void setNext(Node<E> next) {
        this.next = next;
    }

    public Node<E> getPrev() {
        return prev;
    }

    public void setPrev(Node<E> prev) {
        this.prev = prev;
    }

    public E getVal() {
        return val;
    }

    public void setVal(E val) {
        this.val = val;
    }
}
