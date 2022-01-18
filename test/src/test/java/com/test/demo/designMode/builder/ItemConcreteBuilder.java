package com.test.demo.designMode.builder;

/**
 * @author: ZSY
 * @program: multipleTest
 * @date 2022/1/18 9:54
 * @description:
 *
 * 第二步：创建具体建造者类。对抽象建造者类的抽象方法进行实现赋值，达到我们所需要的结果。
 */
public class ItemConcreteBuilder extends ItemBuilder {

    @Override
    public void buildNormal() {
        item.setItemName("普通商品");
        item.setType(1);
    }

    @Override
    public void buildCard() {
        item.setItemName("卡券商品");
        item.setCode("123456");
        item.setType(2);
    }

    @Override
    public void buildVideo() {
        item.setItemName("视频商品");
        item.setType(3);
        item.setUrl("https://www.baidu.com");
    }
}
