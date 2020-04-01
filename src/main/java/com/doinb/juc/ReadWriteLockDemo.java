package com.doinb.juc;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 资源类
 */
class MyCache{
    private volatile Map<String, Object> map = new HashMap<>();
    // private Lock lock = new ReentrantLock(); 该锁能实现锁，但使用上有缺点：一个线程独占，无法共享读。

    // 可重入的读写锁
    private ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();

    public void put(String key, Object value){
        rwLock.writeLock().lock();
        try {
            System.err.println(Thread.currentThread().getName()+"\t 正在写入："+key);
            TimeUnit.MILLISECONDS.sleep(300);
            map.put(key,value);
            System.err.println(Thread.currentThread().getName()+"\t 写入完成：");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            rwLock.writeLock().unlock();
        }

    }

    public void get(String key){
        rwLock.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName()+"\t 正在读取：");
            try {
                TimeUnit.MILLISECONDS.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Object result = map.get(key);
            System.out.println(Thread.currentThread().getName()+"\t 读取完成："+result);
        } catch (Exception e){
            e.printStackTrace();
        }finally {
            rwLock.readLock().unlock();
        }
    }

    public void clearMap(){
        map.clear();
    }

}

/**
 * @author doinb
 *  独占锁(写锁)/共享锁(读锁)/互斥锁
 *
 * 独占锁：值该锁一次只能被一个线程所持有。对ReentrantLock和Synchronized而言都是独占锁。
 * 共享锁：值该锁可被多个线程所持有。
 * 对ReentrantReadWriteLock其读锁是共享锁，其写锁是独占锁。
 * 读锁的共享锁可保证并发读是非常高效的，读写，写读，写写的过程是互斥的。
 *  总结：
 *   读-读能共存
 *   读-写不能共存
 *   写-写不能共存
 *
 *   写操作：原子+独占，整个过程必须是一个完整的统一体，中间不允许被分割，被打断。
 */
public class ReadWriteLockDemo {
    //ReentrantReadWriteLock
    public static void main(String[] args) {
        MyCache myCache = new MyCache();
        // 写操作
        for (int i = 1; i < 5; i++) {
            final int tempInt = i;
            new Thread(()->{
                myCache.put(tempInt+"", tempInt+"");
            },String.valueOf(i)).start();
        }

        // 读操作
        for (int i = 1; i < 5; i++) {
            final int tempInt = i;
            new Thread(()->{
                myCache.get(tempInt+"");
            },String.valueOf(i)).start();
        }
    }
}
