package com.zht.entity;

import lombok.Builder;
import lombok.Data;

/**
 * @author ZHT
 */
@Data
@Builder
public class User {

    private String name;

    private Integer age;

    private String password;
}
