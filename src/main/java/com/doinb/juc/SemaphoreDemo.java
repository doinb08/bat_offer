package com.doinb.juc;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @author doinb
 *
 *  线程排序工具类
 *  SemaphoreDemo 信号量：拼多多使用，可代替synchronized和lock。
 *  争抢车位，秒杀系统常用类、
 *  两主要目的：一个是用于多个共享资源的互斥使用，另一个是用于并发线程数的控制。
 */
public class SemaphoreDemo {
    public static void main(String[] args) {
        // 模拟3个车位
        Semaphore semaphore = new Semaphore(3);
        // 模拟6部汽车
        for (int i = 1; i <= 6 ; i++) {
            new Thread(()->{
                try {
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName()+"\t抢到车位");
                    // 每个车停车3s
                    TimeUnit.SECONDS.sleep(3);
                    System.out.println(Thread.currentThread().getName()+"\t停车3秒后离开车位");
                }catch (InterruptedException e){
                    e.printStackTrace();
                }finally {
                    semaphore.release();
                }
            },String.valueOf(i)).start();

        }
    }
}
