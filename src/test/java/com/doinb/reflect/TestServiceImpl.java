package com.doinb.reflect;

import org.springframework.stereotype.Component;

/**
 * @author bi.wang
 * @description TODO
 * @createTime 2022/07/28
 */
@Component
public class TestServiceImpl<T> implements TestService<T> {

    @Override
    public void print() {
        System.out.println("TestServiceImpl >>>");
    }

}
