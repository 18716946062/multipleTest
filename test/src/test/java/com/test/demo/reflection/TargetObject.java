package com.test.demo.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TargetObject {
    /**
     * 获取 Class 对象的四种方式:
     *      如果我们动态获取到这些信息，我们需要依靠 Class 对象.Class 类对象将一个类的方法、变量等信息告诉运行的程序。Java 提供了四种方式获取 Class 对象:
     *      1.知道具体类的情况下可以使用：
     *      Class alunbarClass = TargetObject.class;
     *      但是我们一般是不知道具体类的，基本都是通过遍历包下面的类来获取 Class 对象，通过此方式获取 Class 对象不会进行初始化
     *
     *      2.通过 Class.forName()传入类的路径获取：
     *      Class alunbarClass1 = Class.forName("cn.javaguide.TargetObject");
     *
     *      3.通过对象实例instance.getClass()获取：
     *      TargetObject o = new TargetObject();
     *      Class alunbarClass2 = o.getClass();
     *
     *      4.通过类加载器xxxClassLoader.loadClass()传入类路径获取:
     *      Class clazz = ClassLoader.loadClass("cn.javaguide.TargetObject");
     *      通过类加载器获取 Class 对象不会进行初始化，意味着不进行包括初始化等一些列步骤，静态块和静态对象不会得到执行
     */

    private String value;

    public TargetObject() {
        value = "JavaGuide";
    }

    public void publicMethod(String s) {
        System.out.println("I love " + s);
    }

    private void privateMethod() {
        System.out.println("value is " + value);
    }

    /** ------------------------------------------------------------------------------------------------------------- */
    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException,
            NoSuchMethodException, InvocationTargetException, NoSuchFieldException {

        /** 获取TargetObject类的Class对象并且创建TargetObject类实例 */
        Class<?> clazz = Class.forName("com/test/demo/reflection/TargetObject");
        //Class<TargetObject> clazz = TargetObject.class;
        //Class<? extends TargetObject> clazz = new TargetObject().getClass();
        TargetObject targetObject = (TargetObject) clazz.newInstance();
        /** 获取所有类中所有定义的方法 */
        Method[] declaredMethods = clazz.getDeclaredMethods();
        for (Method declaredMethod : declaredMethods) {
            System.out.println(declaredMethod.getName());
        }
        /** 获取指定方法并调用 */
        Method publicMethod = clazz.getDeclaredMethod("publicMethod", String.class);
        publicMethod.invoke(targetObject, "JavaGuide");
        /** 获取指定参数并对参数进行修改 */
        Field field = clazz.getDeclaredField("value");
        //为了对类中的参数进行修改我们取消安全检查
        field.setAccessible(true);
        field.set(targetObject, "JavaGuide");
        /** 调用 private 方法 */
        Method privateMethod = clazz.getDeclaredMethod("privateMethod");
        //为了调用private方法我们取消安全检查
        privateMethod.setAccessible(true);
        privateMethod.invoke(targetObject);
    }

}
