package com.zht.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * 抽象的mybatis、service封装了mybatis-plus
 * @author ZHT
 */
public abstract class AbstractCrudService<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> {

}

