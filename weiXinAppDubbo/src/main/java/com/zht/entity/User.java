package com.zht.entity;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ZHT
 */
@Data
@Builder
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;

    private Integer age;
}
