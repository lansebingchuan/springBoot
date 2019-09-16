package com.zht;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author zht
 * @create 2019-09-10 16:30
 */
@Target({ElementType.FIELD , ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface StudentName {

    String sql() default "" ;
}
