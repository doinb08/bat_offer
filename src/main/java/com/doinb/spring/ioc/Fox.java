package com.doinb.spring.ioc;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.PostConstruct;
import java.util.Arrays;


public class Fox implements InitializingBean, DisposableBean {

    public Fox() {
        System.out.println("【IoC源码学习】Construct~fox");
    }

    @PostConstruct
    public void init() {
        System.out.println("【IoC源码学习】@PostConstruct~init");
    }

    @PostConstruct
    public void init2() {
        System.out.println("【IoC源码学习】@PostConstruct~init2");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("【IoC源码学习】destroy~");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("【IoC源码学习】afterPropertiesSet~");
    }

    public static void main(String[] args) {

    }
}
