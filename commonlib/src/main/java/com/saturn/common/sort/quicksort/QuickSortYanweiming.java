package com.saturn.common.sort.quicksort;

/**
 * 严版算法
 * 分为三个集合 x<=pivot ,pivot,x>=pivot
 * 中间部分随着计算进行，越来越少，从N,变为pivot ;low ... high 的数量越来越少
 * 证明:
 * 1.初始条件;low ==0,high= N-1; 此时 low-1 侧集合为空, high+1 侧集合为空。A[0]=pivot ; 左右中间部分有N个, 分为三个集合，左，中,右.
 *  满足左<=pivot,右>= pivot
 *
 *  不变式表示 ：
 *  如0<=k<=low-1,则,A[k]<=pivot ;
 *  如 k>=high+1 and k<=N-1, 则A[k]>=pivot
 *
 * 2.从high向left 扫描,遇到大的，向左，遇到小的停下来; 或者low==high 时，停下来, A[low]=A[high],则 low-1 侧处理完，且左侧所有数均<=pivot
 *   如low < high, 则 A[low] <=pivot ;则左移动扫过的部分，满足不变式 ;包含 high-1,high;
 *   从low 向right 扫描，遇到小的，向右，遇到大的停下来;或者low==high 时,停下来, A[high]=A[low],则 high+1侧处理完，且右侧所有数均>=pivot
 *   如low <high ,则A[high]>=pivot ;则向右移动扫过的部分，满足不变式;包含 low-1,low ;
 *
 *   如low==high ，检查终止条件. 此时A[low/high]值未定，取 pivot 则保持不变式
 *
 * 3. 终止条件 :low=high ,中间部分有1个.为A[low]
 *     此时,左侧  0 ... low-1 ,共low-1 个,
 *         右侧  high+1 ... N-1 ,共 N-high -1 个,
 *    左，右侧相加为N-1 +(low-high) 个,
 *    当low == high 时, 左右侧共为N-1 个;A[low]=A[high] ;取A[low/high] 为pivot ，则总数为N=N-1+1; 满足不变式.且扫描完集合.
 *    因每一步操作均保持不变式1，故操作结果正确.
 *
 */
public class QuickSortYanweiming {

    static int count=0;

   public <T extends Comparable<T>> int Partition(T[] A,int low,int high)
   {

       T pivotkey;

       //(*L).r[0]=(*L).r[low]; /* 用子表的第一个记录作枢轴记录 */
      // A[0]=A[low];

       // /* 枢轴记录关键字 */
       pivotkey=A[low];


       while(low< high)
       {
           /* 从表的两端交替地向中间扫描 */


           while ((low<high) && A[high].compareTo(pivotkey)>=0)
           {
               --high;
           }

//           if(low==high)
//           {
//               System.out.println("low==high:"+low);
//           }

           if(low>high)
           {
               System.out.println("impossible,low:"+low+" high:"+high );
           }

           // /* 将比枢轴记录小的记录移到低端 */
           A[low]=A[high]; //当low=high 时,A[low]=a[high] 值未定

           // 将比枢轴记录大的记录移到高端 */

           while ((low <high) && A[low].compareTo(pivotkey)<=0)
           {
               ++low;
           }



           if(low>high)
           {
               System.out.println("impossible,low:"+low+" high:"+high );
           }

           A[high]=A[low];//当low=high 时,A[low]=a[high] 值未定

       }

       //最终low 都 会等于high
       if(low==high)
       {
           System.out.println("low==high:"+low);
       }
       // /* 枢轴记录到位 */
       A[low]= pivotkey;
       //当low=high 时, 修正A[low]=a[high] 值为pivot值

       return low; /* 返回枢轴位置 */
   }



   public  <T extends Comparable<T>> void QuickSort(T[] A)
    {
        /* 对顺序表L作快速排序。算法10.8 */
        QSort(A,0,A.length-1);
    }

    public <T extends Comparable<T>>  void   QSort(T[] A,int low,int high)

    {
        /* 对顺序表L中的子序列L.r[low..high]作快速排序。算法10.7 */

        int pivotloc;

        if(low<high)
        { /* 长度大于1 */
            pivotloc=Partition(A,low,high); /* 将L.r[low..high]一分为二 */
            count++;
            System.out.println(String.format("第%s轮:",count));
            // print(*L,1);
            System.out.println("pivot index:"+pivotloc+" value:"+A[pivotloc]);
            print(A);

            QSort(A,low,pivotloc-1); /* 对低子表递归排序，pivotloc是枢轴位置 */
            QSort(A,pivotloc+1,high); /* 对高子表递归排序 */
        }
        else
        {
           // System.out.println("do nothing !!!");
        }


    }

    public <T extends Comparable<T>> void print(T[] A)
    {
        for(T item :A)
        {
            System.out.print(item+" ");
        }

        System.out.println();
    }

}
