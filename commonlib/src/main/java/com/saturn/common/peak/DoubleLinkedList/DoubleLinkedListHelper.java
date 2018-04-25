package com.saturn.common.peak.DoubleLinkedList;

public class DoubleLinkedListHelper {

    public static DoubleLinkedList build(int size)
    {

        DoubleLinkedList list=new DoubleLinkedList();
        for(int i=0;i<size;i++)
        {

            list.addBefore(new ListNode(size-i));
        }

        return list;

    }



}
