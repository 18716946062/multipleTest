package com.test.mytest.basic;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author: ZSY
 * @program: multipleTest
 * @date 2022/2/10 15:21
 * @description:
 */

@Slf4j
@SpringBootTest
public class JavaBasicTest {

    /**
     * == 和 equals 的区别是什么:
     *  == 对于基本类型来说是值比较，对于引用类型来说是比较的是引用；而 equals 默认情况下是引用比较，只是很多类重新了 equals 方法，比如
     *  String、Integer 等把它变成了值比较，所以一般情况下equals 比较的是值是否相等。
     */
    @Test
    public void test1() {
        Integer i1 = new Integer(1);
        Integer i2 = 1;
        Integer i3 = new Integer(1);
        System.out.println(i1 == i2); // flase
        System.out.println(i1.equals(i2)); // true
        System.out.println(i1 == i3); // false
        System.out.println(i1.equals(i3)); // true
    }


    public static void main(String[] args) {

    }


}
