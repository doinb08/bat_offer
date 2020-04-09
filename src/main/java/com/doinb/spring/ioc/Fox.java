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
        /**
         * 5个参数，
         * 第一个参数是要被复制的数组
         * 第二个参数是被复制的数字开始复制的下标
         * 第三个参数是目标数组，也就是要把数据放进来的数组
         * 第四个参数是从目标数据第几个下标开始放入数据
         * 第五个参数表示从被复制的数组中拿几个数值放到目标数组中
         */
        int[] arr = { 1, 2, 3, 4, 5 };
        int[] targetArr = { 5, 6,7, 8, 9 };
        System.arraycopy(arr, 0, targetArr, 0, 5);

        Arrays.stream(targetArr).forEach(System.out::println);
    }
}
