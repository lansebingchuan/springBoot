package com.zht.enumConfig;

/**
 * 实现枚举类的接口
 *
 * @author ZHT
 */
public interface ConvertibleEnum<T> {


    /**
     * 获得枚举项唯一标识
     *
     * @return 唯一标识
     */
    T getId();

}
