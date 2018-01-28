package com.saturn.common.sort.quicksort;

import com.google.gson.Gson;
import org.junit.Test;

/**
 * Created by lyz on 2018/1/28.
 */
public class QuickSorter_test {

    @Test
    public void test1()
    {
        try {
            QuickSorter<Integer> worker = new QuickSorter();

            Integer[] A = {2, 8, 7, 1, 3, 5, 6, 4};

            System.out.println(new Gson().toJson(A));
            worker.quickSort(A, 0, A.length - 1);
            System.out.println(new Gson().toJson(A));
        }catch (Exception ex)
        {
            ex.printStackTrace();
        }

    }

    @Test
    public void test2()
    {
        try {


            Integer[] A = {2, 8, 7, 1, 3, 5, 6, 4};

            System.out.println(new Gson().toJson(A));
            QuickSortHelper.quickSort(A, 0, A.length - 1);
            System.out.println(new Gson().toJson(A));
        }catch (Exception ex)
        {
            ex.printStackTrace();
        }

    }
}
