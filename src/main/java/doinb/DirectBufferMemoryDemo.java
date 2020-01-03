package doinb;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author  wb
 * @create 2019-10-10
 *
 *  配置参数:
 *  -Xms10m -Xmx10m -XX:+PrintGCDetails -XX:MaxDirectMemorySize=5m
 *
 *  故障现象:
 *  Exception in thread "main" java.lang.OutOfMemoryError: Direct buffer memory
 *
 *
 */
public class DirectBufferMemoryDemo {

    public static void main(String[] args) throws InterruptedException {
        List<String> stringList = new ArrayList<>(32);
        Map<Integer, List<String>> map = new HashMap<>(32);
        for (int i = 1; i <= 10000; i++) {
            stringList.add(String.valueOf(i));
            map.put(i, stringList);
        }

        System.out.println(stringList);
        System.out.println(16 >> 1);
        System.out.println(1 << 4);
        System.out.println(1 << 30);

        //directBufferMemory();
/*        for (int i = 1; ; i++) {
            System.out.println("*********** i = " + i);
            new Thread(() ->{
                try {
                    Thread.sleep(Integer.MAX_VALUE);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            },""+i).start();
        }*/
    }

    private static void directBufferMemory() {
        // -Xms10m -Xmx10m
        // byte[] bytes = new byte[80 * 1024 * 1024]; //80MB Java heap space
        System.out.println("配置的MaxDirectMemory:" + (sun.misc.VM.maxDirectMemory()/(double)1024/1024) + "MB");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // -XX:MaxDirectMemorySize=5m 设置配置为5MB, 但实际使用6MB, 制造异常.
        ByteBuffer buffer = ByteBuffer.allocateDirect(6 * 1024 * 1024);
    }

}
