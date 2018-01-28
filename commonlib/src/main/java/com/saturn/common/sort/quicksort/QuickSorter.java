package com.saturn.common.sort.quicksort;

/**
 * Created by lyz on 2018/1/28.
 */
public class QuickSorter<T extends Comparable> {


    public void quickSort(T[] A, int p, int r) {

        if (p < r) {
            int q = partition(A, p, r);
            quickSort(A, p, q - 1);
            quickSort(A, q + 1, r);

        }

    }

    public int partition(T A[], int p, int r) {
        T x = A[r];
        int i = p - 1;
        for (int j = p; j < r; j++) {
            if (A[j].compareTo(x) == 0 || A[j].compareTo(x) < 0) {
                i = i + 1;
                T temp = A[i];
                A[i] = A[j];
                A[j] = temp;
            }

        }

        T temp = A[i + 1];
        A[i + 1] = A[r];
        A[r] = temp;

        return i + 1;
    }

}
