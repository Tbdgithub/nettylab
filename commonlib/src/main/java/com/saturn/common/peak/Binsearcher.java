package com.saturn.common.peak;

public class Binsearcher {

    public int find(int[] a, int key) {
        int left = 0;
        int right = a.length - 1;

        while (left < right) {
            int middle = (left + right) / 2;

            if (a[middle] == key) {
                return middle;

            } else if (key < a[middle]) {
                right = middle - 1;
            } else {
                left = middle + 1;
            }

            System.out.println("middle:" + middle + " [" + left + "," + right + "]");
        }

        //left ==right 时
        return left;
    }
}
