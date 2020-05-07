package com.doinb.jvm.oom;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * Caused by: java.lang.OutOfMemoryError: Metaspace
 *
 * @author doinb
 * JVM参数
 * -XX:MetaspaceSize=8m -XX:MaxMetaspaceSize=16m -XX:+HeapDumpOnOutOfMemoryError
 *
 * Java 8及之后的版本使用Metaspace来替代永久代。
 *
 * Metaspace是方法区在HotSpot中的实现，它与持久代最大的区别在于：Metaspace 并不在虚拟机中而是使用本地内存，也即在java 8中，
 * class metadata(the virtual machines internal presentation of java class),被存储在叫做Metaspace的native memory。
 *
 * 永久代（java 8后被原空间Metaspace取代了）存放了以下信息:
 *
 * 虚拟机加载类的信息 rt包中String.class Object.class ArrayList.class
 * 常量池
 * 静态变量
 * 即使编译后的代码
 *
 * 模拟Metaspace空间溢出，我们不断生成类往元空间灌，类占据空间总是会超过Metaspace指定的空间大小的。
 */
public class MetaspaceOOMTest {

    static class OOMTest{

    }

    public static void main(String[] args) {
        int count = 0; // 计数器 1277次
        try {
            while (true){
                count++;

                // 生成一个类
                Enhancer enhancer = new Enhancer();
                enhancer.setSuperclass(OOMTest.class);
                enhancer.setUseCache(false);
                enhancer.setCallback(new MethodInterceptor() {
                    @Override
                    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                        return methodProxy.invokeSuper(o, args);
                    }
                });
                enhancer.create();

            }
        } catch (Throwable e){
            System.out.println("**************多少次之后发生了异常:"+count);
            e.printStackTrace();  // Caused by: java.lang.OutOfMemoryError: Metaspace
            throw e;
        }
    }

}
