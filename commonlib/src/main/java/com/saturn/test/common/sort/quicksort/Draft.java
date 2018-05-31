package com.saturn.test.common.sort.quicksort;

public class Draft {

    public static void process(int[] a) {

        processInner(a, 0, a.length - 1);
    }

    public static   void print(int[] A)
    {
        for(int item :A)
        {
            System.out.print(item+" ");
        }

        System.out.println();
    }

    private static int partition(int[] a, int low, int high) {


        int pivotValue = a[low];
        while (low < high) {



            while (low < high &&
                    a[high] >= pivotValue) {
                --high;
            }

            a[low] = a[high];

            while (low < high &&
                    a[low] <= pivotValue) {
                ++low;
            }

            a[high] = a[low];
        }

        a[low]=pivotValue;

        return low;
    }

    private  static void processInner(int[] a, int low, int high) {

        if(low<high) {
            int partition = partition(a, low, high);
            processInner(a, low, partition - 1);
            processInner(a, partition + 1, high);
        }
    }


}
