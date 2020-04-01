package com.doinb.product.consumer;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author doinb    实现多线程 2.0版本
 *        // synchronized属于可重入锁，保证正常退出，系统级别
 * 题目：synchronized和Lock有什么区别？用新的lock有什么好处？你举例说说。
 * 1：原始构成
 *      synchronized是关键字属于JVM层面。
 *          monitorenter（底层是通过monitor对象来完成，其实wait/notify等方法也依赖于monitor对象只有在同步块或方法中才能调用wait/notify等方法）
 *          monitorexit
 *      Lock是具体类（java.util.concurrent.locks.Lock）是api层面的锁。
 *
 *2：使用方法
 * synchronized 不需要用户去手动释放，当synchronized代码执行完后系统会自动让线程释放对锁的占用。
 * ReentrantLock 则需要用户去手动释放锁，若没有主动释放锁，就有可能导致出现锁死现象。
 * 需要lock()和unlock()方法配合try/finally语句块来完成。
 *
 * 3：等待是否中断
 *  synchronized 不可中断，除非抛出异常或者正常运行完成。
 *  ReentrantLock 可中断，1.设置超时方法tryLock(long timeout, TimeUnit unit)  2.lockInterruptibly()代码块中，调用interrupt()方法可中断.
 *
 *  4. 加锁是否公平
 *  synchronized 非公平锁
 *  ReentrantLock 两者都可以，默认非公平锁，构造方法可以传入boolean值，true为公平锁，false为非公平锁。
 *
 *  5. 锁绑定多个条件Condition
 *  synchronized 没有
 *  ReentrantLock 用来实现分组唤醒需要唤醒的线程们，可以精确唤醒，而不是像synchronized要么随机唤醒一个线程，要么唤醒全部线程。
 *
 *  实现多线程口诀：
 *  0.资源类   1. 判断   2：干活  3：通知
 */

class ShareResource2 {
    private int number = 1; // A:1 B:2 C:3
    private Lock lock = new ReentrantLock();
    private Condition c1 = lock.newCondition();
    private Condition c2 = lock.newCondition();
    private Condition c3 = lock.newCondition();

    public void print(int printTimes, int lockNum){
        lock.lock();
        try {
            // 1. 判断 避免线程的虚假唤醒
            while (number != lockNum){
                // c1.await();
                switch (lockNum){
                    case 1:
                        c1.await(); //  1 号锁等待
                        break;
                    case 2:
                        c2.await(); //  2 号锁等待
                        break;
                    case 3:
                        c3.await(); //  3 号锁等待
                        break;
                }
            }
            //  2：干活 不满足while条件则打印
            for (int i = 1; i <= printTimes; i++) {
                System.out.println(Thread.currentThread().getName()+"\t"+i);
            }
            // 3：通知    A通知B  this.notify();无法保证能够正常通知。
            number = lockNum + 1; //修改标志位，避免再次使用c1，并通知c2
            if (number == 4){ // 如果number为4的时候，重新指向1号锁
                number = 1;
            }
            switch (number){
                case 1:
                    c1.signal(); // 通知 1 号锁
                    break;
                case 2:
                    c2.signal(); // 通知 2 号锁
                    break;
                case 3:
                    c3.signal(); // 通知 3 号锁
                    break;
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }
}

public class SyncAndReentrantLockDemo {

    public static void main(String[] args) {

        ShareResource2 shareResource = new ShareResource2();

        // AAA线程循环10次，每一次调用print 5次
        new Thread(()->{
            for (int i = 1; i <= 10 ; i++) {
                shareResource.print(5,1);
            }
        },"AAA").start();

        // BBB线程循环10次，每一次调用print 10次
        new Thread(()->{
            for (int i = 1; i <= 10 ; i++) {
                shareResource.print(10,2);
            }
        },"BBB").start();

        // CCC线程循环10次，每一次调用print 15次
        new Thread(()->{
            for (int i = 1; i <= 10 ; i++) {
                shareResource.print(15,3);
            }
        },"CCC").start();
    }
}
