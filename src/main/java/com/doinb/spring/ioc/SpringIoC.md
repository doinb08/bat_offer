#Spring IoC专题
[Spring 官网](https://spring.io/projects/spring-framework)  
[Spring Github源码地址](https://github.com/spring-projects/spring-framework.git)
## 什么是IoC?
>IoC也称为依赖注入(dependency injection DI)。它是一个对象定义依赖关系的过程,即对象只通过构造函数参数、  
工厂方法的参数或对象参数实例构造或从工厂方法返回后在对象实例上设置的属性来定义它们所使用的其他对象。  
然后容器在创建bean时注入这些依赖项。这个过程本质上是bean本身的逆过程，因此称为控制反转(IoC),通过直接  
构造来控制其依赖项的实例化。

## 什么是bean?
>在Spring中,构成应用程序主干并由Spring IoC容器管理的对象称为bean。bean是由Spring IoC实例化、组装和管理的对象。

## 1. 为什么我们使用Spring?
>   1. 从spring ioc角度回答：统一负责对象的创建，管理生命周期，自动维护对象依赖关系（也就是依赖注入DI）。  
    依赖查找： byType byName  
    依赖注入：a.构造器传参 b.方法传参 c.属性 反射 field.set(x)  
    spring使用：  
    bean交给spring管理。  
    2. 从spring aop角度回答见下方： 什么是Spring AOP？


## 2. BeanFactory和FactoryBean有什么区别?
> BeanFactory指的是IOC容器的编程抽象，比如 ApplicationContext，XmlBeanFactory 等，  
这些都是 IOC 容器的具体表现，需要使用什么样的容器由客户决定,但 Spring 为我们提供了丰富的选择。  
FactoryBean 只是一个可以在 IOC 而容器中被管理的一个 Bean,是对各种处理过程和资源使用的抽象,FactoryBean  
在需要时产生另一个对象，而不返回 FactoryBean 本身,我们可以把它看成是一个抽象工厂，对它的调用返回的是  
工厂生产的产品。所有的 FactoryBean 都实现特殊的 org.springframework.beans.factory.FactoryBean 接口，当使用  
容器中 FactoryBean 的时候，该容器不会返回 FactoryBean 本身,而是返回其生成的对象。Spring 包括了大部分的  
通用资源和服务访问抽象的 FactoryBean 的实现，其中包括:对 JNDI 查询的处理，对代理对象的处理，对事务性  
代理的处理，对 RMI 代理的处理等，这些我们都可以看成是具体的工厂,看成是 Spring 为我们建立好的工厂。  
也就是说Spring通过使用抽象工厂模式为我们准备了一系列工厂来生产一些特定的对象,免除我们手工重复的工作，  
我们要使用时只需要在 IOC 容器里配置好就能很方便的使用了。
>
>在 Spring 中，有两个很容易混淆的类：BeanFactory 和 FactoryBean。 
 BeanFactory：Bean 工厂，是一个工厂(Factory)，我们 SpringIOC 容器的最顶层接口就是这个 BeanFactory，
 它的作用是管理 Bean，即实例化、定位、配置应用程序中的对象及建立这些对象间的依赖。  
 FactoryBean：工厂 Bean，是一个 Bean，作用是产生其他 bean 实例。通常情况下，这种 bean 没有什么特别的要  
 求，仅需要提供一个工厂方法，该方法用来返回其他 bean 实例。通常情况下，bean 无须自己实现工厂模式，Spring  
 容器担任工厂角色；但少数情况下，容器中的 bean 本身就是工厂，其作用是产生其它 bean 实例。  
 当用户使用容器本身时，可以使用转义字符”&”来得到 FactoryBean 本身，以区别通过 FactoryBean 产生的实例  
 对象和 FactoryBean 对象本身。在 BeanFactory 中通过如下代码定义了该转义字符：  
 StringFACTORY_BEAN_PREFIX="&";  
 如果 myJndiObject 是一个 FactoryBean，则使用&myJndiObject 得到的是 myJndiObject 对象，而不是 myJndiObject  
 产生出来的对象。

## 3. Spring提供了几种方式管理bean的生命周期? 有什么区别?
>Spring Bean 的生命周期简单易懂。在一个 bean 实例被初始化时，需要执行一系列的初始化操作以达到可用的状  
 态。同样的，当一个 bean 不在被调用时需要进行相关的析构操作，并从 bean 容器中移除。Spring bean factory  
 负责管理在 spring 容器中被创建的 bean 的生命周期。Bean 的生命周期由两组回调（call back）方法组成。  
 1.初始化之后调用的回调方法。  
 2.销毁之前调用的回调方法。  
Spring 框架提供了以下四种方式来管理 bean 的生命周期事件：  
1、InitializingBean 和 DisposableBean 回调接口  
2、针对特殊行为的其他 Aware 接口  
3、Bean 配置文件中的 Custom init()方法和 destroy()方法  
4、@PostConstruct 和@PreDestroy 注解方式  
使用 customInit()和 customDestroy()方法管理 bean 生命周期的代码样例如下：   
 `` <beans>
        <bean id="demoBean" class="com.doinb.vo.DemoBean" init-Method="customInit"   
            destroy-Method="customDestroy" >
        </bean>
    </beans>
 ``

## 4. 说说Ioc容器的加载过程?
>简单概括：
1.刷新预处理  
2.将配置信息解析，注册到BeanFactory  
3.设置bean的类加载器  
4.如果有第三方想再bean加载注册完成后，初始化前做点什么(例如修改属性的值，修改bean的scope为单例或者多例。)，提供了相应的模板方法，后面还调用了这个方法的实现，并且把这些个实现类注册到对应的容器中
5.初始化当前的事件广播器  
6.初始化所有的bean  
7.广播applicationcontext初始化完成。  
