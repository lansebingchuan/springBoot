package com.zht.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 *  用户实体类
 * </p>
 *
 * @author ZHT
 * @since 2020/6/14
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    /**
     * 登录名
     */
    private String loginName;

    /**
     * 密码
     */
    private String password;

    /**
     * 用户主键
     */
    private Integer userId;

}
