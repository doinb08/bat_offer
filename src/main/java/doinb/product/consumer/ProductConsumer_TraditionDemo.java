package doinb.product.consumer;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class ShareData{
    private int number = 0;
    private Lock lock = new ReentrantLock();

    public void increment()throws Exception{
        // 判断

    }

}

/**
 * @author doinb
 * @desc java.util.concurrent并发包下的知识点，统筹总结。
 * 题目：一个初始值为零的变量，两个线程对其交替操作，一个加1一个减1，来5轮。
 *
 * 解题思路： 线程操作资源类，必须有资源类。高内聚，低耦合。
 * 1. 线程    操作(方法)    操作
 * 2. 判断    干活    通知
 */
public class ProductConsumer_TraditionDemo {
    public static void main(String[] args) {

    }
}
