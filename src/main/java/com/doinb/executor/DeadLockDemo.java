package com.doinb.executor;

import java.util.concurrent.TimeUnit;

class HoldLockThread implements Runnable{
    private String lockA;
    private String lockB;

    public HoldLockThread(String lockA, String lockB) {
        this.lockA = lockA;
        this.lockB = lockB;
    }

    @Override
    public void run() {
        synchronized (lockA){
            System.out.println(Thread.currentThread().getName()+"\t 自己持有: "+lockA+"\t 尝试获得："+lockB);
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            synchronized (lockB){
                System.out.println(Thread.currentThread().getName()+"\t 自己持有: "+lockB+"\t 尝试获得："+lockA);
            }
        }
    }
}

/**
 * @author doinb
 * 死锁是指两个或两个以上的进程在执行过程中，因抢夺资源而造成的一种互相等待的现象，
 * 若无外力干涉那么它们都将无法推进下去。
 */
public class DeadLockDemo {

    public static void main(String[] args) {
        String lockA = "lockA";
        String lockB = "lockB";

        new Thread(new HoldLockThread(lockA, lockB), "ThreadAAA").start();
        new Thread(new HoldLockThread(lockB, lockA), "ThreadBBB").start();

        /**
         *
         *  定位死锁问题： see see easy, do it hard； 知易行难
         *
         * linux ps -ef|grep xxx    ls -l
         * windows 下的java程序 也有类似ps的查看命令，但是目前我们需要查看的只是java的进程: jps = java ps
         *
         * jps -l 查看当前进程号
         * jstack 5328 抓内存-排查底层故障;
         *
         *  故障的堆栈信息如下： ThreadBBB 线程想要获取waiting to lock <0x000000076bee14e0>对象，
         *  已持有locked <0x000000076bee1518>对象。
         *
         *  ThreadAAA 线程等待waiting to lock <0x000000076bee1518>， 获得locked <0x000000076bee14e0>

         Found one Java-level deadlock:
         =============================
         "ThreadBBB":
         waiting to lock monitor 0x000000001c9e4188 (object 0x000000076bee14e0, a java.lang.String),
         which is held by "ThreadAAA"
         "ThreadAAA":
         waiting to lock monitor 0x000000001c9e6808 (object 0x000000076bee1518, a java.lang.String),
         which is held by "ThreadBBB"

         Java stack information for the threads listed above:
         ===================================================
         "ThreadBBB":
         at doinb.executor.HoldLockThread.run(DeadLockDemo.java:25)
         - waiting to lock <0x000000076bee14e0> (a java.lang.String)
         - locked <0x000000076bee1518> (a java.lang.String)
         at java.lang.Thread.run(Thread.java:748)
         "ThreadAAA":
         at doinb.executor.HoldLockThread.run(DeadLockDemo.java:25)
         - waiting to lock <0x000000076bee1518> (a java.lang.String)
         - locked <0x000000076bee14e0> (a java.lang.String)
         at java.lang.Thread.run(Thread.java:748)

         Found 1 deadlock.
         */

    }
}
