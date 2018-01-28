package com.saturn.common.sort.quicksort;

/**
 * Created by lyz on 2018/1/28.
 */
public class QuickSorter {

    public void quickSort(int[] A, int p, int r) {

        if (p < r) {
            int q = partition(A, p, r);
            quickSort(A, p, q - 1);
            quickSort(A, q + 1, r);

        }

    }

    public int partition(int A[], int p, int r) {
        int x = A[r];
        int i = p - 1;
        for (int j = p; j < r; j++) {
            if (A[j] <= x) {
                i = i + 1;
                int temp = A[i];
                A[i] = A[j];
                A[j] = temp;
            }

        }

        int temp = A[i + 1];
        A[i + 1] = A[r];
        A[r] = temp;

        return i + 1;
    }

}
