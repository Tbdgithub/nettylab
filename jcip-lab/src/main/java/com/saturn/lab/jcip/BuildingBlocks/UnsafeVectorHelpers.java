package com.saturn.lab.jcip.BuildingBlocks;

import java.util.Vector;

/**
 * vector 是线程安全的，
 * 但get ,deletelast 两个操作必须是原子的，才是线程安全的
 */
public class UnsafeVectorHelpers {
    public static Object getLast(Vector list) {
        int lastIndex = list.size() - 1;
        return list.get(lastIndex);
    }

    public static void deleteLast(Vector list) {
        int lastIndex = list.size() - 1;
        list.remove(lastIndex);
    }
}