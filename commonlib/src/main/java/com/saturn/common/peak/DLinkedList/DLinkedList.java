package com.saturn.common.peak.DLinkedList;

import javax.annotation.concurrent.NotThreadSafe;
import java.util.*;


@NotThreadSafe
public class DLinkedList<E> implements Iterable<E> {

    private Node<E> head;
    private Node<E> tail;

    private String separator;
    transient int modCount = 0;
    transient int size = 0;

    public DLinkedList(String separator) {
        this.separator = separator;
    }

    public DLinkedList() {
        this.separator = "-";
    }

    @Override
    public Iterator<E> iterator() {
        return new Itr();
    }


    private class Itr implements Iterator<E> {

        Node<E> walker = head;
        int expectedModCount = modCount;

        @Override
        public boolean hasNext() {
            return walker != null;
        }

        @Override
        public E next() {

            if (modCount != expectedModCount) {
                throw new ConcurrentModificationException();
            }

            E current = walker.getVal();
            walker = walker.getNext();
            return current;
        }

    }

    public E removeAtHead() {
        final Node<E> current = head;

        if (current == null) {
            throw new NoSuchElementException();
        }

        E currentData = current.getVal();
        final Node<E> next = current.getNext();

        //current.prev 本就是null
        current.val = null;
        current.next = null;


        if (next == null) {
            //empty
            tail = null;
        } else {
            next.prev = null;
        }
        head = next;
        size--;
        modCount--;

        return currentData;
    }

    public E removeAtTail() {
        final Node<E> current = tail;

        if (current == null) {
            throw new NoSuchElementException();
        }

        E currentData = current.getVal();
        final Node<E> prev = current.getPrev();

        current.val = null;
        current.next = null;


        if (prev == null) {
            //empty
            head = null;
        } else {
            prev.next = null;
        }
        tail = prev;
        size--;
        modCount--;

        return currentData;
    }

    public void addBeforeHead(E newVal) {

        if (newVal == null) {
            return;
        }

        Node<E> newNode = new Node<>(newVal);
        newNode.setNext(head);


        if (head != null) {
            head.setPrev(newNode);
        } else {
            tail = newNode;
        }
        head = newNode;
        //
        modCount++;
        size++;
    }

    public void addAfterTail(E val) {

        if (val == null) {
            return;
        }

        Node<E> addedNode = new Node<>(val);

        addedNode.setPrev(tail);
        if (tail != null) {
            tail.setNext(addedNode);
        } else {
            head = addedNode;
        }

        tail = addedNode;
        //
        modCount++;
        size++;
    }

    private Node<E> findNode(E val) {
        if (val == null) {
            return null;
        }

        Node<E> current = head;

        while (current != null) {
            if (current.getVal().equals(val)) {
                return current;
            }

            current = current.getNext();
        }

        return null;
    }

    public boolean find(E val) {

        Node<E> node = findNode(val);
        if (node != null) {
            return true;
        } else {
            return false;
        }
    }


    public void reverse() {

        Node<E> prev = null;
        Node<E> current = head;
        Node<E> headTemp=head;

        while (current != null) {
            Node<E> next = current.getNext();
            current.setNext(prev);//
            current.setPrev(next);
            //move one step
            prev = current;
            current = next;
        }

        head = prev;
        tail=headTemp;

    }


    /**
     * print items from head to tail
     *
     * @return String
     */
    public String print() {

        StringBuilder stringBuilder = new StringBuilder();

        for (E item : this) {
            if (stringBuilder.length() > 0) {
                stringBuilder.append(separator);
            }

            stringBuilder.append(item);
        }

        return stringBuilder.toString();
    }

    public String printNegativeDirection() {
        Node<E> walker = tail;

        StringBuilder stringBuilder = new StringBuilder(this.size);

        while (walker != null) {

            if (stringBuilder.length() > 0) {
                stringBuilder.append(separator);
            }

            stringBuilder.append(walker.getVal());
            walker = walker.getPrev();
        }

        return stringBuilder.toString();
    }

    private static class Node<E> {

        private Node<E> next;
        private Node<E> prev;
        private E val;

        public Node(E data) {
            this.val = data;
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
}

