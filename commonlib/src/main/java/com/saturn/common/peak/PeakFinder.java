package com.saturn.common.peak;

import java.util.HashMap;
import java.util.Map;

public class PeakFinder {


    public int getPeakLinear(int[] nums) {
        for (int i = 0; i < nums.length - 1; i++) {
            if (nums[i] > nums[i + 1])
                return i;
        }
        return nums.length - 1;
    }

    public int getPeak0(int[] a) {
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
            //1.先中分
            //2.通过相邻两项比较，确定斜率delta
            //3.如果delta>0,则peak一定在middle+1,right 之间,闭区间
            //4.如果delta<0,则peak一定在left,middle之间，闭区间
            //5.如果delta==0,则peak在left,middle,闭区间 ；middle+1,right ，闭区间，都可能

            if (a[middle] < a[middle + 1]) {
                //middle+1 一定不越界
                //右分
                left = middle + 1; //middle 一定不是目标
                //middle+1有可能是
            } else if (a[middle] == a[middle + 1]) {

                //无法判断,左分，右分均可
                // right = middle;
                left = middle + 1;

            } else {
                //左分
                right = middle; // middle +1 一定不是目标
                //middle 有可能是

            }

            // System.out.println("middle:"+middle+" ["+left+","+right+"]");
        }

        //left ==right 时
        return left;
    }

    public int findPeakElement(int[] nums) {
        return search(nums, 0, nums.length - 1);
    }

    public int search(int[] nums, int l, int r) {
        if (l == r)
            return l;
        int mid = (l + r) / 2;
        if (nums[mid] > nums[mid + 1]) {

            //right:mid 关键
            return search(nums, l, mid);//
        } else {
            //<=相同处理
            //l:mid+1 关键
            return search(nums, mid + 1, r);
        }
    }

    public int[] twoSumMap(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>(nums.length);
        for (int i = 0; i < nums.length; i++) {
            map.put(nums[i], i);
            //关键 nums[i] 为key, i 为value
            //
        }
        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];
            //化运算为查找key
            if (map.containsKey(complement) && map.get(complement) != i) {
                //complement 不能等于i .即，相加的两数不相等
                return new int[] { i, map.get(complement) };
            }
        }

        throw new IllegalArgumentException("No two sum solution");
    }

    public int[] twoSumOnePass(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];
            if (map.containsKey(complement)) {
                return new int[] { map.get(complement), i };
            }
            map.put(nums[i], i);
            //一次扫描
            //先check
            //再put
            //put的key 是num的值,value 是index ;反转
        }

        throw new IllegalArgumentException("No two sum solution");
    }

    public int[] twoSum(int[] nums, int target) {

        int count=0;

        for (int i = 0; i < nums.length ; i++) {

            for(int j=i+1;j<nums.length;j++)
            {
                count++;
                //0: 1,2,3
                //1:   2,3
                //2:    ,3
                //3:
                //关键：j=i+1; i不等于j
                // k <i 的已经比较过了
                //所以j 从i+1开始,闭区间
                if(nums[i]+nums[j]==target)
                {
                    return new int[] {i,j};
                    //count++;
                }
            }
        }

       // System.out.println("count:"+count);

        return nums;

    }


}
