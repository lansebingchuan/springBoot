package com.zht.service.impl;

import com.zht.entity.User;
import com.zht.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 *
 * </p>
 *
 * @author ZHT
 * @since 2020/6/14
 */
@Service
public class UserServiceImpl implements UserService {


    @Override
    public User getUserByUserName(String userName) {
        User user01 = User.builder().userId(1).loginName("张三").password("123").build();
        User user02 = User.builder().userId(1).loginName("张海涛").password("$2a$10$emiSciAFUhwS36OjewcxourdVX1gENirZdGW09TPml2j24jdIphRG").build();
        if (StringUtils.equals(userName, user01.getLoginName())) {
            return user01;
        } else if (StringUtils.equals(userName, user02.getLoginName())) {
            return user02;
        }
        return null;
    }
}
