package com.doinb.collections;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.*;

/**
 * 手写HashMap
 *
 * @author doinb
 */
public class MyHashMap {
    /**
     * 位桶数组 bucket array
     */
    Node[] table;
    /**
     * 存放的键值对的个数
     */
    int size;

    public MyHashMap() {
        // 长度一般定义成2的整数幂
        this.table = new Node[16];
    }

    public void put(Object key, Object value) {
        // 定义了新的节点对象
        Node newNode = new Node();
        newNode.hash = myHash(key.hashCode(), table.length);
        newNode.key = key;
        newNode.value = value;
        newNode.next = null;
        Node tmp = table[newNode.hash];

        // 正在遍历的最后一个元素
        Node iterLast = null;
        // key重复标记
        boolean keyRepeat = false;
        if (tmp == null) {
            // 数组元素为空，直接将新节点放进去
            table[newNode.hash] = newNode;
        } else {
            // 此处数组元素不为空，则遍历对应链表。
            while (tmp != null) {
                // 判断key如果重复，则覆盖
                if (tmp.key.equals(key)) {
                    System.out.println("key重复");
                    keyRepeat = true;
                    // 直接覆盖，其他的值（hash,key,next）不变。
                    tmp.value = value;
                    break;
                } else {
                    // key不重复，则遍历下一个。
                    iterLast = tmp;
                    tmp = tmp.next;
                }
            }
            // 没有发生key重复的情况，则添加到链表最后.
            if (!keyRepeat) {
                iterLast.next = newNode;
            }

        }
    }

    public static void main(String[] args) {
        // 测试MD5加密
        List<String> doc = new ArrayList<>();
        doc.add("google");
        doc.add("rinoob");
        String string = JSON.toJSONString(doc);
        System.out.println("string"+string);
        String sign = DigestUtils.md5Hex(string).toUpperCase();
        // E6721A175CD2D3DB5F3E6EAABC159E10
        // E6721A175CD2D3DB5F3E6EAABC159E10
        // E6721A175CD2D3DB5F3E6EAABC159E10  -- 多次加密结果一致
        System.out.println(sign);
        System.out.println("加密长度" + sign.length());

        Map<String, String> map = new HashMap<>();
        Hashtable<String, String> hashTable = new Hashtable<>();
        hashTable.put("google", "google.com");
        map.put("google", "google.com");
        map.put("rinoob", "runoob.com");

        ArrayList<String> sites = new ArrayList<>();

        sites.add("Runoob");
        sites.add("Google");
        sites.add("Wiki");
        sites.add("Taobao");
        System.out.println("网站列表: " + sites);
        // 将ArrayList转换为String类型
        String list = sites.toString();
        System.out.println("String: " + list);

        sites.removeIf(e ->{ return e.contains("Taobao");});

        ArrayList clone = (ArrayList)sites.clone();


        clone.forEach(e->{
            System.out.println(e + " clone ");
        });

        sites.forEach(e->{
            System.out.println(e + " ");
        });

//
//        MyHashMap hashMap = new MyHashMap();
//        hashMap.put("kc", "cc");
//        hashMap.put("kb", "bb");
//        hashMap.put("kd", "dd");
//        hashMap.put("ks", "ssss");
//
//        System.out.println(hashMap);
//
//        int hash = "kc".hashCode();
//        System.out.println(myHash(hash, 16));
    }

    // hash只是为了散列，作用一样，但并不是结果值一样。
    public static int myHash(int v, int length) {
        // 直接位运算，效率高
        System.out.println("hash in myHash:\t" + (v & (length - 1)));
        // 取模运算，效率低
        System.out.println("hash in myHash:\t" + (v % (length - 1)));
        return v & (length - 1);
    }

}
