package com.doinb.juc;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Phone implements Runnable {
    public synchronized void sendSMS() {
        System.out.println(Thread.currentThread().getName()+ "\t invoked sendSMS()");
        sendEmail();
    }

    public synchronized void sendEmail(){
        System.out.println(Thread.currentThread().getName()+ "\t #####invoked sendEmail()");
    }

    // ==============================================================
    /**
     *  可重入锁底层原理：公平锁和非公平锁(默认)
     *  公平锁：是指多个线程按照申请锁的顺序来获取锁，类似排队打饭，先来后到。
     *  非公平锁：是指多个线程获取锁的顺序并不是按照申请锁的顺序，有可能后申请的线程比先申请的线程优先获取锁
     *            在高并发的情况下，有可能会造成优先级反转或者饥饿现象。优点：吞吐量比公平锁大。
     */
    private Lock lock = new ReentrantLock();

    @Override
    public void run() {
        getd();
    }

    private void getd() {
        /**
         *  https://www.bilibili.com/video/BV1pJ411M7mb?p=27 第17分钟详细详解
         *
         * 同时加两把锁会出现什么情况？  1.编译失败 2.运行时失败 3.执行成功
         * a.在锁配对的情况下：3.执行成功
         * b. 锁不配对的情况下：1.编译成功 2.执行时卡死
         */
        //lock.lock();
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName()+ "\t invoked getd()");
            setd();
        } finally {
            lock.unlock();
            // lock.unlock();
        }
    }

    private void setd() {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName()+ "\t ### invoked setd()");
        } finally {
            lock.unlock();
        }
    }
}

/**
 * @author doinb
 * 可重入锁（也叫做递归锁） 作用：避免死锁
 *
 * 指的是同一线程外层函数获得锁之后，内层函数仍然能获取该锁代码，
 * 在同一个线程在外层方法获取锁的时候，在进入内层方法会自动获取锁
 *
 * 也就是说，线程可以进入任何一个它已经拥有的锁所同步的代码块。
 *
 *  case 1 Synchronized就是一个典型的可重入锁
 * t1	 invoked sendSMS()          t1线程在外层方法获取锁的时候
 * t1	 #####invoked sendEmail()   t1在进入内层方法会自动获取锁
 *
 * t2	 invoked sendSMS()
 * t2	 #####invoked sendEmail()
 *
 *  case 2 ReentrantLock也是一个典型的可重入锁
 *
 */
public class ReentrantLockDemo {
    public static void main(String[] args) throws Exception {
        Phone phone = new Phone();

        new Thread(() -> {
            try {
                phone.sendSMS();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "t1").start();

        new Thread(() -> {
            try {
                phone.sendSMS();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "t2").start();

        // 暂停一会
        TimeUnit.SECONDS.sleep(1);
        System.out.println();
        System.out.println();
        System.out.println("### lock的启动方式，因为实现了Runnable接口 ###");

        // lock的启动方式，因为实现了Runnable接口
        Thread t3 = new Thread(phone, "t3");
        Thread t4 = new Thread(phone, "t4");
        t3.start();
        t4.start();
    }
}
