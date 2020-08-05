<h2 align="center"><code>2019-2020年互联网公司高频重点面试题</code></h2>

<br>
    <p align="center"><i>互联网公司Java高频面试指南</i></p>
<br>

<p align="center">
  <a href="https://github.com/doinb08/bat_offer.git">
    <img src="https://img.shields.io/badge/Branch-master-green.svg?longCache=true"
        alt="Branch">
  </a>
  <a href="https://github.com/doinb08/bat_offer/stargazers">
    <img src="https://img.shields.io/github/stars/doinb08/bat_offer.svg?label=Stars&style=social"
        alt="Stars">
  </a>
    <a href="https://github.com/doinb08/bat_offer/network/members">
    <img src="https://img.shields.io/github/forks/doinb08/bat_offer.svg?label=Forks&style=social"
        alt="Forks">
  </a>
  <a href="https://github.com/doinb08/bat_offer">
    <img src="https://img.shields.io/badge/License-GNU-blue.svg?longCache=true"
        alt="License">
  </a>
   <a href="https://github.com/doinb08/bat_offer">
   <img src="https://cdn.rawgit.com/sindresorhus/awesome/d7305f38d29fed78fa85652e3a63e154dd8e8829/media/badge.svg"
        alt="Awesome">
  </a>
</p>
<br>
    <div align="center">
      Created by
      <a href="https://github.com/doinb08">doinb</a>
    </div>
<br>

****

# 高级Java程序员的核心竞争力
> 1. Java基础扎实、掌握JVM原理、多线程、网络原理、设计模式、常用数据结构和算法。
> 2. 深入理解Spring,Spring MVC Mybatis等开源框架设计原理及底层架构，研究过部分核心功能源码。
> 3. 深入理解Redis线程模型，熟练掌握Redis的核心数据结构的使用场景，熟悉各种缓存高并发的使用场景，比如缓存雪崩，缓存穿透，缓存失效，热点缓存重建等。
> 4. 熟悉常见消息中间件的使用，解决过各种消息通信常见的疑难问题，比如消息丢失、消息重复消费、消息顺序性、大规模消息积压问题。
> 5. 对于高性能IO通信模型以及相关开源组件Netty等源码有过深度研究，熟悉Netty线程模型。 
> 6. 深入理解JVM底层原理，熟悉JVM垃圾收集器的使用以及核心参数的调优，有过一定的JVM线上调优经验，对JVM调优有自己独到的见解。
> 7. 深入理解Spring Boot, Spring Cloud, Alibaba Seata等微服务的设计原理及底层架构，研究过核心源码，熟悉微服务架构场景设计，比如服务发现与注册，服务限流、降级、熔断，服务网关路由设计，服务安全认证架构OAuth2协议,OpenFeign通信。
> 8. 在项目中解决过各种分布式场景的技术难题，比如分布式锁，分布式事务，分布式ID，分布式任务，数据的分库分表Sharding-JDBC。
> 9. 深入理解线程池的核心源码以及线程池核心参数配置。     

> 10.具备应用程序从构建、部署、测试和发布过程的自动化DevOps能力，如：Docker、K8s、Jenkins等技术栈。

## 本工程演示需要用到spring，故建立一个简易版的springboot工程，启动本工程必备环境：
>  1. jdk1.8_xx
>  2. maven 3.6.x
>  3. lombok插件
>  4. 启动DoApplication可访问简易版springboot工程, 使用AOP则启动DoAopApplication。

