package com.test.entity.vo;

import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author: ZSY
 * @program: test
 * @date 2022/1/4 8:55
 * @description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVO {

    private String name;
    private String realname;
    private Integer age;
    private String moblie;
    private String email;
    private Integer id;
    private Date createTime;
}
