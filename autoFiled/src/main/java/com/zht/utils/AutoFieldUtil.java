package com.zht.utils;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReflectUtil;
import com.zht.annotation.FormatDateTime;
import com.zht.annotation.SysLog;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * @author zhanghaitao
 * @date 2020/3/20 0020
 */
@Component
public class AutoFieldUtil {

    @SysLog("系统日志")
    public static void objautoField(Object target){
        Field[] fields = target.getClass().getDeclaredFields();
        Arrays.stream(fields).forEach(field -> {
            FormatDateTime formatDateTime = field.getDeclaredAnnotation(FormatDateTime.class);
            if (ObjectUtil.isNotNull(formatDateTime)) {
                String format = formatDateTime.format();
                String ref = formatDateTime.ref();
                Assert.notNull(format);
                Assert.notNull(ref);
                LocalDateTime localDateTime = (LocalDateTime) ReflectUtil.getFieldValue(target, ref);
                String localDateTimeStr = DateTimeUtil.formatLocalDateTime(localDateTime, format);
                ReflectUtil.setFieldValue(target, field, localDateTimeStr);
            }
        });
    }
}
