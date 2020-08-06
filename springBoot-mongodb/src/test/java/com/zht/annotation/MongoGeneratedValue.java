package com.zht.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>
 * mongo 自增序列注解
 * </p>
 *
 * @author zht
 * @since 2019-07-22
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface MongoGeneratedValue {
}
