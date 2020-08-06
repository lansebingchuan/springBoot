package com.zht.entity;

import lombok.Builder;
import lombok.Data;

/**
 * <p>
 * 用户实体类
 * </p>
 *
 * @author ZHT
 * @since 2020/8/2
 */
@Data
@Builder
public class UserEntity {

    /**
     * 姓名
     */
    private String name;

    /**
     * 年龄
     */
    private int age;
}
