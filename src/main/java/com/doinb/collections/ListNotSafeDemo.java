package com.doinb.collections;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 集合类线程不安全问题
 * ArrayList
 * java.util.ConcurrentModificationException
 */
public class ListNotSafeDemo {


    public static void main(String[] args) {
        /**
         *  创建一个空的数组，默认值为10 = DEFAULT_CAPACITY
         *      private static int calculateCapacity(Object[] elementData, int minCapacity) {
         *         if (elementData == DEFAULTCAPACITY_EMPTY_ELEMENTDATA) {
         *             return Math.max(DEFAULT_CAPACITY, minCapacity);
         *         }
         *         return minCapacity;
         *     }
         */
//        List<String> list = new ArrayList<>();
//        List<String> list = new Vector<>();  加锁机制，可以控制并发性，性能差。
//        list.add("a");
//        list.add("b");
//        list.add("c");
//        list.add("d");
        // list.forEach(System.out::println);

        // List<String> list = Collections.synchronizedList(new ArrayList<>()); 加锁机制

        List<String> list = new CopyOnWriteArrayList<>();

        for (int i = 1; i <= 30000; i++) {
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
         *      3.1 Vector<>()控制并发。
         *      3.2 Collections.synchronizedList()
         *      3.2 CopyOnWriteArrayList 写时复制 读写分离的思想； CopyOnWrite容器即写时复制的容器。往一个容器添加元素的时候，
         *      不直接往当前容器Object[] newElement里添加元素，添加完元素后，再将原容器的引用指向新的容器 setArray(newElement);
         *      这样做的好处是可以对CopyOnWrite容器进行并发的读，而不需加锁，因为当前容器不会添加任何元素。所以CopyOnWrite容器也是
         *      一种读写分离的思想，读和写不同的容器。
         *      public boolean add(E e) {
         *         final ReentrantLock lock = this.lock;
         *         lock.lock();
         *         try {
         *             Object[] elements = getArray();
         *             int len = elements.length;
         *             Object[] newElements = Arrays.copyOf(elements, len + 1);
         *             newElements[len] = e;
         *             setArray(newElements);
         *             return true;
         *         } finally {
         *             lock.unlock();
         *         }
         *     }
         *
         *  4 优化建议？ 同样的错误不犯第二次
         *
         *
         */

    }
}
