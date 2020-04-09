package com.doinb;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class IoCTest {

    @Test
    public void test(){
        // xml方式
        ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");

//        context.getBean("fox");
        System.out.println("输出结果\t"+context.getBean("myFactoryBean")); // user MyFactoryBean的getObject()方法

        // 为什么加了&符号返回的是原生的
        System.out.println("输出结果\t"+context.getBean("&myFactoryBean")); // com.doinb.spring.ioc.MyFactoryBean@795509d9

        ((ClassPathXmlApplicationContext) context).close();

    }


    @Test
    public void test2(){
        // java Configuration
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DoAopApplication.class);
        System.out.println(context.getBean("fox"));
    }
}
