package com.zht.entity;

import com.zht.annotation.MongoGeneratedValue;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

@Data
@Document("user")
@Builder
@ToString
public class User implements Serializable {

    @MongoGeneratedValue
    @Id
    private Long id;

    @Field
    private String name;

    @Field
    private Integer age;
}
