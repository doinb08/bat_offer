package com.doinb.collections;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * 集合类线程不安全问题
 * HashSet
 * java.util.ConcurrentModificationException
 */
public class HashSetNotSafeDemo {


    public static void main(String[] args) {

        /**
         *    // Dummy value to associate with an Object in the backing Map
         *    private static final Object PRESENT = new Object();
         *
         *    public boolean add(E e) {
         *         return map.put(e, PRESENT)==null;
         *     }
         */
        Set<String> set = new HashSet<>(); // 底层数据接口就是 HashMap default initial capacity (16) and load factor (0.75).
        set.add(""); //

        // Set<String> list = Collections.synchronizedSet(new HashSet<>());
        Set<String> list = new CopyOnWriteArraySet<>();

        for (int i = 1; i <= 300; i++) {
            new Thread(()->{
                list.add(UUID.randomUUID().toString().substring(0, 8));
                System.out.println(Thread.currentThread().getName()+"\t list:"+list);
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
         *      3.1 Collections.synchronizedList()
         *      3.2 CopyOnWriteArraySet 底层实现是 CopyOnWriteArrayList
         *
         *
         *  4 优化建议？ 同样的错误不犯第二次
         *
         *
         */

    }
}
