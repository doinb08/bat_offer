package com.doinb.juc;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import static com.doinb.enums.CountryEnum.getCountryEnum;

/**
 * @author doinb
 * 需求: 班级中的6个同学全部离开教室后，主线程班长才能关门！
 * @see CyclicBarrierDemo 加法
 */
public class CountDownLatchDemo {


    //    -Xms10m -Xmx10m -XX:+PrintGCDetails
    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();
        try {
            execute();
            System.out.println("execute执行try方法");
        } finally {
            System.out.println(String.format("finally代码块，execute执行完成时间：%d 毫秒", (System.currentTimeMillis() - start)));
        }
//        futureTest();
//        unificationChina();
//        closeDoor();
//        exception();
    }

    private static void exception() throws Exception {
        int auditResubmitCheck = 2;
        try {
            System.out.println("try 方法执行了-111");
            int v = 10 / auditResubmitCheck;
            if (v == 5) {
                throw new RuntimeException("try方法抛出异常");
            }
            System.out.println("try 方法执行了-222");
        } catch (Exception e) {
            System.out.println("catch 方法执行了");
            throw e;
        } finally {
            System.out.println("finally 方法执行了");
        }
    }

    private static void execute() throws Exception {
        int cpuCore = Runtime.getRuntime().availableProcessors();
        System.out.println("CPU核数 cpuCore: " + cpuCore);
        ExecutorService pool = Executors.newFixedThreadPool(3);
        CountDownLatch countDownLatch = new CountDownLatch(10);
        long start = System.currentTimeMillis();
        for (int i = 1; i <= 10; i++) {
            int finalI = i;
            pool.execute(() -> {
                try {
                    System.out.println(Thread.currentThread().getName() + getCountryEnum(finalI) + "国，被灭 " + finalI);
                    if (finalI == 5) {
//                        throw new RuntimeException("子线程跑出异常啦");
                    }
                    TimeUnit.SECONDS.sleep(3);
                    sleep();
                } catch (Exception var) {
                    System.out.println("异常情况" + var.getMessage());
                } finally {
                    countDownLatch.countDown();
                }
            });
        }
        // 等待，减少数字达到0，放行。
        //countDownLatch.await();
        countDownLatch.await(10, TimeUnit.SECONDS);
        System.out.println("执行完成数：" + countDownLatch.getCount());
        pool.shutdown(); // 线程回收
        System.out.println("执行时间： " + (System.currentTimeMillis() - start) + "毫秒");
        System.out.println(Thread.currentThread().getName() + "\t **************秦帝国，统一华夏");
    }

    private static void sleep() {
        long l = System.currentTimeMillis();
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("sleep时间: " + (System.currentTimeMillis() - l));
    }

    private static void futureTest() throws Exception {
        ExecutorService pool = Executors.newFixedThreadPool(2);
        List<Future> futures = new ArrayList<>(8);
        CountDownLatch countDownLatch = new CountDownLatch(10);
        long start = System.currentTimeMillis();
        for (int i = 1; i <= 10; i++) {
            int finalI = i;
            futures.add(pool.submit(() -> {
                System.out.println(Thread.currentThread().getName() + getCountryEnum(finalI) + "国，被灭 " + finalI);
                countDownLatch.countDown(); // 扣减计数
                return finalI;
            }));
        }
        for (Future future : futures) {
            Object obj = future.get(1, TimeUnit.MINUTES);
            System.out.println("future执行结果：" + obj);
        }
        // 等待，减少数字达到0，放行。
        countDownLatch.await(2, TimeUnit.MINUTES);
        pool.shutdown(); // 线程回收
        System.out.println("执行时间： " + (System.currentTimeMillis() - start) + "毫秒");
        System.out.println(Thread.currentThread().getName() + "\t **************秦帝国，统一华夏");
    }

    /**
     * 减法  秦灭六国，一统华夏
     *
     * @throws InterruptedException ex
     */
    private static void unificationChina() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(6);
        for (int i = 1; i <= 6; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "国，被灭");
                countDownLatch.countDown();
            }, getCountryEnum(i)).start();
        }
        // 等待，减少数字达到0，放行。
        countDownLatch.await();
        System.out.println(Thread.currentThread().getName() + "\t **************秦帝国，统一华夏");
    }

    private static void closeDoor() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(6);
        for (int i = 1; i <= 6; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "\t上完自习，离开教室");
                countDownLatch.countDown();
            }, "thread" + String.valueOf(i)).start();
        }
        // 等待，减少数字达到0，放行。
        countDownLatch.await();
        System.out.println(Thread.currentThread().getName() + "\t **************班长最后关门走人");
    }
}
