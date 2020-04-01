package com.doinb.jvm;

import java.lang.ref.ReferenceQueue;
import java.util.HashMap;
import java.util.WeakHashMap;

/**
 * ReferenceQueue 引用队列；弱/虚引引用被回收的时候，先装载到队列里面，在回收引用。 它是弱/虚引用的通知机制。
 */
public class WeekHashMapDemo {
    public static void createHashMap(){
        HashMap<Integer, String> hashMap = new HashMap<>();
        Integer key = new Integer(1);
        String value = "hashMap";
        hashMap.put(key, value);
        System.out.println(hashMap);

        key = null;
        System.out.println(hashMap);

       // ReferenceQueue referenceQueue = new ReferenceQueue();

        System.gc();
        System.out.println(hashMap + "\t" +hashMap.size());
    }

    private static void createWeekHashMap() {
        WeakHashMap<Integer, String> weakHashMap = new WeakHashMap<>();
        Integer key = new Integer(2);
        String value = "weakHashMap";
        weakHashMap.put(key, value);
        System.out.println(weakHashMap);

        key = null;
        System.out.println(weakHashMap);

        System.gc();
        System.out.println(weakHashMap + "\t" + weakHashMap.size());
    }

    public static void main(String[] args) {
        //createHashMap();
        System.out.println("================");
        createWeekHashMap();
    }

}
