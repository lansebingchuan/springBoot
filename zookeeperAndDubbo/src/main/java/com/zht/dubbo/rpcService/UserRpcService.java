package com.zht.dubbo.rpcService;

import com.zht.entity.User;

/**
 * @author ZHT
 */
public interface UserRpcService {

    /**
     * rpc 接口获取用户
     * @return
     */
    User getUser();


    /**
     * 根据姓名获取user
     * @param name
     * @return
     */
    User getUserByName(String name) throws InterruptedException;
}
