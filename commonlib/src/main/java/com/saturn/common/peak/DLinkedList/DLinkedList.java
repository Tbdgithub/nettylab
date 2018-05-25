package com.saturn.common.peak.DLinkedList;


import javax.annotation.concurrent.NotThreadSafe;

@NotThreadSafe
public class DLinkedList<E> {

    private Node<E> head;
    private String separator;

    public DLinkedList(String separator) {
        this.separator = separator;
    }

    public DLinkedList() {
        this.separator = "-";
    }

    public void addBefore(Node newNode) {
        if (newNode == null) {
            return;
        }

        newNode.setNext(head);
        if (head != null) {
            head.setPrev(newNode);
        }

        head = newNode;
    }

    public void reverse() {
        Node<E> prev = null;
        Node<E> current = head;

        while (current != null) {
            Node<E> next = current.getNext();
            current.setNext(prev);//
            current.setPrev(next);
            //move one step
            prev = current;
            current = next;
        }

        head = prev;
    }


    public String toString() {
        Node<E> walker = head;

        StringBuffer stringBuffer = new StringBuffer();
        while (walker != null) {

            stringBuffer.append(walker.getVal());

            if (walker.getNext() != null) {
                stringBuffer.append(separator);
            }

            walker = walker.getNext();
        }

        return stringBuffer.toString();
    }
}
