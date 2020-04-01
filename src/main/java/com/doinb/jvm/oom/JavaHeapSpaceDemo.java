package com.doinb.jvm.oom;

import java.util.Random;

/**
 * Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
 *
 * -Xms10m -Xmx10m
 */
public class JavaHeapSpaceDemo {

    public static void main(String[] args) {
        // 创建一个大对象， 制造error, 堆占爆了
        byte[] bytes = new byte[80 * 1024 * 1024];
        String str = "doinb";
        while (true){
            str += str + new Random().nextInt(111111) + new Random().nextInt(222222);
        }
    }

}
