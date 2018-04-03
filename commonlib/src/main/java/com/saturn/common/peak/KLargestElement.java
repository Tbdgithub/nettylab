package com.saturn.common.peak;

import com.google.gson.Gson;

public class KLargestElement {


    class MaxHeap {
        private int heapSize;

        // 逻辑index 从1开始
        int[] data;

        public MaxHeap(int size) {
            data = new int[size];
        }

        public MaxHeap(int[] params) {
            build(params);
        }

        public void build(int[] nums) {
            data = nums;
            heapSize = data.length;
            int N = getHeapSize();

            for (int i = N / 2; i > 0; i--) {
                makeMax(data, i);
            }
        }

        public void print() {
            for (int i = 0; i < heapSize; i++) {
                System.out.print(data[i] + " ");
            }
        }

        private void makeMax(int[] a, int i) {
            //
            int left = left(i);
            int right = right(i);

            int largest = -1;
            if (left <= getHeapSize()) {
                if (a[left - 1] > a[i - 1]) {
                    largest = left;
                } else {
                    largest = i;
                }
            }

            if (right <= getHeapSize()) {
                if (a[right - 1] > a[largest - 1]) {
                    largest = right;
                }
            }

            if (largest > 0 && largest != i) {
                int temp = a[i - 1];
                a[i - 1] = a[largest - 1];
                a[largest - 1] = temp;
                makeMax(a, largest);
            }
        }

        public void insert(int key) {

            if (data.length <= heapSize) {
                resize(2);
            }
            //扩容
            heapSize += 1;
            data[heapSize - 1] = Integer.MIN_VALUE;
            increaseKey(heapSize, key);
        }

        private void resize(int factor) {
            int[] newData = new int[heapSize * factor];

            for (int i = 0; i < heapSize; i++) {
                newData[i] = data[i];
            }

            data = newData;
        }

        public void increaseKey(int i, int key) {

            if (key <= data[i - 1]) {
                throw new RuntimeException("new key is smaller");
            }

            //更新key
            data[i - 1] = key;

            //如果小于parent, 则向上交换
            while (i > 1 && data[parent(i) - 1] < data[i - 1]) {
                int temp = data[i - 1];
                data[i - 1] = data[parent(i) - 1];
                data[parent(i) - 1] = temp;

                i = parent(i);
            }

            //此时key 对应的节点上升到合适位置

        }


        private int getHeapSize() {
            return heapSize;
        }

        private Integer left(int i) {
            return 2 * i;
        }

        private Integer right(int i) {
            return 2 * i + 1;
        }

        private int parent(int i) {
            return i / 2;
        }

        public int getMaxium() {
            return data[0];
        }

        public Integer exactMax() {

            if (heapSize <= 0) {
                throw new RuntimeException("underflow");
            }

            int max = data[0];
            data[0] = data[heapSize - 1];
            //
            heapSize -= 1;
            makeMax(data, 1);
            return max;
        }

        public void sort() {

            for (int i = heapSize; i > 1; i--) {
                int temp = data[0];
                data[0] = data[i - 1];//最大的
                data[i - 1] = temp;

                heapSize -= 1;
                makeMax(data, 1);


            }
        }

    }

    public static void main(String[] args) {
        KLargestElement job = new KLargestElement();
        //job.start();
        job.heapRun();
    }

    public void start() {


        int[] nums = {3, 2, 1, 5, 6, 4};
        //int[] nums = {2, 1};

        int k = 2;


        int result = findKthLargest(nums, k);
        System.out.println("result:" + result);
    }

    public void heapRun() {
        int[] nums = {2, 1, 5, 6, 4, 3};
        MaxHeap maxHeap = new MaxHeap(nums);

        //System.out.println(new Gson().toJson(nums));
        //maxHeap.increaseKey(5, 6);

        System.out.println(new Gson().toJson(nums));
        //maxHeap.insert(7);
        //maxHeap.print();

        int SortSize = nums.length;
        maxHeap.sort();

        for (int i = 0; i < SortSize; i++) {
            System.out.print(maxHeap.data[i] + " ");
        }
        // maxHeap.print();

    }


    public int findKthLargest(int[] nums, int k) {

        MaxHeap maxHeap = new MaxHeap(nums);

        System.out.println("maxium:" + maxHeap.getMaxium());
        //  maxHeap.build(nums);

        for (int i = 0; i < k - 1; i++) {
            maxHeap.exactMax();
        }

        int result = maxHeap.exactMax();
        return result;
    }


}

//1.build maxheap
//2.exact max K
//
//a. array heap
//b. tree heap
