package com.saturn.common.peak;

import com.google.gson.Gson;

import java.util.HashMap;

public class TwoSum {

    //1.full map
//    Given nums = [2, 7, 11, 15], target = 9,
//
//    Because nums[0] + nums[1] = 2 + 7 = 9,
//            return [0, 1].

    public static int[] twoSumMap(int[] nums, int target) {

        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] + nums[j] == target) {
                    return new int[]{i, j};
                }
            }
        }

        return null;

    }

    public static int[] twoSumMap1(int[] nums, int target) {

        //1. put value

        HashMap<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {
            map.put(nums[i], i);
        }

        for (int i = 0; i < nums.length; i++) {

            int other = target - nums[i];
            Integer otherIndex = map.get(other);
            if (otherIndex != null
                    && otherIndex != i// 要排除掉自己
                    ) {
                return new int[]{i, otherIndex};
            }
        }

        return null;
    }

    public static int[] twoSumMap2(int[] nums, int target) {

        HashMap<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {

            int other = target - nums[i];
            Integer otherIndex = map.get(other);

            // 0, ... i-1 为已查询过的
            // i 未put 到map,所以不存在和自己比较的问题
            //非常巧妙的不用排除自己)
            if (otherIndex != null) {
                return new int[]{otherIndex, i};
            } else {
                map.put(nums[i], i);
            }
        }

        return null;
    }


    public static void main(String[] args) {
        //  int[] numbs = {2, 7, 11, 15};

        //int[] numbs = {3, 2, 4};
        // int[] result = twoSumMap(numbs, 9);
        // System.out.println(new Gson().toJson(result));

//        int[] result = twoSumMap1(numbs, 6);
//        System.out.println(new Gson().toJson(result));
        int[] numbs = {2, 1, 3, 4};
        int[] result = twoSumMap2(numbs, 6);
        System.out.println(new Gson().toJson(result));
    }

    //2.one pass map
}
