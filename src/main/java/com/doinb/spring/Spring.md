## spring AOP源码解读
### Spring常见面试资料：
> https://juejin.im/post/5e6d993cf265da575b1bd4af
> https://blog.csdn.net/a745233700/article/details/80959716

### SpringMVC常见面试题总结
> https://blog.csdn.net/a745233700/article/details/80963758

### Mybatis常见面试题总结
> https://blog.csdn.net/a745233700/article/details/80977133


### 什么是Spring AOP？
与OOP(Object Oriented Programming面向对象)对比，传统的OOP开发中的代码逻辑是自上而下
在这些自上而下的过程中会产生写横切性问题。而准备横切性的问题又与我们主业务逻辑关系不大。
会散落在代码的各个地方，造成难以维护。

AOP的编程思想就是把这些散落横切性的问题和主业务逻辑进行分离，从而起到解耦的目的。

### AOP对代码的增强有几种？
有五种增强策略：
前置增强
后置增强
环绕增强
抛出增强
引入增强

BeanPostProcessor后置处理器

底层AOP实现
DefaultAopProxyFactory createAopProxy()方法：
@Override
public AopProxy createAopProxy(AdvisedSupport config) throws AopConfigException {
    if (config.isOptimize() || config.isProxyTargetClass() || hasNoUserSuppliedProxyInterfaces(config)) {
        Class<?> targetClass = config.getTargetClass();
        if (targetClass == null) {
            throw new AopConfigException("TargetSource cannot determine target class: " +
                    "Either an interface or a target is required for proxy creation.");
        }
        if (targetClass.isInterface() || Proxy.isProxyClass(targetClass)) {
            return new JdkDynamicAopProxy(config);
        }
        return new ObjenesisCglibAopProxy(config);
    }
    else {
        return new JdkDynamicAopProxy(config);
    }
}

底层AOP是通过cglib或则java动态代理实现。
通过@EableAspactJAutoProxy注解可以指定 cglib代码或java动态代理。

那么AOP的cglib和java动态代理底层是如何实现的？
Proxy类中 Proxy.newProxyInstance(classLoader, proxiedInterfaces, this);

Proxy内部类中的ProxyClassFactory

// prefix for all proxy class names
private static final String proxyClassNamePrefix = "$Proxy";
// next number to use for generation of unique proxy class names
private static final AtomicLong nextUniqueNumber = new AtomicLong();

AOP的这个类ProxyGenerator是什么类？它是jvm的指令生成的字节码文件，


为什么java的动态代理必须是接口？
因为java是单继承多实现，且生成的代理对象继承了Proxy对象，为了不违背java设计原则，所以代理对象必须是接口；
所以java的动态代理必须是接口。





