package com.test.demo.aop;


import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Component
@Aspect
@Slf4j
public class LogAspect {

    /** bean方式定义切入点 */
    @Pointcut("execution(* com.test.demo.aop.AopService.*(..))")
    public void methods(){};

    /** 目标方法执行之前执行 */
    @Before("methods()")
    public void doBefore(JoinPoint joinPoint) {
        log.info("=====Before start=====");

        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        // 记录下请求内容
        log.info("URL:[{}]", request.getRequestURL().toString());
        log.info("HTTP_METHOD:[{}]", request.getMethod());
        log.info("IP:[{}]", request.getRemoteAddr());
        log.info("CLASS_METHOD:[{}]", joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        log.info("ARGS: {}", JSON.toJSONString(joinPoint.getArgs()));

        log.info("=====Before end=====");
    }

    /** 目标方法之后 */
    @After("methods()")
    public void doAfter(JoinPoint joinPoint) {
        log.info("=====After start=====");

        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        // 记录下请求内容
        log.info("URL:[{}]", request.getRequestURL().toString());
        log.info("HTTP_METHOD:[{}]", request.getMethod());
        log.info("IP:[{}]", request.getRemoteAddr());
        log.info("CLASS_METHOD:[{}]", joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        log.info("ARGS: {}", JSON.toJSONString(joinPoint.getArgs()));

        log.info("=====After end=====");
    }

}
