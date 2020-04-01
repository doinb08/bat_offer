package com.doinb.executor;

import com.doinb.juc.ReentrantLockDemo;
import com.doinb.juc.CallableDemo;

import java.util.concurrent.*;

/**
 * @author doinb
 * @desc 自定义过线程池
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
public class MyCustomThreadPoolExecutorDemo {
    public static void main(String[] args) {
        // CPU核数
        System.out.println(Runtime.getRuntime().availableProcessors());

        // 核心线程池
        int corePoolSize = 2;
        // 最大线程数，包含核心数
        int maximumPoolSize = 5;
        // 生存时间
        long keepAliveTime = 1L;
        // 单位秒
        TimeUnit unit = TimeUnit.SECONDS;
        // 阻塞，初始this(Integer.MAX_VALUE)容量，所以必须要初始化容量！！！ 候客区
        BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>(3);
        // 默认线程池工厂
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        // 拒绝策略
        // RejectedExecutionHandler handler = new ThreadPoolExecutor.AbortPolicy(); // RejectedExecutionException异常，生产环境不能使用！
        // RejectedExecutionHandler handler = new ThreadPoolExecutor.CallerRunsPolicy(); // 调用者运行机制，不会抛出异常，线程池满了，就交给main线程。
        // RejectedExecutionHandler handler = new ThreadPoolExecutor.DiscardOldestPolicy(); // 抛弃队列中等会最久的任务
        RejectedExecutionHandler handler = new ThreadPoolExecutor.DiscardPolicy(); // 直接丢弃任务

        // 自定义过线程池
        //ExecutorService threadPool = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
        ExecutorService threadPool = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);


        try{
            // 模拟10个用户来办理业务，每个用户就是一个来自外部的请求线程
            // 触发 RejectedExecutionException 异常，第9个线程，maximumPoolSize+workQueue = 8
            for (int i = 1; i <= 10; i++) {
                //开启连接
                threadPool.execute(()->{
                    System.out.println(Thread.currentThread().getName()+"线程\t 办理业务");
                });
                // 线程休眠一会儿
                //try { TimeUnit.MILLISECONDS.sleep(200); } catch (InterruptedException e) { e.printStackTrace(); }
            }
        } catch (Exception e){
            e.printStackTrace();
        }finally {
            threadPool.shutdown(); //关闭连接
        }
    }

    /**
     *
     * 问题：说说线程池的底层工作原理？
     * 1.在创建了线程池后，等待提交过来的任务请求。
     * 2. 当调用 execute()方法添加一个请求任务时，那么马上创建线程运行这个任务；
     *      2.1 如果正在运行的线程数量小于 corePoolSize，那么马上创建线程运行这个任务；
     *      2.2 如果正在运行的线程数量大于或等于 corePoolSize，那么将这个任务放入队列；
     *      2.3 如果这个时候队列满了且正在运行的线程数量还小于 maximumPoolSize,那么还是要创建非核心线程立刻运行这个任务；
     *      2.4 如果队列满了且正在运功行的线程数量大于或等于 maximumPoolSize,那么线程池会启动饱和拒绝策略来执行。
     * 3. 当一个线程完成任务时，它会从队列中取下一个任务来执行。
     * 4. 当一个线程无事可做超过一定的时间（keepAliveTime）时，线程池会断掉：
     *     4.1 如果当前运行的线程数大于 corePoolSize，那么这个线程就会被停掉。
     *     4.2 所以线程池的所有任务完成后它最终会收缩到 corePoolSize 的大小。
     *
     * 拒绝策略有四种；
     *  AbortPolicy（默认）：直行抛出 RejectedExecutionException 异常阻止系统正常运行。
     *  CallerRunsPolicy: “调用者运行”一种调节机制，该策略既不会抛弃任务，也不会抛出异常，而是将某些任务退回到调用者，从而降低任务的流量。
     *  DiscardOldestPolicy: 抛弃队列中等会最久的任务，然后把当前任务加入队列中尝试再次提交当前任务。
     *  DiscardPolicy: 直接丢弃任务，不予任何处理也不抛出异常。如果允许任务丢失，这是最后的一种方案。
     *
     * 问题：实际工作做Executors中JDK已经提供了，但是为什么一个都不用？
     * 思路： 线程池不允许使用Executors去创建，而是通过ThreadPoolExecutor的方式显示创建。
     *
     * 问题：你在工作中是如何使用线程池的，是否自定义过线程池的使用（摸底题）
     * 思路：MyCustomThreadPoolExecutorDemo。
     *
     * 蚂蚁金服原题：合理配置线程池参数你是如何考虑的？生产上如何设置？
     * 思路：
     * IO密集型，即任务需要大量的IO,即大量的阻塞。
     * 在单线程上运行IO密集型的任务会导致浪费大量的CPU运行能力在等待。
     * 所以在IO密集型任务中使用多线程可以大大的加速程序运行，即使在单核CPU上，这种加速主要就是利用了被浪费掉的阻塞时间。
     *
     * IO密集型时，大部分线程都阻塞，故需要多配置线程数：
     * 参考公式：CPU核数/(1 - 阻塞系数)； 比如：8核CPU: 8/(1 - 0.9) =80个线程数。
     *
     * CPU密集型的意思是该任务需要大量的运算，而没有阻塞，CPU一直全速运行。 CPU密集任务只有在真正的多核CPU上才可能得到加速。
     * CPU密集型任务配置尽可能少的线程数量：
     * 一般公式：CPU核数+1个线程的线程池
     *
     *
     * 需要根据几个值来决定
     * tasks ：每秒的任务数，假设为500~1000
     * taskcost：每个任务花费时间，假设为0.1s
     * responsetime：系统允许容忍的最大响应时间，假设为3s
     * queueCapacity = (coreSizePool/taskcost)*responsetime
     *  (2/0.1)*3 = 60
     *
     */
}
