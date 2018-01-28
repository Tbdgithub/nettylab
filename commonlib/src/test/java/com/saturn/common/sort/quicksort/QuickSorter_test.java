package com.saturn.common.sort.quicksort;

import org.junit.Test;

/**
 * Created by lyz on 2018/1/28.
 */
public class QuickSorter_test {

    @Test
    public void test1()
    {
        try {
            QuickSorter worker = new QuickSorter();

            int[] A = {2, 8, 7, 1, 3, 5, 6, 4};

            //System.out.println(new Gson());
            worker.quickSort(A, 0, A.length - 1);
        }catch (Exception ex)
        {
            ex.printStackTrace();
        }

    }
}
