package com.doinb.utils.math;

import java.math.BigDecimal;

public class BigDecimalUtils {

    private BigDecimalUtils() {
    }

    public static BigDecimalLogic is(BigDecimal value) {
        return new BigDecimalLogic(value);
    }

    public static BigDecimalLogic is(String value) {
        return new BigDecimalLogic(value);
    }

    public static BigDecimalCalculate cal(BigDecimal value) {
        return new BigDecimalCalculate(value);
    }

    public static BigDecimalCalculate cal(String value) {
        return new BigDecimalCalculate(new BigDecimal(value));
    }


    public static void main(String[] args) {
        BigDecimal eq = BigDecimalUtils.cal("10").plus("10").result();
        System.out.println(eq);

        BigDecimal value = BigDecimalHelper.build(1).add("1").getValue();
        System.out.println(value);
    }

}
