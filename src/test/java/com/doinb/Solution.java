package com.doinb;

/**
 * @author ddd
 * @description TODO
 * @createTime 2022/03/18
 */
class Solution {

    public static void main(String[] args) {
        // Par5 赠送货号时上限数量
        Double sendLimit = 5.0;
        Double threshold = 2.0;
        // 赠品上限必须是赠送数量的整数倍
        int finalSendLimit = 0;
        int tmp = sendLimit.intValue() % threshold.intValue();
        if(tmp == 0) {
            // 是整数倍
            finalSendLimit= sendLimit.intValue();
        } else {
            // 非整数倍
            int tmpRound = sendLimit.intValue() / threshold.intValue();
            finalSendLimit = threshold.intValue() * (tmpRound + 1);
        }
        System.out.println(finalSendLimit);
    }

    public static int[] plusOne(int[] digits) {
        int length = digits.length;
        for (int i = length - 1; i >= 0 ; i--) {
            if (digits[i] != 9) {
                //如果数组当前元素不等于9，直接加1
                //然后直接返回
                digits[i]++;
                return digits;
            } else {
                //如果数组当前元素等于9，那么加1之后
                //肯定会变为0，我们先让他变为0
                digits[i] = 0;
            }
        }
        //除非数组中的元素都是9，否则不会走到这一步，
        //如果数组的元素都是9，我们只需要把数组的长度
        //增加1，并且把数组的第一个元素置为1即可
        int length1 = digits.length;
        int temp[] = new int[length + 1];
        temp[0] = 1;
        return temp;
    }


    public static int singleNumber(int[] nums) {

        int rest = 6 ^ 3;
        System.out.println(rest);

        // 参考大神的异或运算
        int ret = 0;
        for (int num : nums) {
            System.out.println("异或运算ret=" + ret);
            // a^b^c=a^c^b；异或运算具有交换律
            ret = ret ^ num;
        }
        return ret;
    }
}