package com.zht.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author ZHT
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer id;

    private String name;

    private Integer age;

    private String password;

    private String jwt;

    /**
     * 是否被禁用
     */
    private String enableFlag;

    /**
     * 审核人
     */
    private Integer audit;
}
