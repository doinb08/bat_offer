# BAT_OFFER
2019-2020年互联网大厂高频重点面试题

## 想要成为一名出色的程序员首先要具备如下能力
> 1. Java基础扎实、掌握JVM原理、多线程、网络原理、设计模式、常用数据结构和算法。
> 2. 深入理解Spring,Spring MVC Mybatis等开源框架设计原理及底层架构，研究过部分核心功能源码。
> 3. 深入理解Redis线程模型，熟练掌握Redis的核心数据结构的使用场景，熟悉各种缓存高并发的使用场景，比如缓存雪崩，缓存穿透，缓存失效，热点缓存重建等。
> 4. 熟悉常见消息中间件的使用，解决过各种消息通信常见的疑难问题，比如消息丢失、消息重复消费、消息顺序性、大规模消息积压问题。
> 5. 对于高性能IO通信模型以及相关开源组件Netty等源码有过深度研究，熟悉Netty线程模型。 
> 6. 深入理解JVM底层原理，熟悉JVM垃圾收集器的使用以及核心参数的调优，有过一定的JVM线上调优经验，对JVM调优有自己独到的见解。
> 7. 深入理解Spring Boot, Spring Cloud, Alibaba Seata等微服务的设计原理及底层架构，研究过核心源码，熟悉微服务架构场景设计，比如服务发现与注册，服务限流、降级、熔断，服务网关路由设计，服务安全认证架构OAuth2协议,OpenFeign通信。
> 8. 在项目中解决过各种分布式场景的技术难题，比如分布式锁，分布式事务，分布式ID，分布式任务，数据的分库分表Sharding-JDBC。
> 9. 深入理解线程池的核心源码以及线程池核心参数配置。  

## Spring源码专题视频学习资料视频地址: 
> https://www.bilibili.com/video/BV1yE41187A3

## JUC专题视频学习资料视频地址：
> https://www.bilibili.com/video/av68723403

## 本工程演示需要用到spring，故建立一个简易版的springboot工程，启动本工程必备环境：
>  1. jdk1.8_xx
>  2. maven 3.6.x
>  3. lombok插件
>  4. 启动DoApplication可访问简易版springboot工程, 使用AOP则启动DoAopApplication。

## java工具类库-提升编码效率
https://github.com/looly/hutool
https://apidoc.gitee.com/loolly/hutool/

## 分库分表面试题
http://www.imooc.com/article/301836
https://juejin.im/post/5e48a9af6fb9a07cc200c203

## Spring Boot、Spring MVC 和 Spring 有什么区别？
* Spring最重要的特征是依赖注入。所有 SpringModules 不是依赖注入就是 IOC 控制反转。
当我们恰当的使用 DI 或者是 IOC 的时候，我们可以开发松耦合应用。松耦合应用的单元测试可以很容易的进行。
* Spring MVC 提供了一种分离式的方法来开发 Web 应用。
通过运用像 DispatcherServelet，MoudlAndView 和 ViewResolver 等一些简单的概念，开发 Web 应用将会变的非常简单。
* Spring 和 SpringMVC 的问题在于需要配置大量的参数。
* Spring Boot 通过一个自动配置和启动的项来目解决这个问题。为了更快的构建产品就绪应用程序，Spring Boot 提供了一些非功能性特征。

## 分布式事务解决方案
[SpringCloudAlibabaSeata官网](https://seata.io/zh-cn)    

[面试技巧1](https://mp.weixin.qq.com/s/_LIPtYukiysDnsLc1erBhw)  
[面试技巧2](https://www.cnblogs.com/JavaArchitect/p/12298114.html)
