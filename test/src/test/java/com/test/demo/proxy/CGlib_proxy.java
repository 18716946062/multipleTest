package com.test.demo.proxy;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author zsy
 * @program : demo
 * date: 2021/8/27 13:59
 * Description: CGLIB 动态代理机制
 */
public class CGlib_proxy implements MethodInterceptor {
    /**
     * JDK 动态代理有一个最致命的问题是其只能代理实现了接口的类。为了解决这个问题，我们可以用 CGLIB 动态代理机制来避免。
     *
     * CGLIB(Code Generation Library)是一个基于ASM的字节码生成库，它允许我们在运行时对字节码进行修改和动态生成。CGLIB 通过继承方式实现代理。
     * 很多知名的开源框架都使用到了CGLIB， 例如 Spring 中的 AOP 模块中：如果目标对象实现了接口，则默认采用 JDK 动态代理，否则采用 CGLIB 动态代理。
     *
     * 在 CGLIB 动态代理机制中 MethodInterceptor 接口和 Enhancer 类是核心。你需要自定义 MethodInterceptor 并重写 intercept 方法，intercept 用于拦截增强被代理类的方法。
     * public interface MethodInterceptor extends Callback{
     *     // 拦截被代理类中的方法
     *     public Object intercept(Object obj, java.lang.reflect.Method method, Object[] args, MethodProxy proxy) throws Throwable;
     * }
     *
     *CGLIB 动态代理类使用步骤:
     *      1.obj :被代理的对象（需要增强的对象）
     *      2.method :被拦截的方法（需要增强的方法）
     *      3.args :方法入参
     *      4.methodProxy :用于调用原始方法
     *
     * 同于 JDK 动态代理不需要额外的依赖。CGLIB(Code Generation Library) 实际是属于一个开源项目，如果你要使用它的话，需要手动添加相关依赖。
     * <dependency>
     *   <groupId>cglib</groupId>
     *   <artifactId>cglib</artifactId>
     *   <version>3.3.0</version>
     * </dependency>
     *
     */

    /**
     * @param o 代理对象（增强的对象）
     * @param method 被拦截的方法（需要增强的方法）
     * @param args 方法入参
     * @param methodProxy 用于调用原始方法
     */
    @Override
    public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        //调用方法之前，我们可以添加自己的操作
        System.out.println("before method " + method.getName());
        Object object = methodProxy.invokeSuper(o, args);
        //调用方法之后，我们同样可以添加自己的操作
        System.out.println("after method " + method.getName());
        return object;
    }

    public static Object getProxy(Class<?> clazz) {
        // 创建动态代理增强类
        Enhancer enhancer = new Enhancer();
        // 设置类加载器
        enhancer.setClassLoader(clazz.getClassLoader());
        // 设置被代理类
        enhancer.setSuperclass(clazz);
        // 设置方法拦截器
        enhancer.setCallback(new CGlib_proxy());
        // 创建代理类
        return enhancer.create();
    }

    /** ------------------------------------------------------------------------------------------------------------- */
    public static void main(String[] args) {
        SmsServiceImpl smsService = (SmsServiceImpl) getProxy(SmsServiceImpl.class);
        smsService.send("java");
    }
}
