package com.doinb.spring.aop;

import com.doinb.spring.service.UserService;
import sun.misc.ProxyGenerator;

import java.io.FileOutputStream;

/**
 * 模拟代理对象生成
 */
public class DoProxyTest {
    private static final String proxyClassNamePrefix = "$Proxy";

    public static void main(String[] args) {
        byte[] proxyClassFile = ProxyGenerator.generateProxyClass(proxyClassNamePrefix, new Class[]{UserService.class});

        try {
            FileOutputStream fileOutputStream = new FileOutputStream("DoProxy.class");
            fileOutputStream.write(proxyClassFile);
            fileOutputStream.flush();
            fileOutputStream.close();
            System.out.println("生成代理成功");
        } catch (Exception var) {
            var.printStackTrace();
        }
    }
}
