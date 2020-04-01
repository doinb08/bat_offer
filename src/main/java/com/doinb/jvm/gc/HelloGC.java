package com.doinb.jvm.gc;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * 输入命令:
 * java -XX:+PrintCommandLineFlags -version
 *
 * -XX:InitialHeapSize=265097920 -XX:MaxHeapSize=4241566720 -XX:+PrintCommandLineFlags -XX:+UseCompressedClassPointers -XX:+UseCompressedOops -XX:-UseLargePagesIndividualAl
 * location -XX:+UseParallelGC
 * java version "1.8.0_212"
 * Java(TM) SE Runtime Environment (build 1.8.0_212-b10)
 * Java HotSpot(TM) 64-Bit Server VM (build 25.212-b10, mixed mode)
 */
public class HelloGC {

    /**
     *  -Xms10m -Xmx10m -XX:+PrintGCDetails -XX:PrintCommandLineFlags -XX:UseSerialGC
     *  配置GC收集器： -XX:+UseSerialGC
     * @param args
     */
    public static void main(String[] args) {
        ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap();
        System.out.println("*************");
        try {
            TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
