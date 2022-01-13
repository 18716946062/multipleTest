package com.test.demo.proxy;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author zsy
 * @program : demo
 * date: 2021/8/27 13:59
 * Description: JDK 动态代理
 */
public class Dynamic_proxy implements InvocationHandler {

    /**
     *
     * 在 Java 动态代理机制中 InvocationHandler 接口和 Proxy 类是核心。
     * public static Object newProxyInstance(ClassLoader loader, Class<?>[] interfaces, InvocationHandler h) throws IllegalArgumentException {
     *           ... ...
     *   }
     *这个方法一共有 3 个参数：
     *      loader :类加载器，用于加载代理对象。
     *      interfaces : 被代理类实现的一些接口；
     *      h : 实现了 InvocationHandler 接口的对象；
     *
     *要实现动态代理的话，还必须需要实现InvocationHandler 来自定义处理逻辑。 当我们的动态代理对象调用一个方法时候，这个方法的调用就会被转发到实现InvocationHandler 接口类的 invoke 方法来调用。
     *
     *JDK 动态代理类使用步骤：
     *       1.定义一个接口及其实现类；
     *       2.自定义 InvocationHandler 并重写invoke方法，在 invoke 方法中我们会调用原生方法（被代理类的方法）并自定义一些处理逻辑；
     *       3.通过 Proxy.newProxyInstance(ClassLoader loader,Class<?>[] interfaces,InvocationHandler h) 方法创建代理对象；
     *
     *
     *  JDK 动态代理有一个最致命的问题是其只能代理实现了接口的类。为了解决这个问题，可以用 CGLIB 动态代理机制来避免
     */

    /** 代理类中的真实对象 */
    private final Object target;

    public Dynamic_proxy(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 调用方法之前，我们可以添加自己的操作
        System.out.println("before method " + method.getName());
        Object result = method.invoke(target, args);
        // 调用方法之后，我们同样可以添加自己的操作
        System.out.println("after method " + method.getName());
        return result;
    }

    /**
     * 获取代理对象的工厂类
     *  getProxy() ：主要通过Proxy.newProxyInstance（）方法获取某个类的代理对象
     */
    public static Object getProxy(Object target) {
        Object proxy = Proxy.newProxyInstance(target.getClass().getClassLoader(), // 目标类的类加载
                target.getClass().getInterfaces(),  // 代理需要实现的接口，可指定多个
                new Dynamic_proxy(target)// 代理对象对应的自定义 InvocationHandler
        );
        return proxy;
    }

    /** ------------------------------------------------------------------------------------------------------------- */
    public static void main(String[] args) {
        SmsServiceImpl smsServiceImpl = new SmsServiceImpl();
        SmsService smsService = (SmsService) getProxy(smsServiceImpl);
        smsService.send("java");
    }
}
