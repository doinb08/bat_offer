package com.doinb.jvm.oom;

/**
 *  栈默认的大小是 512-1024k; 栈被撑爆了
 */
public class StackOverflowErrorDemo {
    public static void main(String[] args) {
        stackOverflowError();
    }

    /**
     * StackOverflowErrorDemo.stackOverflowError(StackOverflowErrorDemo.java:9)
     *
     * 它是错误，不属于异常。
     */
    private static void stackOverflowError() {
        stackOverflowError();
    }
}
