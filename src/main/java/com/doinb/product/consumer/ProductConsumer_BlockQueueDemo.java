package com.doinb.product.consumer;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 3.0版本-高并发版本 最有技术含量的生产者消费者Demo
 *
 * 多线程领域：所谓阻塞，在某些情况下会挂起线程(即阻塞)，一旦条件满足，被挂起的线程又自动被唤醒。
 * 为什么需要 BlockingQueue
 * 好处是我们不需要关心什么时候需要阻塞线程，什么时候需要唤醒线程，因为这一切 BlockingQueue 都一手包办好了。
 *
 * 在 concurrent 包发布以前，在多线程环境下，我们每个程序员都必须去自己控制这些细节，尤其还要兼顾效率和线程安全，而这会给我们的程序带来不小的复杂度。
 */
class ShareResource3{
    private volatile boolean FLAG = true; // 默认开启，进行生产+消费 -- 高并发下的程序,需使用volatile关键字，确保可见性。
    private AtomicInteger atomicInteger = new AtomicInteger(); // initial value {@code 0}, 高并发下使用 i++将会造成错乱，使用原子类。

    // 阻塞队列的接口都需要适配 底层实现使用了 ReentrantLock，因此使用阻塞队列实现生产者消费者的时候，不需要额外的加锁。
    BlockingQueue<String> blockingQueue = null;

    /**
     *  构造方法赋值
     * @param blockingQueue 接口实现类，或子类。
     */
    public ShareResource3(BlockingQueue<String> blockingQueue) {
        this.blockingQueue = blockingQueue;
        System.out.println(blockingQueue.getClass().getName()); // 查看完整的类名和包名
    }

    public void myProd() throws Exception{
        String data = null;
        boolean retValue;
        // 高并发使用while判断, 使用原子版的整型，保证原子性
        while (FLAG){
            data = atomicInteger.incrementAndGet() + "";
            retValue = blockingQueue.offer(data, 2L, TimeUnit.SECONDS);  // 实际工作中使用 offer() 方法，规避抛异常。
            if (retValue){
                System.out.println(Thread.currentThread().getName()+"\t 插入队列"+data+"成功");
            } else {
                System.err.println(Thread.currentThread().getName()+"\t 插入队列"+data+"失败");
            }
            TimeUnit.SECONDS.sleep(1); // 为了容易观察，休眠1s
        }
        System.out.println(Thread.currentThread().getName()+"\t 大老板叫停，表示FLAG=false，生产动作结束");
    }


    public void myConsumer() throws Exception {
        String result = null;
        // flag 同生同死
        while (FLAG){
            result = blockingQueue.poll(2L, TimeUnit.SECONDS);
            if(null == result || result.equalsIgnoreCase("")){
                System.out.println(Thread.currentThread().getName()+"\t 超过2秒钟没有取到数据，消费退出");
                System.out.println();
                return; // 必须结束
            }
            System.out.println(Thread.currentThread().getName()+"\t 消费队列数据"+result+"成功");
            System.out.println();
        }
    }

    /**
     *  停止线程的方法
     */
    public void stop() {
        this.FLAG = false;
    }
}

/**
 * 线程通信之生产者消费者阻塞队列版 3.0版本-高并发版本
 *
 *  volatile/CAS/atomicInteger/BlockQueue/线程交互/原子引用
 */
public class ProductConsumer_BlockQueueDemo {

    public static void main(String[] args) throws InterruptedException {
        ShareResource3 shareResource3 = new ShareResource3(new ArrayBlockingQueue<>(10));

        new Thread(()->{
            try {
                System.out.println(Thread.currentThread().getName()+"\t 生产者线程启动");
                shareResource3.myProd();
            } catch (Exception e) {
                e.printStackTrace();
            }
        },"Product").start();

        new Thread(()->{
            try {
                System.out.println(Thread.currentThread().getName()+"\t 消费者线程启动");
                System.out.println();
                shareResource3.myConsumer();
                System.out.println();
            } catch (Exception e) {
                e.printStackTrace();
            }
        },"Consumer").start();

        TimeUnit.SECONDS.sleep(5);
        System.out.println();
        System.out.println("5秒钟时间到，大老板main线程叫停，活动结束");

        shareResource3.stop(); // stop后，volatile flag 所有线程可见，马上就收到通知

    }
}
