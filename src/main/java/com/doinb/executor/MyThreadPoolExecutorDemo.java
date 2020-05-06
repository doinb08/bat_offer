package com.doinb.executor;

import com.doinb.juc.ReentrantLockDemo;
import com.doinb.juc.CallableDemo;

import java.util.concurrent.*;

/**
 *  @author doinb
 *
 *  此处为第4种获取/使用java多线程的方式--线程池。
 *
 *  第一种：继承 Thread 类,用start()方法启动多线程。
 *  @see ReentrantLockDemo 第二种：实现 Runnable 接口，重写run()方法，但是Runnable接口没有返回值，也不抛出异常。
 *  @see CallableDemo 第三种：实现 Callable 接口，重写call()方法，Callable有返回值，并且抛出异常，与 FutureTask 类配合使用。
 *  第四种：通过线程池，Executors工具类可获取。
 *
 * 线程池的学习，非常重要！
 * 2020-1-4周六，阿里巴巴电话面试：讲讲你了解的线程池？其中关键的几个参数代表什么意思？你是如何设置这些参数值的？
 */
public class MyThreadPoolExecutorDemo {
    public static void main(String[] args) {
        // 此处不是new，线程池里面已经初始化好了，直接获取即可，new会给jvm增加负担！ 漏洞！！！

        // 在线程池里面，固定线程数的池子！（一池固定数线程）  -- 执行长期的任务
         ExecutorService threadPool = Executors.newFixedThreadPool(20);

        // 单个线程 （一池一线程）
        ExecutorService threadPool2 = Executors.newSingleThreadExecutor();

        // 可扩容，带缓存的池子。（一池多线程） 缺点：初始化maximumPoolSize为Integer.MAX_VALUE
        ExecutorService threadPool3 = Executors.newCachedThreadPool();

        ExecutorService threadPool4 = Executors.newScheduledThreadPool(10);


        try{
            // 模拟10个用户来办理业务，每个用户就是一个来自外部的请求线程
            for (int i = 1; i <= 10; i++) {
                //开启连接
                threadPool.execute(()->{
                    System.out.println(Thread.currentThread().getName()+"线程\t 办理业务");
                });
                // 线程休眠一会儿
                try { TimeUnit.MILLISECONDS.sleep(200); } catch (InterruptedException e) { e.printStackTrace(); }
            }
        } catch (Exception e){
            e.printStackTrace();
        }finally {
            threadPool.shutdown(); //关闭连接
        }
    }

    /**
     *  ################## 线程池的七大参数详解 ##################
     *     public ThreadPoolExecutor(int corePoolSize,
     *                               int maximumPoolSize,
     *                               long keepAliveTime,
     *                               TimeUnit unit,
     *                               BlockingQueue<Runnable> workQueue,
     *                               ThreadFactory threadFactory,
     *                               RejectedExecutionHandler handler) {
     *         if (corePoolSize < 0 ||
     *             maximumPoolSize <= 0 ||
     *             maximumPoolSize < corePoolSize ||
     *             keepAliveTime < 0)
     *             throw new IllegalArgumentException();
     *         if (workQueue == null || threadFactory == null || handler == null)
     *             throw new NullPointerException();
     *         this.corePoolSize = corePoolSize;
     *         this.maximumPoolSize = maximumPoolSize;
     *         this.workQueue = workQueue;
     *         this.keepAliveTime = unit.toNanos(keepAliveTime);
     *         this.threadFactory = threadFactory;
     *         this.handler = handler;
     *     }
     *
     *   理解线程池参数可以将 线程池比作银行网点
     *   线程池 = 银行网点
     *   物理上的受理窗口，办理业务的窗口。
     *   BlockingQueue阻塞队列 -> 银行里面的候客厅。
     *   threadFactory默认 -> 银行卡网点的logo/工作人员的制服胸卡。
     *
     * corePoolSize： 线程池中的常驻核心线程数。
     *                 1. 在创建了线程之后，当有请求任务来之后，就会安排池中的线程去执行请求任务，近似理解为今日当值线程。
     *                 2. 当线程池中的线程数目达到corePoolSize之后，就会把达到的任务放到缓存队列当中。
     *
     * maximumPoolSize： 线程池能够容纳同时执行的最大线程数，此值必须大于等于1。
     *
     * keepAliveTime：多余的空闲线程的存活时间。当前线程池数量超过corePoolSize时，当空闲时间达到keepAliveTime值时，多余空闲线程会被销毁直到只剩下corePoolSize个线程为止。
     *                默认情况下：只有当线程池中的线程数大于corePoolSize时keepAliveTime才会起作用，直到线程池中的线程数不大于corePoolSize。
     *
     * unit： keepAliveTime的单位，秒，分，时。
     *
     * workQueue: 任务队列，被提交但尚未被执行的任务。
     *
     * threadFactory：表示生成线程池中工作线程的线程工厂，用于创建线程一般默认的即可。
     *
     * RejectedExecutionHandler：拒绝策略，表示当队列满了并且工作线程大于等于线程的最大线程数。
     *
     */
}
