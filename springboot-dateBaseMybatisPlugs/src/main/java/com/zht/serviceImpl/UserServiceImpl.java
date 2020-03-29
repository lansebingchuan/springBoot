package com.zht.serviceImpl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zht.base.AbstractCrudService;
import com.zht.entity.User;
import com.zht.mapper.UserMapper;
import com.zht.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @author ZHT
 */
@Service
@DS("master")
public class UserServiceImpl extends AbstractCrudService<UserMapper, User> implements UserService {

    @Override
    public User getUser(User user){
        return this.getById(user.getId());
    }
}
