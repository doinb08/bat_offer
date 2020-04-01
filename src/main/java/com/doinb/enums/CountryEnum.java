package com.doinb.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author doinb
 */
@AllArgsConstructor
public enum CountryEnum {
    ONE(1, "齐"), TWO(2, "楚"), THREE(3, "燕"),
    FOUR(4, "赵"), FIVE(5, "魏"), SIX(6, "韩");

    @Getter private Integer retCode;
    @Getter private String retMessage;

    public static String getCountryEnum(int index) {
        for (CountryEnum countryEnum : values()) {
            if (countryEnum.getRetCode() == index){
                return countryEnum.retMessage;
            }
        }
        return "";
    }
}
