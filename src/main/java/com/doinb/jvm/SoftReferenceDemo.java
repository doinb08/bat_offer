package com.doinb.jvm;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

/**
 * 软引用
 *
 *
 */
public class SoftReferenceDemo {

    /**
     * 内存够用的时候保留，不够用的时候回收！
     */
    public static void softRef_memory_enough(){
        Object object = new Object();
        SoftReference<Object> softReference = new SoftReference<>(object);
        // WeakReference<Object> softReference = new WeakReference<>(object);
        System.out.println(object);
        System.out.println(softReference.get());

        object = null;
        System.gc();
        System.out.println("===gc===");
        System.out.println(object);
        System.out.println(softReference.get());
    }

    /**
     * 内存不够用模拟，需要配置参数：
     *  -Xms5m -Xmx5m -XX:+PrintGCDetails
     *
     *  总结：
     *  强引用：发生oom都不回收。 Reference类
     *  软引用：内存够用就不回收，内存不够用就回收。SoftReference类
     *  弱引用：只要有GC就回收。WeekReference类。
     *  虚引用：
     */
    public static void softRef_memory_not_enough(){
        Object object = new Object();
        SoftReference<Object> softReference = new SoftReference<>(object);
        System.out.println(object);
        System.out.println(softReference.get());

        object = null;
        // System.gc();
        System.out.println("===gc===");

        try {
            byte[] bytes = new byte[80 * 1024 * 1024];
        } catch (Throwable e){
            e.printStackTrace();
        } finally {
            System.out.println(object);
            System.out.println(softReference.get());
        }
    }


    public static void main(String[] args) {
//        softRef_memory_not_enough();
        softRef_memory_enough();
    }
}
