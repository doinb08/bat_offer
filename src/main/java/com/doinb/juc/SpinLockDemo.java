package com.doinb.juc;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 *  @author doinb
 *  自旋锁（spinlock）
 *
 *  是指尝试获取锁的线程不会立即阻塞，而是采用循环的方式去尝试获取锁，
 *  这样的好处: 是减少线程上下文切换的消耗，缺点:是循环会消耗CPU。
 *
 *  题目：实现一个自旋锁
 *  自旋锁的好处：循环比较获取直到成功为止，没有类似wait的阻塞。
 *
 *  通过CAS操作完成自旋锁，A线程进来调用myLock方法自己持有锁5秒钟，B随后进来发现
 *  当前线程持有锁，不是null，所以只能通过自旋等待，直到A释放锁后B随后抢到。
 */
public class SpinLockDemo {
    // 原子整型、AtomicInteger
    // 原子引用线程
    AtomicReference<Thread> atomicReference = new AtomicReference<>();

    // 加锁
    public void myLock(){
        Thread thread = Thread.currentThread();
        System.out.println(Thread.currentThread().getName()+"\t come in o(^_^)o");
        // 循环比较当前原子线程是否为null
        int count = 0;
        while (!atomicReference.compareAndSet(null, thread)){
            // do nothing
            System.out.println("try to get lock "+ count++);
        }
    }

    // 解锁
    public void myUnLock(){
        Thread thread = Thread.currentThread();
        atomicReference.compareAndSet(thread, null);
        System.out.println(Thread.currentThread().getName()+"\t invoked myUnLock()");
    }


    public static void main(String[] args) {
        // 线程操作资源类
        SpinLockDemo spinLockDemo = new SpinLockDemo();

        new Thread(() -> {
            spinLockDemo.myLock();
            try {
                TimeUnit.SECONDS.sleep(5); // 占用线程5秒钟
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            spinLockDemo.myUnLock();
        }, "AA").start();

        // 主线程休眠1s
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 创建B线程
        new Thread(() -> {
            spinLockDemo.myLock();
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            spinLockDemo.myUnLock();
        }, "BB").start();
    }

}
