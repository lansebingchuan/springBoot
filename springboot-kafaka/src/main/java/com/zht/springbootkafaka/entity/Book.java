package com.zht.springbootkafaka.entity;

import lombok.*;

/**
 * <p>
 * 书的实体类
 * </p>
 *
 * @author ZHT
 * @since 2020/8/1
 */
@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    /**
     * 书的名称
     */
    private String name;

    /**
     * 书的价格
     */
    private Float price;

}
