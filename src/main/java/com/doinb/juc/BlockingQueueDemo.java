package com.doinb.juc;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author doinb
 *
 * 1.队列
 *
 * 2. 阻塞队列
 *  2.1 阻塞队列有没有好的一面？
 *
 *  2.2 不得不阻塞，你如何管理？
 *
 * @see SynchronousQueueDemo 子级队列
 */
public class BlockingQueueDemo {
    /**
     * 集合的高级类
     * ## ArrayBlockingQueue: 由数组结构组成的有界阻塞队列。 --线程池底层使用
     * ## LinkedBlockingDeque: 由链表结构组成的有界（但大小默认值为Integer.MAX_VALUE）阻塞队列。--线程池底层使用，慎用，因为有界范围太大。
     * PriorityBlockingQueue: 支持优先排序的无界阻塞队列。
     * DelayQueue: 使用优先级队列实现的延迟无界阻塞队列。
     * ## SynchronousQueue: 不储存元素的阻塞队列，也即单个元素的队列。 --线程池底层使用
     * LinkedTransferQueue: 由链表结构组成的无界阻塞队列。
     * LinkedBlockingQueue: 由链表结构组成的双向阻塞队列。
     *
     * BlockingQueue核心方法：
     *  抛出异常组：add(e), remove() element()
     *  特殊值：offer(e), poll(), peek()
     *  阻塞：put(), take()
     *  超时-折中，温柔的版本： offer(e, time, unit), poll(time, unit)
     */
    public static void main(String[] args) throws InterruptedException {
        // List list = new ArrayList();
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<String>(3);
        System.out.println(blockingQueue.offer("a",2L, TimeUnit.SECONDS));
        System.out.println(blockingQueue.offer("a",2L, TimeUnit.SECONDS));
        System.out.println(blockingQueue.offer("a",2L, TimeUnit.SECONDS));
        System.out.println(blockingQueue.offer("a",2L, TimeUnit.SECONDS));

        /*
        新增不进去，就等待，直到有空位
        blockingQueue.put("a");
        blockingQueue.put("b");
        blockingQueue.put("c");
        System.out.println("============");
        //blockingQueue.put("d");
        blockingQueue.take();
        blockingQueue.take();
        blockingQueue.take();
        //blockingQueue.take(); 消费不到就等待，直到有生产者
         */

        /*
        非异常组API
        System.out.println(blockingQueue.offer("a"));
        System.out.println(blockingQueue.offer("b"));
        System.out.println(blockingQueue.offer("c"));
        System.out.println(blockingQueue.offer("x"));

        System.out.println(blockingQueue.peek()); // 探测器，探测最顶端元素
        System.out.println(blockingQueue.poll()); // 取出队列中的元素
        System.out.println(blockingQueue.poll()); // 取出队列中的元素
        System.out.println(blockingQueue.poll()); // 取出队列中的元素
        System.out.println(blockingQueue.poll()); // 取出队列中的元素
        */

        /*
        异常组API
        System.out.println(blockingQueue.add("a"));
        System.out.println(blockingQueue.add("b"));
        System.out.println(blockingQueue.add("c"));
        System.out.println(blockingQueue.element());
        System.out.println(blockingQueue.remove());
        System.out.println(blockingQueue.remove());
        System.out.println(blockingQueue.remove());
        */
    }



}
