package com.zht.dubbo.provider;

import com.zht.dubbo.rpcService.UserRpcService;
import com.zht.entity.User;
import com.zht.service.UserService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * dubbo rpc远程调用接口实现类
 *
 * @author ZHT
 */
@Service
@Component
public class UserRpcServiceImpl implements UserRpcService {

    @Autowired
    private UserService userService;

    @Override
    public User getUser(){
        return userService.getUser();
    }

    @Override
    public User getUserByName(String name) throws InterruptedException {
        return userService.getUserByName(name);
    }

    @Override
    public User getUserAuthByLoginName(String loginName) {
        return userService.getUserAuthByLoginName(loginName);
    }

    @Override
    public User getUserAuthByJwt(String jwt) {
        return userService.getUserAuthByJwt(jwt);
    }

    @Override
    public User getUserById(Integer id) {
        return userService.getUserById(id) ;
    }

    @Override
    public List<User> listUser(User user) {
        return userService.listUser(user) ;
    }
}
