package com.zht.enumConfig;

import cn.hutool.core.date.DateUtil;
import org.apache.dubbo.common.utils.StringUtils;
import org.checkerframework.framework.qual.Covariant;
import org.springframework.core.convert.converter.Converter;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * 字符串转LocalDateTime类
 *
 * @author ZHT
 */
public class StringToLocalDateTimeConverter implements Converter<String, LocalDateTime> {

    @Override
    public LocalDateTime convert(String source) {
        if (StringUtils.isBlank(source)) {
            return null;
        }
        Date date = DateUtil.parse(source);
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }
}
