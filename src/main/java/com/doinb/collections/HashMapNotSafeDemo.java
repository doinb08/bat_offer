package com.doinb.collections;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 集合类线程不安全问题
 * HashMap
 * java.util.ConcurrentModificationException
 */
public class HashMapNotSafeDemo {


    public static void main(String[] args) {

        // Map<String, Object> map = new HashMap<>();
        //Map<String, Object> map = Collections.synchronizedMap(new HashMap<>());
        Map<String, Object> map = new ConcurrentHashMap<>();

        for (int i = 1; i <= 3000; i++) {
            new Thread(()->{
                map.put(Thread.currentThread().getName(), UUID.randomUUID().toString().substring(0, 8));
                System.out.println(Thread.currentThread().getName()+"\t map:"+map);
            },"t"+i).start();
        }

        /**
         * 1 故障现象
         *  java.util.ConcurrentModificationException
         *
         *  2 导致原因
         *  并发争抢修改导致，参考花名册登记情况。
         *  一个人正在写入，另外一个同学过来争抢，导致数据不一致情况。并发修改异常；
         *
         *  3 解决方案
         *      3.1 Collections.synchronizedMap(new HashMap<>())
         *      3.2 ConcurrentHashMap
         *
         *
         *  4 优化建议？ 同样的错误不犯第二次
         *
         *
         */

    }
}
