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
public class UserRegVo {
    
    @NotEmpty(message = "用户名不能为空！")
    @Length(min = 3, max = 6, message = "用户名长度为3-6位")
    private String username;

    @NotEmpty(message = "密码不能为空！")
    @Length(min = 3, max = 6, message = "密码长度为3-6位")
    private String password;

    @NotEmpty(message = "验证码不能为空！")
    @Length(min = 4, max = 4, message = "验证码长度为3-6位")
    private String code;

    @NotEmpty(message = "手机号不能为空！")
    @Length(min = 3, max = 6, message = "手机号长度为3位")
    @Pattern(regexp = "^[1]([3-9])[0-9]{9}$", message = "手机号格式不正确")
    private String phone;
    
}
