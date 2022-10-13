package com.doinb.algorithm;

/**
 * @author bi.wang
 * @description TODO
 * @createTime 2022/09/23
 */
public class Code_EvenTimes {

    public static void main(String[] args) {
        int[] arr = {1, 1, 2, 2, 2, 2, 3, 3, 3};
        printOddTimesNum(arr);
    }


    /**
     * 同一批数据，找到落单的数； 偶数的1，结果是0；奇数的1，结果是1
     *
     * @param arr arr
     */
    private static void printOddTimesNum(int[] arr) {
        int eor = 0;
        for (int cur : arr) {
            eor ^= cur;
        }
        System.out.println(eor);
    }
}
