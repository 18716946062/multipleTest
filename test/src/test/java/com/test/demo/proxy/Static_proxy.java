package com.test.demo.proxy;

/**
 * @author zsy
 * @program : demo
 * date: 2021/8/27 13:59
 * Description: 静态代理
 */
public class Static_proxy implements SmsService {
    /** 静态代理中，我们对目标对象的每个方法的增强都是手动完成的（后面会具体演示代码），非常不灵活（比如接口一旦新增加方法，目标对象和代理对象都要进行修改）
     * 且麻烦(需要对每个目标类都单独写一个代理类)。 实际应用场景非常非常少，日常开发几乎看不到使用静态代理的场景。
     *
     * 静态代理实现步骤:
     *     1.定义一个接口及其实现类；
     *     2.创建一个代理类同样实现这个接口
     *     3.将目标对象注入进代理类，然后在代理类的对应方法调用目标类中的对应方法。这样的话，我们就可以通过代理类屏蔽对目标对象的访问，
     *       并且可以在目标方法执行前后做一些自己想做的事情。
     */
    private final SmsService smsService;

    public Static_proxy(SmsService smsService) {
        this.smsService = smsService;
    }

    @Override
    public String send(String message) {
        //调用方法之前，我们可以添加自己的操作
        System.out.println("before method send()");
        smsService.send(message);
        //调用方法之后，我们同样可以添加自己的操作
        System.out.println("after method send()");
        return null;
    }

    /** ------------------------------------------------------------------------------------------------------------- */
    public static void main(String[] args) {
        SmsServiceImpl smsService = new SmsServiceImpl();
        Static_proxy static_proxy = new Static_proxy(smsService);
        static_proxy.send("java");

    }
}
