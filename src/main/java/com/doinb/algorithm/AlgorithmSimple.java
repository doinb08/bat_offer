package com.doinb.algorithm;

import java.util.concurrent.atomic.AtomicInteger;

public class AlgorithmSimple {

    /**
     * 二分查找法
     *
     * @param array 有序数组
     * @param a     查找值
     * @return 返回查找值
     */
    public static int biSearch(int[] array, int a) {
        int lo = 0;
        int hi = array.length - 1;
        int mid;
        while (lo <= hi) {
            mid = (lo + hi) / 2;//中间位置
            if (array[mid] == a) {
                return array[mid];
            } else if (array[mid] < a) { //向右查找
                lo = mid + 1;
            } else { //向左查找
                hi = mid - 1;
            }
        }
        return -1;
    }


    /**
     * 版冒泡排序
     *
     * @param a 乱序的数组
     */
    public static void bubbleSort(int[] a) {
        AtomicInteger at = new AtomicInteger(0);

        for (int i = 0; i < a.length - 1; i++) {         // 排序总轮数 = 元素个数 - 1
            int flag = 0;
            for (int j = 0; j < a.length - i - 1; j++) { // 每轮对比次数= 元素个数 - 排序轮数 - 1
                if (a[j] > a[j + 1]) {
                    int temp = a[j + 1];
                    a[j + 1] = a[j];
                    a[j] = temp;
                    flag = 1; // 加入标记
                    at.getAndIncrement();
                }
            }
            System.out.println(String.format("排序轮数，第 %d轮", i + 1));

            // 此处为冒泡排序的优化点
            if (flag == 0) { // 如果没有交换过的元素，则已经有序
                System.out.println("次数：" + at.get());
                return;
            }
        }
        // 打印排序后的结果
        for (int i = 0; i < a.length; i++) {
            System.out.println(a[i]);
        }
    }

    /**
     * 快速排序
     * 快速排序的原理：选择一个关键值作为基准值。比基准值小的都在左边序列（一般是无序的），
     * 比基准值大的都在右边（一般是无序的）。一般选择序列的第一个元素。
     *
     * @param a
     * @param low
     * @param high
     */
    public void sort(int[] a, int low, int high) {
        int start = low;
        int end = high;
        int key = a[low];
        while (end > start) {
            //从后往前比较
            while (end > start && a[end] >= key)
                //如果没有比关键值小的，比较下一个，直到有比关键值小的交换位置，然后又从前往后比较
                end--;
            if (a[end] <= key) {
                int temp = a[end];
                a[end] = a[start];
                a[start] = temp;
            }
            //从前往后比较
            while (end > start && a[start] <= key)
                //如果没有比关键值大的，比较下一个，直到有比关键值大的交换位置
                start++;
            if (a[start] >= key) {
                int temp = a[start];
                a[start] = a[end];
                a[end] = temp;
            }
            //此时第一次循环比较结束，关键值的位置已经确定了。左边的值都比关键值小，右边的值都比关键值大，
            // 但是两边的顺序还有可能是不一样的，进行下面的递归调用
        }
        //递归
        if (start > low) sort(a, low, start - 1);//左边序列。第一个索引位置到关键值索引-1
        if (end < high) sort(a, end + 1, high);//右边序列。从关键值索引+1 到最后一个
    }


    public static void main(String[] args) {
        int[] arrays = new int[]{1, 2, 3, 4, 5, 7, 10, 9, 8};
        int a = 1;
        // System.out.println(biSearch(arrays, a));
        bubbleSort(arrays);
    }
}
