package com.doinb.spring.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component //让spring进行管理
@Slf4j
public class DoAspect {
    /**
     * 思考： 为什么这些切面会执行？
     *
     */

    @Pointcut("execution(* com.doinb.spring.service.*.*(..))") // service包下的 所有接口，所有方法
    public void pointcut() {
    }

    @Around(value = "com.doinb.spring.aop.DoAspect.pointcut()")
    public Object around(ProceedingJoinPoint point) {
        long current = System.currentTimeMillis();
        Object proceed = null;
        try {
            proceed = point.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        long end = System.currentTimeMillis();
        log.info("【AOP环绕增强】执行消耗时间：" + (end - current));
        return proceed;
    }

    @Before(value = "com.doinb.spring.aop.DoAspect.pointcut()")
    public void before() {
        log.info("【AOP前置增强】before...");
    }

    @After("com.doinb.spring.aop.DoAspect.pointcut() && args(name,..)")
    public void after(String name) {
        log.info(String.format("【AOP后置增强】after... %s", name));
    }

}
