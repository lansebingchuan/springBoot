package com.zht.service;

import com.zht.entity.User;

public interface UserService {

    void addUser(User user);

    User getUserById(Long userId);

    User findUserByName(String 张海涛);

    void removeById(long l);

    void updateById(User userUpdate);
}
