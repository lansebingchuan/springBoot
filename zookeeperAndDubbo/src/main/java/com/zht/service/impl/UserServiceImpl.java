package com.zht.service.impl;

import com.zht.context.CurrentUserHolder;
import com.zht.entity.User;
import com.zht.service.UserService;
import org.apache.dubbo.common.utils.StringUtils;
import org.springframework.boot.autoconfigure.klock.annotation.Klock;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ZHT
 */
@Service
public class UserServiceImpl implements UserService {

    @Override
    @Klock(keys = "zht")
    public User getUser() {
        return User.builder().name("张海涛").age(22).build();
    }

    @Override
    @Klock(waitTime = Long.MAX_VALUE)
    public User getUserByName(String name) throws InterruptedException {
        if ("zht".equals(name)){
            return User.builder().name("张海涛").age(22).build();
        }else {
            //线程等待 50 秒
            Thread.sleep(1000 * 5);
            return User.builder().name("张海涛11").age(22).build();
        }
    }

    @Override
    public User getUserAuthByLoginName(String loginName) {
        //模拟数据库查询服务
        if (!ObjectUtils.isEmpty(loginName) && StringUtils.isEquals("张海涛", loginName)){
            return User.builder().id(1).name("张海涛").age(22).password("123").build();
        }else {
            return null;
        }
    }

    @Override
    public User getUserAuthByJwt(String jwt) {
        //模拟数据库查询服务
        if (!ObjectUtils.isEmpty(jwt) && StringUtils.isEquals("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIn0.0Eji5S9teRgQYlLBxtTjUMZsops-6KDu_G8iHJweudA", jwt)){
            return User.builder().id(1).name("张海涛").age(22).password("20161047036").jwt("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIn0.0Eji5S9teRgQYlLBxtTjUMZsops-6KDu_G8iHJweudA").build();
        }else {
            return null;
        }
    }

    @Override
    public User getUserById(Integer id) {
        //模拟数据库查询服务
        if (!ObjectUtils.isEmpty(id) && id.equals(1)){
            User user = User.builder().id(1).name("张海涛").age(22).password("123").jwt("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIn0.0Eji5S9teRgQYlLBxtTjUMZsops-6KDu_G8iHJweudA").enableFlag("N").build();
            return user;
        }else {
            return null;
        }
    }

    @Override
    public List<User> listUser(User user) {
        Integer userId = CurrentUserHolder.getUserId();
        List<User> users = Arrays.asList(new User[]{
                User.builder().id(1).audit(1).name("张海涛1").password("123").build(),
                User.builder().id(2).audit(1).name("张海涛2").password("123").build(),
                User.builder().id(3).audit(1).name("张海涛3").password("123").build(),
                User.builder().id(4).audit(2).name("张海涛4").password("123").build(),
                User.builder().id(5).audit(2).name("张海涛5").password("123").build()
        });
        List<User> collect = users.stream().filter(user1 -> user1.getAudit().equals(userId)).collect(Collectors.toList());
        return collect;
    }
}
