package com.doinb.jvm.oom;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author doinb
 * JVM参数配置演示：
 * -Xms10m -Xmx10m -XX:+PrintGCDetails -XX:MaxDirectMemorySize=5m
 *
 * GC回收时间过长时会抛出 OutOfMemroyError。过长的定义是：超过90%的时间用来做GC并且回收了不到2%的堆内存，
 * 连续多次GC都只回收了不到2%的极端情况下才会抛出。假如不抛出GC overhead limt 错误会发生什么情况呢？
 * 发生GC清理的内存很快会再次填满，迫使GC再次执行，这样就形成恶性循环，CPU使用率一直是100%，而GC却没有任何成果。
 */
public class GCOverheadDemo {

    /**
     * 查看GC小技巧： 【名称】【GC前内存占用】->【GC后内存占用】【该区内存总大小】
     * [Full GC (Ergonomics) [PSYoungGen: 2047K->0K(2560K)] [ParOldGen: 7115K->633K(7168K)] 9163K->633K(9728K), [Metaspace: 3081K->3081K(1056768K)], 0.0126975 secs] [Times: user=0.16 sys=0.00, real=0.01 secs]
     *
     *
     * Exception in thread "main" java.lang.OutOfMemoryError: GC overhead limit exceeded
     */
    public static void main(String[] args) {
        int i = 0;
        // List<String> list = Collections.synchronizedList(new ArrayList<>());
        // List<String> list = new Vector<>();
         List<String> list = new ArrayList<>();
         try {
             while (true){
                 list.add(String.valueOf(++i).intern());
                 //System.out.println();
             }
         } catch (Throwable e){
             System.out.println("**************i:"+i);
             e.printStackTrace(); // Exception in thread "main" java.lang.OutOfMemoryError: GC overhead limit exceeded
             throw e;
         }
    }
}
