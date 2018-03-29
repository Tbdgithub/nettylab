package com.saturn.common.peak;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class LengthOfLongestSubstring {


    public static void main(String[] args) {

        String input = "abccbaabcd";

        //int result = lengthOfLongestSubstring_Brute(input);
        //int result = lengthOfLongestSubstring_slidewindow_opt(input);
        int result = lengthOfLongestSubstring_slidewindow_Array(input);


        System.out.println("result:" + result);
    }


    public static int lengthOfLongestSubstring_slidewindow_Array(String s) {
        int len = 0;
        int[] indexArray = new int[128];
        for (int i = 0, j = 0; j < s.length(); j++) {

            i = Math.max(indexArray[s.charAt(j)], i);
            len = Math.max(len, j - i + 1);// j-i+1 为 当前window长度
            //map.put(s.charAt(j), j + 1); //值为index的右边
            indexArray[s.charAt(j)] = j + 1;
        }

        return len;

    }

    public static int lengthOfLongestSubstring_slidewindow_opt(String s) {

        //1.map 放s 放不同的
        //2.向右移动
        //
        int len = 0;
        HashMap<Character, Integer> map = new HashMap<>();
        for (int i = 0, j = 0; j < s.length(); j++) {

            //j 从0到 N-1
            if (map.containsKey(s.charAt(j))) {
                //i 直接跳到该到的位置.
                //i 左边的index 已处理过
                i = Math.max(map.get(s.charAt(j)), i);
            }

            len = Math.max(len, j - i + 1);// j-i+1 为 当前window长度
            map.put(s.charAt(j), j + 1); //值为index的右边
        }

        return len;

    }

    public static int lengthOfLongestSubstring_slidewindow(String s) {

        //1.windows 放不同的
        //2.向右移动

        Set<Character> set = new HashSet<>();

        int i = 0;
        int j = 0;
        int N = s.length();
        int result = 0;

        while (i < N && j < N) {
            if (!set.contains(s.charAt(j))) {
                //j 为右端
                set.add(s.charAt(j));
                ++j;//右端右移一步;
                result = Math.max(result, j - i);//最大的
            } else {
                //
                set.remove(s.charAt(i));//因为i,与j 值重复了
                ++i;// 左端右移一步
            }
        }

        //todo 可输出子串;记录i,j

        // System.out.println(s.substring(i,j));
        return result;
    }

    public static int lengthOfLongestSubstring_Brute(String s) {

        //1.all unique
        //2.
        //index
        int len = 0;
        for (int i = 0; i < s.length(); i++) {
            for (int j = i; j < s.length(); j++) {
                if (allUnique(s, i, j)) {
                    len = Math.max(len, j - i + 1);
                }
            }
        }

        return len;
    }

    public static boolean allUnique(String s, int begin, int end) {
        Set<Character> map = new HashSet<>();
        for (int i = begin; i <= end; i++) {
            Character character = s.charAt(i);
            if (map.contains(character)) {
                return false;
            } else {
                map.add(character);
            }
        }

        return true;
    }


}
