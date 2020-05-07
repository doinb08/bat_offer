package com.doinb.jvm.oom;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * 配置参数
 * -Xms10m -Xmx10m -XX:+PrintGCDetails -XX:MaxDirectMemorySize=5m -XX:+HeapDumpOnOutOfMemoryError
 *
 * 故障现象：
 * Exception in thread "main" java.lang.OutOfMemoryError: Direct buffer memory
 *
 * 导致原因：     ByteBuffer buffer = ByteBuffer.allocateDirect(6 * 1024 * 1024);
 * 写NIO程序经常使用ByteBuffer来读取或者写入数据，这是一种基于（Channel）与缓冲区（Buffer）的I/O方式，
 * 它可以使用Native函数库直接分配堆外内存，然后通过一个存储在Java堆里面的DirectByteBuffer对象作为这块内存的引用进行操作。
 * 这样能在一些场景中显著提高性能，因为避免了在Java堆和Native堆中来回复制数据。
 *
 * ByteBuffer.allocate(capability)第一种方式是分配JVM堆内存，属于GC管辖范围，由于需要拷贝所以速度相对较慢
 *
 * ByteBuffer.allocateDirect(capability)第一种方式是分配OS本地内存，不属于GC管辖范围，由于不需要内存拷贝所以速度相对较快。
 *
 * 但如果不断分配本地内存，堆内存很少使用，那么JVM就不需要执行GC，DirectByBuffer 对象们就不会被回收，
 * 这时候堆内存充足，但内地内存可能已经使用光了，再次尝试分配本地内存就会出现OutOfMemoryError，那么程序就直接崩溃了。
 */
public class DirectBufferMemoryDemo {

    public static void main(String[] args) throws InterruptedException {

        directBufferMemory();


        /**
         * JVM调优重要参数：
         *  java -XX:+PrintFlagsInitial  // 查看jvm初始参数命令
         *  java -XX:+PrintFlagsFinal  // 查看jvm修改过的参数命令  = （默认值） := （用户或jvm修改后的值）
         *  java -XX:+PrintCommandLineFlags
         *
         * 1. Boolean类型     2. KV设值类型
         * 虚拟机配置    -XX:+PrintGCDetails -XX:MetaspaceSize=1024m
         *
         * jps -l 查看java进程号
         *
         * jinfo -flag PrintGCDetails 25040  查看java某一类参数是否开启
         * -XX:+PrintGCDetails
         * -XX:+UseSerialGC 使用串行垃圾回收器.
         *
         * 查看元数据空间
         * jinfo -flag MetaspaceSize  打印： -XX:MetaspaceSize=21807104
         *
         * 查看多大年龄的数据可以被回收？
         * jinfo -flag MaxTenuringThreshold 76880
         *
         *  特别需要注意的两个常用参数：
         *   这两个参数属于XX参数
         *   -Xms 等价于 -XX:InitialHeapSize
         *   -Xmx 等价于 -XX:MaxHeapSize
         *
         *
         *  一般的常用参数有那些 ？
         *  -Xms1m -Xmx8m -Xss1024k -XX:MetaspaceSize=512m -XX:+PrintCommandLineFlags -XX:+PrintGCDetails -XX:+UseSerialGC
         *
         */

    }

    private static void directBufferMemory() {
        // -Xms10m -Xmx10m
         byte[] bytes = new byte[80 * 1024 * 1024]; //80MB Java heap space

        System.out.println("MaxDirectMemory:" + (sun.misc.VM.maxDirectMemory()/(double)1024/1024) + "MB");
        System.out.println("availableProcessors:" + Runtime.getRuntime().availableProcessors());
        System.out.println("totalMemory:" + (Runtime.getRuntime().totalMemory()/(double)1024/1024) + "MB");
        System.out.println("maxMemory:" + (Runtime.getRuntime().maxMemory()/(double)1024/1024) + "MB");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // -XX:MaxDirectMemorySize=5m 设置配置为5MB, 但实际使用6MB, 制造异常.
        ByteBuffer buffer = ByteBuffer.allocateDirect(6 * 1024 * 1024);
    }

}
