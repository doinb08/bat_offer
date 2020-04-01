package com.doinb.juc;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author doinb
 * @see BlockingQueueDemo 父级对列
 */
public class SynchronousQueueDemo {
    public static void main(String[] args) {
        // 该同步队列的属性是，不存储元素，新增的元素没有被消费，则不让新增下一个元素。
        BlockingQueue<String> blockingQueue = new SynchronousQueue<>();

        new Thread(()->{
            try {
                System.out.println(Thread.currentThread().getName()+"\t put 1");
                blockingQueue.put("1");
                System.out.println(Thread.currentThread().getName()+"\t put 2");
                blockingQueue.put("2");
                System.out.println(Thread.currentThread().getName()+"\t put 3");
                blockingQueue.put("3");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"AAA").start();

        new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(3);
                System.out.println(Thread.currentThread().getName()+"\t take 1");
                blockingQueue.take();

                TimeUnit.SECONDS.sleep(3);
                System.out.println(Thread.currentThread().getName()+"\t take 2");
                blockingQueue.take();

                TimeUnit.SECONDS.sleep(3);
                System.out.println(Thread.currentThread().getName()+"\t take 3");
                blockingQueue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"BBB").start();

    }

}
