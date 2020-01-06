package doinb.product.consumer;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 3.0版本-高并发版本 最有技术含量的生产者消费者Demo
 */
class ShareResource3{
    private volatile boolean FLAG = true; // 默认开启，进行生产+消费 -- 高并发下的程序,需使用volatile关键字，确保可见性。
    private AtomicInteger atomicInteger = new AtomicInteger(); // initial value {@code 0}, 高并发下使用 i++将会造成错乱，使用原子类。

    // 阻塞队列的接口都需要适配
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
