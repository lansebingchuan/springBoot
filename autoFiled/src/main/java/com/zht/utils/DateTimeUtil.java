package com.zht.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author zhanghaitao
 * @date 2020/3/20 0020
 */
public class DateTimeUtil {

    /**
     * 把LocalDateTime转换为字符串 日期格式见-DatePattern-工具类
     * @param localDateTime
     * @param format
     * @return
     */
    public static String formatLocalDateTime(LocalDateTime localDateTime, String format){
        return DateTimeFormatter.ofPattern(format).format(localDateTime);
    }

}
