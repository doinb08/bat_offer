package com.doinb.utils.math;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author zk
 * @description BigDecimal帮助类
 * @createTime 2022/02/07
 */
public class BigDecimalUtil {

    /**
     * 定义一些常量
     */

    private static final int ZERO = 0;

    private static final int TWO = 2;

    private static final int DEF_DIV_SCALE = 6;

    /**
     * 当前值
     */
    private volatile BigDecimal value;

    /**
     * 私有无参构造
     */
    private BigDecimalUtil() {

    }

    /**
     * 有参构造
     *
     * @param value value值
     */
    private BigDecimalUtil(BigDecimal value) {
        this.value = value;
    }

    /**
     * string转换成BigDecimal
     *
     * @param val value值
     * @return BigDecimal
     */
    public static BigDecimal strToDecimal(String val) {
        Assert.isTrue(StrUtil.isNotBlank(val), "param cannot be null!");
        return new BigDecimal(val);
    }

    /**
     * 构建BigDecimalHelper
     *
     * @param val value值 只接受BigDecimal类型和String类型的数字
     */
    public static BigDecimalUtil build(Object val) {
        Assert.isTrue(val != null, "param cannot be null!");
        return val instanceof BigDecimal ? build(((BigDecimal) val)) : build(val.toString());
    }

    /**
     * 构建BigDecimalHelper
     *
     * @param val value值
     */
    public static BigDecimalUtil build(String val) {
        return new BigDecimalUtil(strToDecimal(val));
    }

    /**
     * 构建BigDecimalHelper
     *
     * @param val value值
     */
    public static BigDecimalUtil build(BigDecimal val) {
        Assert.isTrue(val != null, "param cannot be null!");
        return new BigDecimalUtil(val);
    }

    /**
     * 大于
     *
     * @param value value
     */
    public boolean gt(BigDecimalUtil value) {
        return gt(value.getValue());
    }

    /**
     * 大于
     *
     * @param value value
     */
    public boolean gt(BigDecimal value) {
        return this.value.compareTo(value) > ZERO;
    }

    /**
     * 大于
     *
     * @param value value
     */
    public boolean gt(String value) {
        return gt(strToDecimal(value));
    }

    /**
     * 大于等于
     *
     * @param value value
     */
    public boolean gte(BigDecimalUtil value) {
        return gte(value.getValue());
    }

    /**
     * 大于等于
     *
     * @param value value
     */
    public boolean gte(BigDecimal value) {
        return this.value.compareTo(value) >= ZERO;
    }

    /**
     * 大于等于
     *
     * @param value value
     */
    public boolean gte(String value) {
        return gte(strToDecimal(value));
    }

    /**
     * 小于
     *
     * @param value value
     */
    public boolean lt(BigDecimalUtil value) {
        return lt(value.getValue());
    }

    /**
     * 小于
     *
     * @param value value
     */
    public boolean lt(BigDecimal value) {
        return this.value.compareTo(value) < ZERO;
    }

    /**
     * 小于
     *
     * @param value value
     */
    public boolean lt(String value) {
        return lt(strToDecimal(value));
    }

    /**
     * 小于等于
     *
     * @param value value
     */
    public boolean lte(BigDecimalUtil value) {
        return lte(value.getValue());
    }

    /**
     * 小于等于
     *
     * @param value value
     */
    public boolean lte(BigDecimal value) {
        return this.value.compareTo(value) <= ZERO;
    }

    /**
     * 小于等于
     *
     * @param value value
     */
    public boolean lte(String value) {
        return lte(strToDecimal(value));
    }

    /**
     * 相等
     *
     * @param value value
     */
    public boolean eq(BigDecimalUtil value) {
        return eq(value.getValue());
    }

    /**
     * 相等
     *
     * @param value value
     */
    public boolean eq(BigDecimal value) {
        return this.value.compareTo(value) == ZERO;
    }

    /**
     * 相等
     *
     * @param value value
     */
    public boolean eq(String value) {
        return eq(strToDecimal(value));
    }

    /**
     * 不等
     *
     * @param value value
     */
    public boolean neq(BigDecimalUtil value) {
        return !eq(value);
    }

    /**
     * 不等
     *
     * @param value value
     */
    public boolean neq(BigDecimal value) {
        return !eq(value);
    }

    /**
     * 不等
     *
     * @param value value
     */
    public boolean neq(String value) {
        return !eq(value);
    }

    /**
     * 在某个区间 包含区间值
     *
     * @param small 小值
     * @param large 大值
     */
    public boolean isBetweenOrEq(BigDecimal small, BigDecimal large) {
        return lte(large) && gte(small);
    }

    /**
     * 在某个区间 不包含区间值
     *
     * @param small 小值
     * @param large 大值
     */
    public boolean isBetween(BigDecimal small, BigDecimal large) {
        return lt(large) && gt(small);
    }

    /**
     * 在某个区间 包含区间值
     *
     * @param small 小值
     * @param large 大值
     */
    public boolean isBetweenOrEq(BigDecimalUtil small, BigDecimalUtil large) {
        return lte(large) && gte(small);
    }

