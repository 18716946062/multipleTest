package com.test.demo.designMode.builder;

/**
 * @author: ZSY
 * @program: multipleTest
 * @date 2022/1/18 9:45
 * @description:
 *
 * 第一步：创建我们的抽象建造者类。这里面我们看下有三个抽象方法，来确定不同的商品类型，我们调用不同的方法，达到解偶的思想
 */
public abstract class ItemBuilder {
    // 创建产品对象
    protected Item item = new Item();

    public abstract void buildNormal();

    public abstract void buildCard();

    public abstract void buildVideo();

    // 返回对象类
    public Item getResult() {
        return item;
    }
}
