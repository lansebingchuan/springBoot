package com.zht.annotation;

import cn.hutool.core.date.DatePattern;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author zhanghaitao
 * @date 2020/3/20 0020
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface FormatDateTime {

    String format() default DatePattern.NORM_DATETIME_PATTERN;

    String ref() default  "";

}
