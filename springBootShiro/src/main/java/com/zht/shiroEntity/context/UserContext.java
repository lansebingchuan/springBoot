package com.zht.shiroEntity.context;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author ZHT
 */
@Data
@ToString
public class UserContext implements Serializable {

    /**
     * 用户名
     */
    private String name;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 职务
     */
    private String job;
}
