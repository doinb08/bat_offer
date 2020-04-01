package com.doinb.volatiles;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author doinb
 *
 *  volatile是java虚拟机提供的轻量级的同步机制(轻量级的synchronized)，基本遵守JMM规范主要如下：
 *  1 保证可见性 -- 某一个线程修改了主物理内存的值后，需立即通知其他线程，称为可见性。
 *  2 不保证原子性
 *  3 禁止指令重排
 *
 *  JMM（java内存模型Java Memory Model）它描述的是一组规范或规则。
 *
 *  JMM关于同步的规定：
 *  1 线程解锁前，必须把共享变量的值刷新回主内存。
 *  2 线程加锁前，必须读取主内存的最新值到自己的工作内存。
 *  3 加锁解锁是统一把锁。
 *
 */
class MyData{
    volatile int number = 0;

    public void addT60(){
        this.number = 60;
    }

    // 请注意，number前面加了volatile关键字修饰，但是volatile不保证原子性,出现写覆盖; 添加 synchronized 可保证原子性
    public void addPlusPlus(){
        number++;
    }


    // 创建一个原子类，默认初始值为0 -- 引入 CAS 自旋锁概念！非常重要
    AtomicInteger atomicInteger = new AtomicInteger();
    public void addMyAtomic(){
        atomicInteger.getAndIncrement(); //类似 i++
    }
}

public class VolatileDemo {
    /**
     *  1 验证 volatile 的可见性
     *      1.1 加入 int number = 0,number变量之前根本没有添加volatile关键字修饰,没有可见性
     *      1.2 添加了 volatile，可以解决可见性问题。
     *
     *  2 验证 volatile 不保证原子性
     *      2.1 原子性指的是什么意思？
     *      不可分割，完整性，也即某个线程正在做某个具体业务时，中间不可以被加塞或者被分割。需要整体完整，要么同时成功，要么同时失败。
     *      2.2 volatile 不保证原子性
     *      2.3 why 查看jvm字节码
     *      2.4 如何解决原子性？   #加synchronized，杀鸡用牛刀； # AtomicInteger可解决
     *
     *
     *
     */
    public static void main(String[] args) {
        MyData myData = new MyData();

        for (int i = 1; i <= 20; i++) {
            new Thread(()->{
                for (int j = 1; j <= 1000; j++) {
                    myData.addPlusPlus();
                    myData.addMyAtomic();
                }
            },"doinb_"+i).start();
        }

        // 需要等到上面20个线程全部计算完成后，再用main线程取得最终的结果值看是多少？
        while (Thread.activeCount() > 2){ // 一个程序，默认有两个线程，一个是main线程，一个是gc线程。
            Thread.yield(); // 主线程不执行
        }
        System.out.println(Thread.currentThread().getName()+"\t int type, finally number value: "+myData.number);
        System.out.println(Thread.currentThread().getName()+"\t atomicInteger type, finally number value: "+myData.atomicInteger);
    }


    // 可以保证可见性，及时通知其他线程，主物理内存的值已经被修改。
    private static void seeOkByVolatile() {
        MyData myData = new MyData();

        new Thread(()->{
            System.out.println(Thread.currentThread().getName()+"\t come in");
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            myData.addT60();
            System.out.println(Thread.currentThread().getName()+"\t update number value: "+myData.number);
        },"AAA").start();

        while (myData.number == 0){
            // main线程就一直在这里等待循环，直到number值不再等于零；
        }

        System.out.println(Thread.currentThread().getName()+"\t mission is over, main get number value: "+myData.number);
    }
}
