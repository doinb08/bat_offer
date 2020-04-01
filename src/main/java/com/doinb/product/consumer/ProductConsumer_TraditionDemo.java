package com.doinb.product.consumer;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class ShareData1 {
    private int number = 0;
    private Lock lock = new ReentrantLock();
    // 新版写法，类似于 Synchronized
    private Condition condition = lock.newCondition();


    /**
     * 加法
     * @throws Exception ex
     */
    public void increment()throws Exception{
        // 提示
        Object object = new Object();
        //  这组等待，并非是jcu包下的，认清楚。
        //object.wait();
        //object.notify();

        lock.lock(); // 加锁
        try {
            // 1. 判断 深 透 明 细
            while (number != 0){ // 初始化需要干活 --注意：多线程的判断必须要while
            //if (number != 0){ // 错误的示范
                // 等待， 不能生产
                condition.await();
            }
            // 2. 干活
            number++;
            System.out.println(Thread.currentThread().getName()+"\t"+number);

            // 3.通知唤醒 -老版本的notify
            condition.signalAll();
        } finally {
            lock.unlock(); //解锁
        }
    }

    /**
     *  减法
     * @throws Exception ex
     */
    public void decrement()throws Exception{
        lock.lock(); // 加锁
        try {
            // 1. 判断 深 透 明 细
            while (number == 0){ // 初始化需要干活 --注意：多线程的判断必须要while
            //if (number == 0){ // 错误示范
                // 等待，不能生产
                condition.await();
            }
            // 2. 干活
            number--;
            System.out.println(Thread.currentThread().getName()+"\t"+number);

            // 3.通知唤醒 -老版本的notify
            condition.signalAll();
        } finally {
            lock.unlock(); //解锁
        }
    }

}

/**
 * @author doinb 实现多线程 1.0版本
 * @desc java.util.concurrent并发包下的知识点，统筹总结。
 * 题目：一个初始值为零的变量，两个线程对其交替操作，一个加1一个减1，来5轮。-- 传统版的生产者消费者
 *
 * 解题思路： 线程操作资源类，必须有资源类。高内聚，低耦合。
 * 1. 线程    操作(方法)    操作
 * 2. 判断    干活    通知
 * 3. 虚假唤醒
 */
public class ProductConsumer_TraditionDemo {
    public static void main(String[] args) {
        ShareData1 shareData = new ShareData1();

        // AAA线程做加法
        new Thread(()->{
            for (int i = 1; i <=5 ; i++) {
                try {
                    shareData.increment();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        },"AAA").start();

        // BBB线程做减法
        new Thread(()->{
            for (int i = 1; i <=5 ; i++) {
                try {
                    shareData.decrement();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        },"BBB").start();

        // CCC线程做加法
        new Thread(()->{
            for (int i = 1; i <=5 ; i++) {
                try {
                    shareData.increment();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        },"CCC").start();

        // DDD线程做减法
        new Thread(()->{
            for (int i = 1; i <=5 ; i++) {
                try {
                    shareData.decrement();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        },"DDD").start();
    }
}
