package com.saturn.common.sort.quicksort;

import com.google.gson.Gson;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

/**
 * Created by lyz on 2018/1/28.
 */
public class QuickSorter_test {

    @Test
    public void test1() {
        try {
            QuickSorter<Integer> worker = new QuickSorter();

            Integer[] A = {2, 8, 7, 1, 3, 5, 6, 4};

            System.out.println(new Gson().toJson(A));
            worker.quickSort(A, 0, A.length - 1);
            System.out.println(new Gson().toJson(A));
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @Test
    public void test2() {
        try {
            Integer[] A = {2, 8, 7, 1, 3, 5, 6, 4};

            System.out.println(new Gson().toJson(A));
            QuickSortHelper.quickSort(A, 0, A.length - 1);
            System.out.println(new Gson().toJson(A));
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }


    @Test
    public void test3() {
        try {

            Draft draft = new Draft();
            Random random = new Random(100);

            int M = 1;
            int N = 10;
            for (int i = 0; i < M; i++) {

                int[] a = new int[N];
                int[] b = new int[N];
                for (int j = 0; j < a.length; j++) {
                    int temp=random.nextInt(10);
                    a[j] = temp;
                    b[j]=temp;
                }

                //draft.process(a);

                YanWeiMingSimple.QuickSort(a);
                //YanWeiMingSimple.QuickSort(b);
                draft.process(b);

                if (!valueEqual(a, b)) {
                    System.err.println("not equal");
                    draft.print(a);
                    draft.print(b);
                    return;
                }

            }

            System.out.println("all equal");

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private boolean valueEqual(int[] a, int[] b) {
        if (a.length != b.length) {
            return false;
        }

        for (int i = 0; i < a.length; i++) {
            if (a[i] != b[i]) {
                return false;
            }
        }

        return true;
    }
}
