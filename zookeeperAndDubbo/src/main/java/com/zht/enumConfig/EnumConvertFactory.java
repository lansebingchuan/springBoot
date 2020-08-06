package com.zht.enumConfig;

import org.apache.dubbo.common.utils.StringUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

/**
 * 枚举类配置类
 *
 * @author ZHT
 */
public class EnumConvertFactory implements ConverterFactory<String, ConvertibleEnum> {

    @Override
    public <T extends ConvertibleEnum> Converter<String, T> getConverter(Class<T> targetType) {
        return new StringToConvertibleEnum<>(targetType);
    }

    private static class StringToConvertibleEnum<T extends ConvertibleEnum> implements Converter<String, T> {

        private Class<T> targetType;

        private StringToConvertibleEnum(Class<T> targetType) {
            this.targetType = targetType;
        }

        @Override
        public T convert(String source) {
            if (StringUtils.isBlank(source)) {
                return null;
            }
            //如果参数值与枚举项的id值相等则返回该枚举项
            for (T enumObj : targetType.getEnumConstants()) {
                if (source.equals(String.valueOf(enumObj.getId()))) {
                    return enumObj;
                }
            }
            return null;
        }
    }

}