## java工具类库-提升编码效率
>https://github.com/looly/hutool  
https://apidoc.gitee.com/loolly/hutool/   
[前后端框架脚手架](https://blog.csdn.net/zj7321/article/details/81586244)

## 分库分表面试题
>http://www.imooc.com/article/301836  
https://juejin.im/post/5e48a9af6fb9a07cc200c203

## Spring Boot、Spring MVC 和 Spring 有什么区别？
* Spring最重要的特征是依赖注入。所有 SpringModules 不是依赖注入就是 IOC 控制反转。
当我们恰当的使用 DI 或者是 IOC 的时候，我们可以开发松耦合应用。松耦合应用的单元测试可以很容易的进行。
* Spring MVC 提供了一种分离式的方法来开发 Web 应用。
通过运用像 DispatcherServelet，MoudlAndView 和 ViewResolver 等一些简单的概念，开发 Web 应用将会变的非常简单。
* Spring 和 SpringMVC 的问题在于需要配置大量的参数。
* Spring Boot 通过一个自动配置和启动的项来目解决这个问题。为了更快的构建产品就绪应用程序，Spring Boot 提供了一些非功能性特征。

## 分布式事务解决方案
>[SpringCloudAlibabaSeata官网](https://seata.io/zh-cn)    

## 网络原理
>[面试 HTTP ，99% 的面试官都爱问这些问题](https://mp.weixin.qq.com/s/F_wrb6g1jnC_g903EHlS-w)

## Netty网络应用框架
>[Netty官网](https://netty.io)     
>[视频学习Netty](https://www.bilibili.com/video/BV17t41137su?p=2)  

## 面试专场
>[面试技巧-进大厂也就这回事](https://mp.weixin.qq.com/s/_LIPtYukiysDnsLc1erBhw)    
[面试技巧-如何把面试官的提问引导到自己准备好的范围内](https://www.cnblogs.com/JavaArchitect/p/12298114.html)    
[Redis面试题（2020最新版）](https://mp.weixin.qq.com/s?__biz=MzUxNDA1NDI3OA==&mid=2247486246&idx=1&sn=c9c670ce5d4e17d156bcc9e309efde98&chksm=f94a8acfce3d03d97a6dce52a86d9898dcfd8c25309e6121fa4b2aa6a9c4c8079c29e4099a70&scene=21#wechat_redirect)  
[Spring Boot面试题（2020最新版）](https://mp.weixin.qq.com/s?__biz=MzUxNDA1NDI3OA==&mid=2247486357&idx=1&sn=126e2f979906e823aad46c93d310d820&chksm=f94a8a7cce3d036af7e805eab62bc323581a02c13e4ed8a87b91b1324f3791fc08a0e81fd312&scene=21#wechat_redirect)  
[Tomcat面试题（2020最新版）](https://mp.weixin.qq.com/s?__biz=MzUxNDA1NDI3OA==&mid=2247486250&idx=1&sn=7de9aebd26dd06b5e27390f8cd5652b9&chksm=f94a8ac3ce3d03d5759d865d84052b675f9360b11369d4f44e63e815b6160e6d2ccd279464f0&scene=21#wechat_redirect)  
[Java虚拟机(JVM)面试题（2020最新版）](https://mp.weixin.qq.com/s?__biz=MzUxNDA1NDI3OA==&mid=2247486273&idx=1&sn=3bc11083a43590fa3cc3d1ad8361cbfc&chksm=f94a8aa8ce3d03be8b3454f1f06ebe505d829c0845b6bc25a8a98ae544e2696a1986372ea06d&scene=21#wechat_redirect)  
[Spring Cloud面试题（2020最新版）](https://mp.weixin.qq.com/s?__biz=MzUxNDA1NDI3OA==&mid=2247486233&idx=1&sn=f1b4fa8bc476e44ee86827157c32120c&chksm=f94a8af0ce3d03e689ed25cc7cb55aa2ba0cc7bcfb2bcd148fa347c5849744842cf344d2f89c&scene=21#wechat_redirect)  
[MySQL数据库面试题（2020最新版）](https://mp.weixin.qq.com/s?__biz=MzUxNDA1NDI3OA==&mid=2247486347&idx=1&sn=dcf2ac7b71f984abac1d0e717b82b1e5&chksm=f94a8a62ce3d03741fcdb61d3c62bf5f80911e8de6faf2ab1efcb2d7c6ddd64dbde65cdced88&scene=21#wechat_redirect)  
[消息中间件MQ与RabbitMQ面试题（2020最新版）](https://mp.weixin.qq.com/s?__biz=MzUxNDA1NDI3OA==&mid=2247486238&idx=1&sn=36f69624d21ffa6ef4815faabafbe9af&chksm=f94a8af7ce3d03e1bf469aaf880e0dc55a5b4989657efc0b13fd9533875bd29ccbfdff703eec&scene=21#wechat_redirect)   
[Java并发编程面试题（2020最新版）](https://mp.weixin.qq.com/s?__biz=MzUxNDA1NDI3OA==&mid=2247486353&idx=1&sn=5513d8c3e2821d35c553ab7c545102e4&chksm=f94a8a78ce3d036e2669429eb1c76e14db63a60d9f8179cde57d1d7690e903325f30a212b18b&scene=21#wechat_redirect)   
[Netty面试题（2020最新版）](https://mp.weixin.qq.com/s?__biz=MzUxNDA1NDI3OA==&mid=2247486254&idx=1&sn=83de4bdf603c7d68a861914b1e42ca54&chksm=f94a8ac7ce3d03d11f1009268875b6f93120e07ec18be97eb4ed64dd45a355c77541c0dd0223&scene=21#wechat_redirect)   
[Zookeeper超详细的面试题](https://mp.weixin.qq.com/s?__biz=MzUxNDA1NDI3OA==&mid=2247486207&idx=1&sn=7cf36dabd34c38db80b10f3b2682bb12&chksm=f94a8b16ce3d0200a8c10666b1f4ef05db230f7860087a24170b168f2c0960cb078b8744d574&scene=21#wechat_redirect)   
[Java经典面试题整理及答案详解（一）](https://mp.weixin.qq.com/s?__biz=MzUxNDA1NDI3OA==&mid=2247486222&idx=1&sn=fede2c994146afc17314df8e9699ee98&chksm=f94a8ae7ce3d03f1faecafa0a6d5b481997a29a91496bd8ca54fa9afd88f5c7117636ac506a6&scene=21#wechat_redirect)  
[面试官问：为什么需要消息队列？使用消息队列有什么好处？](https://mp.weixin.qq.com/s?__biz=MzUxNDA1NDI3OA==&mid=2247486211&idx=1&sn=28b470281c083d7453f08b1c3377b83f&chksm=f94a8aeace3d03fcf3ffefa2eca67ff4b4f9cd426b7b4ba547c03863b7b0712e1f20896abfd0&scene=21#wechat_redirect)  
[Spring MVC 面经](https://mp.weixin.qq.com/s?__biz=MzUxNDA1NDI3OA==&mid=2247486171&idx=1&sn=57c3feef86b0c7d5d049032c82eb2ed5&chksm=f94a8b32ce3d02248d797fa781fab04a8baa4d881415bfd88088f650cdb7b24204142ccf0154&scene=21#wechat_redirect)   
[Spring经典面试题总结](https://mp.weixin.qq.com/s?__biz=MzUxNDA1NDI3OA==&mid=2247486096&idx=1&sn=8a471403c996fb61b05bd631970d2e1b&chksm=f94a8b79ce3d026f881d382996db1a6875c1b6895dd12129c9091e690612c14611a56e69518b&scene=21#wechat_redirect)   
[史上最全多线程面试题 ！](https://mp.weixin.qq.com/s?__biz=MzUxNDA1NDI3OA==&mid=2247486083&idx=1&sn=b431e6e522fe202279b24ae80ddfdcff&chksm=f94a8b6ace3d027c5a5b21c7bce84ef879307f0e30f4b6398a3936b2e64f48fd569ed17b05ff&scene=21#wechat_redirect)    
[Docker入门视频教程](https://www.bilibili.com/video/BV1Vs411E7AR)      
[Docker详细讲解](http://www.iocoder.cn/Docker/good-collection/)    
[CORS跨域讲解](https://developer.mozilla.org/zh-CN/docs/Web/HTTP/Access_control_CORS)   
[MySQL索引面试题](https://www.jianshu.com/p/c82148473235)  
[数据库分库分表shardingJDBC-数据迁移难题](https://www.iteye.com/blog/mingkongeye-2300683)   

## 优秀开源项目推荐  
[Spring Cloud 系列项目学习案例](https://github.com/YunaiV/SpringBoot-Labs)     
[JAVA全套技术学习-芋道源码](http://www.iocoder.cn/?bilibili&BV1bE411p7As#)    

## MyBatis极速入门
[MyBatis官方使用文档](https://mybatis.org/mybatis-3/zh/dynamic-sql.html)   
[MyBatis源码-GitHub地址](https://github.com/mybatis/mybatis-3)    

## SpringCloud学习视频推荐
[2020尚硅谷《谷粒商城》-技术栈非常全面](https://www.bilibili.com/video/BV1QA41187m2)    
[微服务视频教程-尚硅谷2020最新版SpringCloud框架开发教程全套完整版从入门到精通](https://www.bilibili.com/video/BV18E411x7eT)      
[Spring源码专题视频学习资料](https://www.bilibili.com/video/BV1yE41187A3)   
[juc专题视频学习资料](https://www.bilibili.com/video/av68723403)  
