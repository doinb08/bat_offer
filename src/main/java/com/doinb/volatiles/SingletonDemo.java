package com.doinb.volatiles;

/**
 *  多线程情况下，容易出现的问题的单列模式 示范：
 */
public class SingletonDemo {

    // private static SingletonDemo instance = null;

    // DCL
    private static volatile SingletonDemo instance = null;

    private SingletonDemo() {
        System.out.println(Thread.currentThread().getName()+"\t 我是构造方法SingletonDemo()");
    }


    // DCL（double check lock双端检锁机制）
    public static SingletonDemo getInstanceDCL(){
        if (instance == null){
            synchronized(SingletonDemo.class){
                if (instance == null){
                    instance = new SingletonDemo();
                }
            }
        }
        return instance;
    }

    // 饿汉模式
    public static SingletonDemo getInstanceHunger(){
        if (instance == null){
            instance = new SingletonDemo();
        }
        return instance;
    }

    public static void main(String[] args) {

        for (int i = 1; i < 1000; i++) {
            new Thread(SingletonDemo::getInstanceDCL, String.valueOf(i)).start();
        }

    }
}
