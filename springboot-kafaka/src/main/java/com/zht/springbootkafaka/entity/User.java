package com.zht.springbootkafaka.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 用户实体类
 * </p>
 *
 * @author ZHT
 * @since 2020/8/1
 */
@Data
public class User implements Serializable {

    /**
     * 用户姓名
     */
    private String name;

    /**
     * 用户密码
     */
    private Integer age;

}
