package com.doinb.utils.math;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BigDecimalCalculate {

    private final BigDecimal amount;

    public BigDecimalCalculate(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimalCalculate(String amount) {
        this.amount = new BigDecimal(amount);
    }

    public BigDecimalCalculate plus(BigDecimal value) {
        return new BigDecimalCalculate(this.amount.add(value));
    }

    public BigDecimalCalculate plus(String value) {
        return plus(new BigDecimal(value));
    }

    public BigDecimalCalculate minus(BigDecimal value) {
        return new BigDecimalCalculate(this.amount.subtract(value));
    }

    public BigDecimalCalculate minus(String value) {
        return minus(new BigDecimal(value));
    }

    public BigDecimalCalculate mul(BigDecimal value) {
        return new BigDecimalCalculate(this.amount.multiply(value));
    }

    public BigDecimalCalculate mul(String value) {
        return mul(new BigDecimal(value));
    }

    public BigDecimalCalculate div(BigDecimal value) {
        return new BigDecimalCalculate(this.amount.divide(value, RoundingMode.HALF_EVEN));
    }

    public BigDecimalCalculate div(String value) {
        return div(new BigDecimal(value));
    }

    public BigDecimalCalculate div(BigDecimal value, int scale) {
        return new BigDecimalCalculate(this.amount.divide(value, scale, RoundingMode.HALF_EVEN));
    }

    public BigDecimalCalculate div(String value, int scale) {
        return div(new BigDecimal(value), scale);
    }

    /**
     * 默认保留2位小数
     *
     * @return result
     */
    public BigDecimal result() {
        return this.amount.setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * 保留精度
     *
     * @param newScale 小数点个数
     * @return result
     */
    public BigDecimal result(int newScale) {
        return this.amount.setScale(newScale, RoundingMode.HALF_UP);
    }

}
