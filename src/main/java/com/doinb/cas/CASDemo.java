package com.doinb.cas;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author doinb
 *
 * CAS是什么？ ===> compareAndSet(比较并且交换) 如果线程的期望值和物理内存的真实值一样，我就修改成为更新值。
 * 回答思路：
 *  1 CAS称为Compare-And-Swap，它是一条CPU并发原语。 它的功能是判断内存某个位置的值是否为预期值，如果是则更改为新的值，这个过程是原子的。
 *  2 CAS并发原语体现在java语言中的 sum.misc.Unsafe 类中的各个方法。调用Unsafe类中CAS方法，JVM会帮我们实现出 CAS汇编指令。这是一种完全依赖于
 *  硬件的功能，通过它实现了原子操作。强调，由于CAS是一种系统原语，原语属于操作系统用语范畴，是由若干指令组成的，由于完成某个功能的一个过程，
 *  并且原语的执行必须是连续的，在执行的过程中不允许被中断，也就是说CAS是一条CPU的原子指令，不会造成所谓的数据不一致问题。
 *
 *
 *  回答i++操作，在多线程操作下，不使用 synchronized 也能保证线程安全：
 *  1 是因为用的Unsafe类，rt.sun.misc包中，Unsafe类中的valueOffset，表示内存偏移地址，Unsafe根据内存地址获取数据。
 *  2 变量value使用volatile关键字修饰，保证了多线程之间的可见性。
 *
 */
public class CASDemo {

    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger(5); // 真实值，原始值

        // 从main线程拿到对象的值，然后比较并且交换
        System.out.println(atomicInteger.compareAndSet(5, 2019)+"\t current data: "+atomicInteger.get());
        System.out.println(atomicInteger.compareAndSet(2019, 1024<<1)+"\t current data: "+atomicInteger.get());
        System.out.println(atomicInteger.compareAndSet(5, 1024<<2)+"\t current data: "+atomicInteger.get());

        atomicInteger.getAndIncrement();


        /**
         *  CAS --> Unsafe --> CAS底层思想 --> ABA --> 原子引用更新 --> 如果规避ABA问题
         *
        // 为什么使用 CAS 而不采用 synchronized ？
        // 答： synchronized 同一时间段，只允许有一个线程访问，一致性得到保障，但是并发性下降。
        // cas重复的调用 this.compareAndSwapInt()方法，直到成功为止，即提高了并发性，又保证了一致性。

        // CAS缺点？
        // 从getAndAddInt()方法可以看出，有一个do while, 如果CAS失败，会一直尝试。如果cas长时间一直不成功，可能会给cpu带来很大的开销。
        // 总结： 1 循环时间长开销很大  2 只能保证一个共享变量的原子操作   3 引出来ABA问题？？？

         *    public final int getAndIncrement() {
         *        return unsafe.getAndAddInt(this, valueOffset, 1);
         *     }
         *     this 当前对象；
         *     valueOffset 内存偏移量
         *     1
         *
         *     Unsafe是rt.sun.misc包，内部变量 private volatile int value 使用volatile关键字。
         *
         *         public final int getAndAddInt(Object var1, long var2, int var4) {
         *         int var5;
         *         do {
         *             var5 = this.getIntVolatile(var1, var2);
         *         } while(!this.compareAndSwapInt(var1, var2, var5, var5 + var4));
         *
         *         return var5;
         *     }
         */

    }

}
