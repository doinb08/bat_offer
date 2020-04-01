package com.doinb.juc;

import java.util.concurrent.*;


class MyCallableThread implements Callable<Integer>{

    /**
     * 实现Callable接口的call()方法
     * @return 反正泛型中的类型值
     * @throws Exception ex
     */
    @Override
    public Integer call() throws Exception {
        System.out.println(Thread.currentThread().getName()+"线程**********come in Callable.");
        // 线程休眠一会儿
        try { TimeUnit.SECONDS.sleep(10); } catch (InterruptedException e) { e.printStackTrace(); }
        System.out.println(Thread.currentThread().getName()+"线程**********invoked Callable.");
        return 1024;
    }
}

/**
 * @author doinb
 *
 */
public class CallableDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        // 两个线程，一个main线程，一个是futureTask
        FutureTask<Integer> futureTaskA = new FutureTask<>(new MyCallableThread());
        FutureTask<Integer> futureTaskB = new FutureTask<>(new MyCallableThread());
        new Thread(futureTaskA, "futureTask-AA").start();
        new Thread(futureTaskA, "futureTask-BB").start(); // 相同的callable只会被调用一次，除非new一个新的

        // 模拟等待异步线程处理结果，合并结果反馈给用户
        System.out.println(Thread.currentThread().getName() + "*******");

        int result01 = 100;

        // 如有严格的编码规范，则使用while的写法，偷懒的写法 futureTaskA.get()同样也会判断
        int count = 1;
        while (!futureTaskA.isDone()){
            // your logic
            // 线程休眠一会儿
            try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }
            System.out.println(String.format("%s线程 \t等待中,第%d次进入自旋区", Thread.currentThread().getName(), count++));
        }

        // 要求获得Callable线程的计算结果，如果没有计算完成就要去强求，会导致整个main线程堵塞，直到计算完成。
        int result02 = futureTaskA.get() << 2; // << 位运算2位，等于乘以 2^2
        System.out.println("******result:"+(result01+result02));

        System.out.println("******result: 处理完成！");
    }
}
