package com.zht.context;

import cn.hutool.core.util.ObjectUtil;
import com.zht.shiroEntity.context.UserContext;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

/**
 * @author ZHT
 */
public class CurrentUserHolder {

    /**
     * 通过shiro内部获取当前登录用户信息
     *
     * @return 登录用户信息
     */
    public static UserContext getUser(){
        Object principal = SecurityUtils.getSubject().getPrincipal();
        if (principal instanceof UserContext){
            return (UserContext) principal;
        }
        return null;
    }

    /**
     * 通过shiro内部获取当请求用户信息
     *
     * @return 用户上下文
     */
    public static UserContext getRequiredUser(){
        UserContext user = getUser();
        if (ObjectUtil.isNull(user)){
            throw new NullPointerException();
        }
        return user;
    }

    /**
     * 获取登录人姓名
     *
     * @return 登录人姓名
     */
    public static String getUserName(){
        UserContext requiredUser = getRequiredUser();
        return requiredUser.getName();
    }
}
