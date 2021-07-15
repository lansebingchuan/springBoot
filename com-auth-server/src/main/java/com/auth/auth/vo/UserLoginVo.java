package com.auth.auth.vo;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

/**
 * <p> 用户注册vo </p>
 *
 * @author: ZHT
 * @create: 2021-07-12 10:30
 **/
@Data
public class UserLoginVo {

    @NotEmpty(message = "密码不能为空！")
    @Length(min = 3, max = 6, message = "密码长度为3-6位")
    private String password;

    @NotEmpty(message = "用户名不能为空！")
    private String username;
    
}
