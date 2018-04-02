package com.saturn.common.peak;

import com.google.gson.Gson;

import java.util.*;

public class TopKFrequent {

    public static void main(String[] args) {
        TopKFrequent job = new TopKFrequent();
        job.start();
    }

    public List<Integer> topKFrequent(int[] nums, int k) {

        //1. 按key 分区
        //2. 按value bucket 倒排
        //3. 取最高的K个

        //max bucket ,最大的frequency ,避免outofindex
        int max = 0;
        Map<Integer, Integer> map = new HashMap<>();
        for (int n : nums) {
            int temp = map.getOrDefault(n, 0) + 1;
            max = temp > max ? temp : max;
            map.put(n, temp);
        }

        //准备bucket  0(m) 长度为max+1 ; 0,1,.... max;
        List<Integer>[] bucket = new List[max + 1];
        for (int n : map.keySet()) {
            int freq = map.get(n);
            if (bucket[freq] == null) {
                bucket[freq] = new LinkedList<>();
            }

            bucket[freq].add(n);
        }

        List<Integer> res = new LinkedList<>();

        //bucket 排序 0(n)
        //bucket  按frequency 天然有序号
        for (int i = 0; i < bucket.length && k > 0; i++) {
            //从高到低查
            //查到K个bucket ，退出
            if (bucket[bucket.length - 1 - i] != null) {
                List<Integer> list = bucket[bucket.length - 1 - i];
                res.addAll(list);
                k -= list.size();
            }
        }

        return res;

    }

    public void start() {
        int[] nums = {1, 1, 1, 2, 2, 2, 3, 3, 3, 3, 4, 4, 4, 4};
        //int[] nums = {1, 1};
        System.out.println(new Gson().toJson(topKFrequent(nums, 1)));
    }
}
