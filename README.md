# BAT_Offer
2019年互联网大厂高频重点面试题Demo

资料来自于B站，【真全栈程序员】 公开的视频学习资料。
视频地址：https://www.bilibili.com/video/av68723403

由于demo演示需要用到java包，故建立一个简易版的springboot工程，依赖需要：lombok插件。

启动该demo必备：
1. jdk1.8_xx
1. maven 3.6.x
2. lombok插件

java工具类库
https://github.com/looly/hutool
https://apidoc.gitee.com/loolly/hutool/

分库分表面试题：
http://www.imooc.com/article/301836
https://juejin.im/post/5e48a9af6fb9a07cc200c203


>Spring Boot、Spring MVC 和 Spring 有什么区别？
* Spring最重要的特征是依赖注入。所有 SpringModules 不是依赖注入就是 IOC 控制反转。
当我们恰当的使用 DI 或者是 IOC 的时候，我们可以开发松耦合应用。松耦合应用的单元测试可以很容易的进行。
* Spring MVC 提供了一种分离式的方法来开发 Web 应用。
通过运用像 DispatcherServelet，MoudlAndView 和 ViewResolver 等一些简单的概念，开发 Web 应用将会变的非常简单。
* Spring 和 SpringMVC 的问题在于需要配置大量的参数。
* Spring Boot 通过一个自动配置和启动的项来目解决这个问题。为了更快的构建产品就绪应用程序，Spring Boot 提供了一些非功能性特征。


分布式事务解决方案,官网 https://seata.io/zh-cn/
SpringCloud Alibaba Seata：
一个典型的分布式事务过程：分布式事务处理过程的一ID+三组件模型：
    Transaction ID XID 全局唯一的事务ID
    Transaction Coordinator(TC) TC - 事务协调者
    Transaction Manager(TM) TM - 事务管理器
    Resource Manager(RM)  RM - 资源管理器

