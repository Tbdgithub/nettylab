package com.saturn.test.common.sort.quicksort;

public class YanWeiMingSimple {


    public static void QuickSort(int[] A) {
        /* 对顺序表L作快速排序。算法10.8 */
        QSort(A, 0, A.length - 1);
    }

    public static void QSort(int[] A, int low, int high)

    {
        /* 对顺序表L中的子序列L.r[low..high]作快速排序。算法10.7 */

        int pivotloc;

        if (low < high) { /* 长度大于1 */
            pivotloc = Partition(A, low, high); /* 将L.r[low..high]一分为二 */
            //count++;
            //System.out.println(String.format("第%s轮:",count));
            // print(*L,1);
            //  System.out.println("pivot index:"+pivotloc+" value:"+A[pivotloc]);
            // print(A);

            QSort(A, low, pivotloc - 1); /* 对低子表递归排序，pivotloc是枢轴位置 */
            QSort(A, pivotloc + 1, high); /* 对高子表递归排序 */
        } else {
            // System.out.println("do nothing !!!");
        }

    }


    public static int Partition(int[] A, int low, int high) {

        int pivotkey;

        //(*L).r[0]=(*L).r[low]; /* 用子表的第一个记录作枢轴记录 */
        // A[0]=A[low];

        // /* 枢轴记录关键字 */
        pivotkey = A[low];


        while (low < high) {
           /* 从表的两端交替地向中间扫描 */


            while ((low < high) &&
                    A[high] >= pivotkey) {
                --high;
            }


//            if(low>high)
//            {
//                System.out.println("impossible,low:"+low+" high:"+high );
//            }

            // /* 将比枢轴记录小的记录移到低端 */
            A[low] = A[high]; //当low=high 时,A[low]=a[high] 值未定

            // 将比枢轴记录大的记录移到高端 */

            while ((low < high) &&
                    A[low] <= pivotkey) {
                ++low;
            }


//            if(low>high)
//            {
//                System.out.println("impossible,low:"+low+" high:"+high );
//            }

            A[high] = A[low];//当low=high 时,A[low]=a[high] 值未定

        }

        //最终low 都 会等于high
//        if(low==high)
//        {
//            System.out.println("low==high:"+low);
//        }
        // /* 枢轴记录到位 */
        A[low] = pivotkey;
        //当low=high 时, 修正A[low]=a[high] 值为pivot值

        return low; /* 返回枢轴位置 */
    }

}
