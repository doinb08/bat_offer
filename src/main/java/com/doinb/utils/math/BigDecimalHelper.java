package com.doinb.utils.math;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.sun.istack.internal.NotNull;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author bi.wang
 * @description BigDecimal帮助类
 * @createTime 2022/02/07
 */
public class BigDecimalHelper {

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
    private BigDecimalHelper() {

    }

    /**
     * 有参构造
     *
     * @param value value值
     */
    private BigDecimalHelper(BigDecimal value) {
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
    public static BigDecimalHelper build(@NotNull Object val) {
        Assert.isTrue(val != null, "param cannot be null!");
        return val instanceof BigDecimal ? build(((BigDecimal) val)) : build(val.toString());
    }

    /**
     * 构建BigDecimalHelper
     *
     * @param val value值
     */
    public static BigDecimalHelper build(@NotNull String val) {
        return new BigDecimalHelper(strToDecimal(val));
    }

    /**
     * 构建BigDecimalHelper
     *
     * @param val value值
     */
    public static BigDecimalHelper build(@NotNull BigDecimal val) {
        Assert.isTrue(val != null, "param cannot be null!");
        return new BigDecimalHelper(val);
    }

    /**
     * 大于
     *
     * @param value value
     */
    public boolean gt(@NotNull BigDecimalHelper value) {
        return gt(value.getValue());
    }

    /**
     * 大于
     *
     * @param value value
     */
    public boolean gt(@NotNull BigDecimal value) {
        return this.value.compareTo(value) > ZERO;
    }

    /**
     * 大于
     *
     * @param value value
     */
    public boolean gt(@NotNull String value) {
        return gt(strToDecimal(value));
    }

    /**
     * 大于等于
     *
     * @param value value
     */
    public boolean gte(@NotNull BigDecimalHelper value) {
        return gte(value.getValue());
    }

    /**
     * 大于等于
     *
     * @param value value
     */
    public boolean gte(@NotNull BigDecimal value) {
        return this.value.compareTo(value) >= ZERO;
    }

    /**
     * 大于等于
     *
     * @param value value
     */
    public boolean gte(@NotNull String value) {
        return gte(strToDecimal(value));
    }

    /**
     * 小于
     *
     * @param value value
     */
    public boolean lt(@NotNull BigDecimalHelper value) {
        return lt(value.getValue());
    }

    /**
     * 小于
     *
     * @param value value
     */
    public boolean lt(@NotNull BigDecimal value) {
        return this.value.compareTo(value) < ZERO;
    }

    /**
     * 小于
     *
     * @param value value
     */
    public boolean lt(@NotNull String value) {
        return lt(strToDecimal(value));
    }

    /**
     * 小于等于
     *
     * @param value value
     */
    public boolean lte(@NotNull BigDecimalHelper value) {
        return lte(value.getValue());
    }

    /**
     * 小于等于
     *
     * @param value value
     */
    public boolean lte(@NotNull BigDecimal value) {
        return this.value.compareTo(value) <= ZERO;
    }

    /**
     * 小于等于
     *
     * @param value value
     */
    public boolean lte(@NotNull String value) {
        return lte(strToDecimal(value));
    }

    /**
     * 相等
     *
     * @param value value
     */
    public boolean eq(@NotNull BigDecimalHelper value) {
        return eq(value.getValue());
    }

    /**
     * 相等
     *
     * @param value value
     */
    public boolean eq(@NotNull BigDecimal value) {
        return this.value.compareTo(value) == ZERO;
    }

    /**
     * 相等
     *
     * @param value value
     */
    public boolean eq(@NotNull String value) {
        return eq(strToDecimal(value));
    }

    /**
     * 不等
     *
     * @param value value
     */
    public boolean neq(@NotNull BigDecimalHelper value) {
        return !eq(value);
    }

    /**
     * 不等
     *
     * @param value value
     */
    public boolean neq(@NotNull BigDecimal value) {
        return !eq(value);
    }

    /**
     * 不等
     *
     * @param value value
     */
    public boolean neq(@NotNull String value) {
        return !eq(value);
    }

    /**
     * 在某个区间 包含区间值
     *
     * @param small 小值
     * @param large 大值
     */
    public boolean isBetweenOrEq(@NotNull BigDecimal small, @NotNull BigDecimal large) {
        return lte(large) && gte(small);
    }

    /**
     * 在某个区间 不包含区间值
     *
     * @param small 小值
     * @param large 大值
     */
    public boolean isBetween(@NotNull BigDecimal small, @NotNull BigDecimal large) {
        return lt(large) && gt(small);
    }

    /**
     * 在某个区间 包含区间值
     *
     * @param small 小值
     * @param large 大值
     */
    public boolean isBetweenOrEq(@NotNull BigDecimalHelper small, @NotNull BigDecimalHelper large) {
        return lte(large) && gte(small);
    }

    /**
     * 在某个区间 不包含区间值
     *
     * @param small 小值
     * @param large 大值
     */
    public boolean isBetween(@NotNull BigDecimalHelper small, @NotNull BigDecimalHelper large) {
        return lt(large) && gt(small);
    }

    /**
     * 在某个区间 包含区间值
     *
     * @param small 小值
     * @param large 大值
     */
    public boolean isBetweenOrEq(@NotNull String small, @NotNull String large) {
        return lte(large) && gte(small);
    }

    /**
     * 在某个区间 不包含区间值
     *
     * @param small 小值
     * @param large 大值
     */
    public boolean isBetween(@NotNull String small, @NotNull String large) {
        return lt(large) && gt(small);
    }

    /**
     * 加法
     *
     * @param value 加数
     */
    public BigDecimalHelper add(@NotNull BigDecimal value) {
        return build(this.value.add(value));
    }

    /**
     * 加法
     *
     * @param value 加数
     */
    public BigDecimalHelper add(@NotNull BigDecimalHelper value) {
        return add(value.getValue());
    }

    /**
     * 加法
     *
     * @param value 加数
     */
    public BigDecimalHelper add(@NotNull String value) {
        return add(strToDecimal(value));
    }

    /**
     * 减法
     *
     * @param value 减数
     */
    public BigDecimalHelper sub(@NotNull BigDecimal value) {
        return build(this.value.subtract(value));
    }

    /**
     * 减法
     *
     * @param value 减数
     */
    public BigDecimalHelper sub(@NotNull BigDecimalHelper value) {
        return sub(value.getValue());
    }

    /**
     * 减法
     *
     * @param value 减数
     */
    public BigDecimalHelper sub(@NotNull String value) {
        return sub(strToDecimal(value));
    }

    /**
     * 乘法
     *
     * @param value 乘数
     */
    public BigDecimalHelper mul(@NotNull BigDecimal value) {
        return build(this.value.multiply(value));
    }

    /**
     * 乘法
     *
     * @param value 乘数
     */
    public BigDecimalHelper mul(@NotNull BigDecimalHelper value) {
        return mul(value.getValue());
    }

    /**
     * 乘法
     *
     * @param value 乘数
     */
    public BigDecimalHelper mul(@NotNull String value) {
        return mul(strToDecimal(value));
    }

    /**
     * 除法
     *
     * @param value 除数
     */
    public BigDecimalHelper div(@NotNull BigDecimal value) {
        return div(value, DEF_DIV_SCALE);
    }

    /**
     * 除法
     *
     * @param value 除数
     */
    public BigDecimalHelper div(@NotNull BigDecimalHelper value) {
        return div(value.getValue(), DEF_DIV_SCALE);
    }

    /**
     * 除法
     *
     * @param value 除数
     */
    public BigDecimalHelper div(@NotNull String value) {
        return div(strToDecimal(value), DEF_DIV_SCALE);
    }

    /**
     * 除法
     *
     * @param value 除数
     * @param scale scale
     */
    public BigDecimalHelper div(@NotNull BigDecimal value, @NotNull int scale) {
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
    public BigDecimalHelper div(@NotNull BigDecimalHelper value, @NotNull int scale) {
        return div(value.getValue(), scale);
    }

    /**
     * 除法
     *
     * @param value 除数
     * @param scale scale
     */
    public BigDecimalHelper div(@NotNull String value, @NotNull int scale) {
        return div(strToDecimal(value), scale);
    }

    /**
     * 四舍五入保留两位小数
     */
    public BigDecimalHelper round() {
        return build(this.value.setScale(TWO, BigDecimal.ROUND_HALF_UP));
    }


    /**
     * 四舍五入保留scale位
     */
    public BigDecimalHelper round(@NotNull int scale) {
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
    public void setValue(@NotNull BigDecimal value) {
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
