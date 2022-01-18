package com.test.demo.designMode.builder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: ZSY
 * @program: multipleTest
 * @date 2022/1/18 9:47
 * @description: item类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Item {
    //必填
    private String itemName;
    //必填
    private Integer type;
    //卡券必填
    private String code;
    //视频必填
    private String url;
}
