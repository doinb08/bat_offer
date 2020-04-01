package com.doinb.utils;

import java.math.BigDecimal;

/**
 * @author: doinb.
 *
 * 金融业务代码中,对钱计算精度要求非常高,float和double只能用来做科学计算和工程计算。商业运算中我们要使用BigDecimal。
 *
 * API
 * 构造器                   描述
 * BigDecimal(int)       创建一个具有参数所指定整数值的对象。
 * BigDecimal(double)    创建一个具有参数所指定双精度值的对象。
 * BigDecimal(long)      创建一个具有参数所指定长整数值的对象。
 * BigDecimal(String)    创建一个具有参数所指定以字符串表示的数值的对象。
 * 函数：
 *
 * 方法                    描述
 * add(BigDecimal)       BigDecimal对象中的值相加，然后返回这个对象。
 * subtract(BigDecimal)  BigDecimal对象中的值相减，然后返回这个对象。
 * multiply(BigDecimal)  BigDecimal对象中的值相乘，然后返回这个对象。
 * divide(BigDecimal)    BigDecimal对象中的值相除，然后返回这个对象。
 * toString()            将BigDecimal对象的数值转换成字符串。
 * doubleValue()         将BigDecimal对象中的值以双精度数返回。
 * floatValue()          将BigDecimal对象中的值以单精度数返回。
 * longValue()           将BigDecimal对象中的值以长整数返回。
 * intValue()            将BigDecimal对象中的值以整数返回。
 */
public class BigDecimalUtil {

    private BigDecimalUtil() {

    }

    public static BigDecimal add(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2);
    }

    public static BigDecimal sub(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2);
    }

    public static BigDecimal mul(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2);
    }

    public static BigDecimal div(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        // 2 = 保留小数点后两位   ROUND_HALF_UP = 四舍五入 应对除不尽的情况
        return b1.divide(b2, 2, BigDecimal.ROUND_HALF_UP);
    }
}