    /**
     * 在某个区间 不包含区间值
     *
     * @param small 小值
     * @param large 大值
     */
    public boolean isBetween(BigDecimalUtil small, BigDecimalUtil large) {
        return lt(large) && gt(small);
    }

    /**
     * 在某个区间 包含区间值
     *
     * @param small 小值
     * @param large 大值
     */
    public boolean isBetweenOrEq(String small, String large) {
        return lte(large) && gte(small);
    }

    /**
     * 在某个区间 不包含区间值
     *
     * @param small 小值
     * @param large 大值
     */
    public boolean isBetween(String small, String large) {
        return lt(large) && gt(small);
    }

    /**
     * 加法
     *
     * @param value 加数
     */
    public BigDecimalUtil add(BigDecimal value) {
        return build(this.value.add(value));
    }

    /**
     * 加法
     *
     * @param value 加数
     */
    public BigDecimalUtil add(BigDecimalUtil value) {
        return add(value.getValue());
    }

    /**
     * 加法
     *
     * @param value 加数
     */
    public BigDecimalUtil add(String value) {
        return add(strToDecimal(value));
    }

    /**
     * 减法
     *
     * @param value 减数
     */
    public BigDecimalUtil sub(BigDecimal value) {
        return build(this.value.subtract(value));
    }

    /**
     * 减法
     *
     * @param value 减数
     */
    public BigDecimalUtil sub(BigDecimalUtil value) {
        return sub(value.getValue());
    }

    /**
     * 减法
     *
     * @param value 减数
     */
    public BigDecimalUtil sub(String value) {
        return sub(strToDecimal(value));
    }

    /**
     * 乘法
     *
     * @param value 乘数
     */
    public BigDecimalUtil mul(BigDecimal value) {
        return build(this.value.multiply(value));
    }

    /**
     * 乘法
     *
     * @param value 乘数
     */
    public BigDecimalUtil mul(BigDecimalUtil value) {
        return mul(value.getValue());
    }

    /**
     * 乘法
     *
     * @param value 乘数
     */
    public BigDecimalUtil mul(String value) {
        return mul(strToDecimal(value));
    }

    /**
     * 除法
     *
     * @param value 除数
     */
    public BigDecimalUtil div(BigDecimal value) {
        return div(value, DEF_DIV_SCALE);
    }

    /**
     * 除法
     *
     * @param value 除数
     */
    public BigDecimalUtil div(BigDecimalUtil value) {
        return div(value.getValue(), DEF_DIV_SCALE);
    }

    /**
     * 除法
     *
     * @param value 除数
     */
    public BigDecimalUtil div(String value) {
        return div(strToDecimal(value), DEF_DIV_SCALE);
    }

    /**
     * 除法
     *
     * @param value 除数
     * @param scale scale
     */
    public BigDecimalUtil div(BigDecimal value, int scale) {
        Assert.isTrue(scale >= ZERO, "The scale must be a positive integer or zero");
        //0除以任何数都为0
        if (eq(BigDecimal.ZERO)) {
            return build(BigDecimal.ZERO);
        }
        //除数不能为0
        Assert.isTrue(BigDecimal.ZERO.compareTo(value) != ZERO, "Divisor cannot be zero");
        return build(this.value.divide(value, scale, BigDecimal.ROUND_HALF_UP));
    }

    /**
     * 除法
     *
     * @param value 除数
     * @param scale scale
     */
    public BigDecimalUtil div(BigDecimalUtil value, int scale) {
        return div(value.getValue(), scale);
    }

    /**
     * 除法
     *
     * @param value 除数
     * @param scale scale
     */
    public BigDecimalUtil div(String value, int scale) {
        return div(strToDecimal(value), scale);
    }

    /**
     * 四舍五入保留两位小数
     */
    public BigDecimalUtil round() {
        return build(this.value.setScale(TWO, BigDecimal.ROUND_HALF_UP));
    }


    /**
     * 四舍五入保留scale位
     */
    public BigDecimalUtil round(int scale) {
        Assert.isTrue(scale >= ZERO, "The scale must be a positive integer or zero");
        BigDecimal one = BigDecimal.ONE;
        return build(this.value.divide(one, scale, BigDecimal.ROUND_HALF_UP));
    }

    /**
     * 向上取整
     */
    public long roundUp() {
        return this.value.setScale(ZERO, BigDecimal.ROUND_UP).longValue();
    }

    /**
     * 向下取整
     */
    public long roundDown() {
        return this.value.setScale(ZERO, BigDecimal.ROUND_DOWN).longValue();
    }

    /**
     * 默认保留2位小数
     *
     * @return getter
     */
    public BigDecimal getValue() {
        return value.setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * 保留精度
     *
     * @param newScale 小数点个数
     * @return result
     */
    public BigDecimal getValue(int newScale) {
        return value.setScale(newScale, RoundingMode.HALF_UP);
    }

    /**
     * setter
     */
    public void setValue(BigDecimal value) {
        this.value = value;
    }

    /**
     * toString
     */
    @Override
    public String toString() {
        return value.toString();
    }
}
