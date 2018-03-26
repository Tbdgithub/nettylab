package com.saturn.common.peak;

public class PeakFinder {

    public int getPeak1(int[] a) {
        for (int i = 0; i < a.length; i++) {
            if ((i - 1) > 0 && (a[i] >= a[i - 1])
                    && (a[i] >= a[i + 1])
                    && i + 1 < a.length
                    ) {
                return i;
            }
        }
        //
        return -1;
    }

    public int getPeakBin(int[] a) {
        int left = 0;
        int right = a.length - 1;

        while (left < right) {
            int middle = (left + right) / 2;
            if (a[middle] < a[middle + 1]) {
                //middle+1 一定不越界
                //右分
                left = middle + 1; //middle 一定不是目标
                //middle+1有可能是
            } else if (a[middle] == a[middle + 1]) {

                //无法判断,左分，右分均可
                right = middle;
                //left = middle + 1;

            } else {
                //左分
                right = middle; // middle +1 一定不是目标
                //middle 有可能是

            }
        }

         //left ==right 时
        return left;
    }


}
