package com.test.demo.designMode.builder;

/**
 * @author: ZSY
 * @program: multipleTest
 * @date 2022/1/18 9:59
 * @description:
 *
 * 第三步：创建我们的导演类。指导我们怎么去创建对象，这个我们是可以简化的，视具体使用场景确定吧！
 */
public class ItemDirector {

    private ItemBuilder itemBuilder;

    public ItemDirector(ItemBuilder itemBuilder) {
        this.itemBuilder = itemBuilder;
    }

    public Item normalConstruct() {
        itemBuilder. buildNormal();
        return itemBuilder.getResult();
    }

    public Item cardConstruct() {
        itemBuilder.buildCard();
        return itemBuilder.getResult();
    }

    public Item videoConstruct() {
        itemBuilder.buildVideo();
        return itemBuilder.getResult();
    }
}
