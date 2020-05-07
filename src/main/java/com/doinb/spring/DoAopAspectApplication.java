package com.doinb.spring;

import com.doinb.spring.service.UserService;
import com.doinb.spring.service.UserServiceImpl;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author doinb
 * 通过代理调用login方法, 根据debug在login()方法处，查看返回的userService对象得知结果：
 * 1. JDKDynamicAopProxy 代理时 被代理的对象必须是接口
 * 2. CglibAopProxy 代理时，被代理的对象是普通的类
 */

@EnableAspectJAutoProxy
@ComponentScan("com.doinb.spring.*")
public class DoAopAspectApplication {

    public static void main(String[] args) {
        // 通过反射机制执行service中的login()方法
        // CglibAopProxy
        // AnnotationConfigApplicationContext 等同于 new ClassPathXmlApplication

        /**
         *  看源码技巧：过滤debug条件、带着问题看源码 ==> 找出代理对象 带返回值的方法才看，不带返回值可以略过，否则spring的源码几周时间都读不下来。
         *
         *  AOP动态代理源码入口：
         *  1. 点击 getBean 进入 AbstractApplicationContext。
         *  2. 点击 getBeanFactory().getBean(name)方法，进入AbstractBeanFactory 点击doGetBean()方法。
         *  3. 在点击 doGetBean()方法的此行代码处  Object sharedInstance = getSingleton(beanName) 打断点。
         *  4. 点击 getSingleton() DefaultSingletonBeanRegistry, debug断点打在 Object singletonObject = this.singletonObjects.get(beanName);
         *  5. 根据 singletonObjects.get() 推断，必定有put初始化方法，因此在 DefaultSingletonBeanRegistry 类中搜索  singletonObjects.put ; 找到 addSingleton()方法。
         *  6. addSingleton()方法中的 this.singletonObjects.put(beanName, singletonObject);就是IOC容器初始化，spring本身也有很多的类需要管理。
         *  7. 在 this.singletonObjects.put 处使用条件断点，鼠标在断点上右键输入：beanName.equals("userService") 查看singletonObject值；
         *  8. 保持断点不动，查看方法调用栈：定位到初始化调用栈，找到方法： sharedInstance = getSingleton(beanName, () -> {  接着 F7 进入断点，找到 getSingleton()方法。
         *  9. 在 DefaultSingletonBeanRegistry类中getSingleton()方法，逐行debug有返回值的仔细看，没返回值的忽略，定位到： singletonObject = singletonFactory.getObject(); 继续F7进入实现。
         * 10. 定位到 AbstractBeanFactory类的 createBean(beanName, mbd, args); 方法，继续 F8追踪，
         * 11. 在 AbstractAutowireCapableBeanFactory 类中createBean()方法找到 Object beanInstance = doCreateBean(beanName, mbdToUse, args) 就是代理的具体实现！
         * 12. F7进入doCreateBean()查看具体实现！ 继续追踪到 exposedObject = initializeBean(beanName, exposedObject, mbd);
         * 13. 此时的 bean 为 userServiceImpl, 当initializeBean()执行完成之后得到的 exposedObject 变成了 CglibAopProxy 对象。
         * 14. 此时已找到将 普通对象变为代理对象的方法， initializeBean() 继续F7方法跳转至内部实现。
         * 15. 追踪到 wrappedBean = applyBeanPostProcessorsAfterInitialization(wrappedBean, beanName);
         * 16. 在for循环中依次debug for (BeanPostProcessor processor : getBeanPostProcessors())，当 processor为：AnnotationAwareAspectJAutoProxyCreator时，F7进入processor.postProcessAfterInitialization(result, beanName)
         * 17. 查看 AbstractAutoProxyCreator 类中的 wrapIfNecessary(bean, beanName, cacheKey) 方法.
         * 18. F7 进入 Object proxy = createProxy(bean.getClass(), beanName, specificInterceptors, new SingletonTargetSource(bean));
         * 19. 继续追踪找到 createProxy()方法中, 找到调用栈 proxyFactory.getProxy(getProxyClassLoader())方法。F7 继续进入！
         * 20. 最后找到 DefaultAopProxyFactory 类中的 AopProxy createAopProxy(AdvisedSupport config)方法，找到最终实现代理！！！
         *
         *     带式动态代理的更底层的实现又是什么？ 接着21步骤追踪！
         * 21. JDK动态代理实现 找到 Proxy类， public static Object newProxyInstance()方法，找到 Class<?> cl = getProxyClass0(loader, intfs);
         * 22. 点击  proxyClassCache.get(loader, interfaces) 进入查看get实现。
         * 23. 找到  Object subKey = Objects.requireNonNull(subKeyFactory.apply(key, parameter)); apply方法，F7进入查看实现！
         * 24. 此时需要手动进入 apply(key, parameter)方法的实现，Proxy类的apply中随便打一个断点。
         * 25. 依次debug，直到代码 String proxyName = proxyPkg + proxyClassNamePrefix + num; 此时就是代理类创建的核心代码。
         *
         * 26. byte[] proxyClassFile = ProxyGenerator.generateProxyClass(proxyName, interfaces, accessFlags); 该方法就是通过底层字节码实现的。
         * 27. ProxyGenerator 是一个底层字节码， final byte[] var4 = var3.generateClassFile()
         *  AOP源码完结！
         */

        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(DoAopAspectApplication.class);

        //  通过jdk动态代理实现
        UserService userService = annotationConfigApplicationContext.getBean(UserService.class);

        // 通过Cglib动态代理实现
// UserServiceImpl userService = (UserServiceImpl) annotationConfigApplicationContext.getBean("userService");

        Object result = userService.login("admin");
        System.out.println(result);
    }

}

