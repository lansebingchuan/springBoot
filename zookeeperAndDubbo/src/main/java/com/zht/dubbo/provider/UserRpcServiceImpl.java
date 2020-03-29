package com.zht.dubbo.provider;

import com.zht.dubbo.rpcService.UserRpcService;
import com.zht.entity.User;
import com.zht.service.UserService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * dubbo rpc远程调用接口实现类
 *
 * @author ZHT
 */
@Component
@Service
public class UserRpcServiceImpl implements UserRpcService {

    @Autowired
    UserService userService;

    @Override
    public User getUser(){
        return userService.getUser();
    }

    @Override
    public User getUserByName(String name) throws InterruptedException {
        return userService.getUserByName(name);
    }
}
