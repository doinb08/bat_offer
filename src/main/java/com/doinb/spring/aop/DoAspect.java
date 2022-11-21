package com.doinb.spring.aop;

import com.doinb.spring.controller.UserController;
import com.doinb.utils.IpRegionUtils;
import com.doinb.utils.ReactiveWebUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

@Aspect
@Component //让spring进行管理
@Slf4j
public class DoAspect {
    /**
     * 思考： 为什么这些切面会执行？
     */

    @Autowired
    IpRegionUtils ipRegionUtils;

    @Pointcut("execution(* com.doinb.spring.service.*.*(..))") // service包下的 所有接口，所有方法
    public void servicePointcut() {
    }

    @Pointcut("execution(* com.doinb.spring..*.*(..))") // com.doinb.spring 包下的所有方法
    public void pointcut() {
    }

    @Around(value = "com.doinb.spring.aop.DoAspect.pointcut()")
    public Object around(ProceedingJoinPoint point) throws Exception {
        long current = System.currentTimeMillis();
        Object proceed = null;
        try {
            proceed = point.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        long end = System.currentTimeMillis();
        log.info("【AOP环绕增强】执行消耗时间：{}", (end - current));
        String classType = point.getTarget().getClass().getName();
        Class<?> clazz = Class.forName(classType);
        // 获取类名
        String clazzName = clazz.getName();
        // 获取方法名
        Signature signature = point.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        String mName = methodSignature.getName();
        // 拦截的类名
        String myClazzName = UserController.class.getName();
        // 记录登录地址
        if (StringUtils.equals(clazzName, myClazzName)) {
            Method currentMethod = clazz.getMethod(mName, methodSignature.getParameterTypes());
            String methodName = currentMethod.getName();
            // 拦截指定类名
            Method myMethod = UserController.class.getMethod("login", String.class);
            String myMethodName = myMethod.getName();
            if (StringUtils.equals(methodName, myMethodName)) {
                RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
                HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
                String ip = ReactiveWebUtils.getRemoteAddress(request);
                String region = ipRegionUtils.getRegion(ip);
                log.info("IP所属区域：{}", region);
                // 实际项目中调用自定义的save(region)存入日志表
                log.info("记录登录日志成功");
            }
        }
        log.info("【AOP环绕增强】 end.");
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
