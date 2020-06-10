package com.zht.context;

import com.zht.entity.User;
import org.apache.shiro.SecurityUtils;

/**
 * 当前用户管理类
 *
 * @author ZHT
 */
public class CurrentUserHolder {

    /**
     * 当前用户在线程中的上下文
     */
    private static final ThreadLocal<User> CONTEXT = new ThreadLocal<>();

    /**
     * 获取当前登录人用户
     *
     * @return 用户
     */
    public static User getUser(){
        if (CONTEXT.get() == null){
            Object principal = SecurityUtils.getSubject().getPrincipal();
            if (principal instanceof User) {
                CONTEXT.set((User)principal);
            }
        }
        return CONTEXT.get();
    }

    /**
     * 获取当前登录人用户id
     *
     * @return 用户id
     */
    public static Integer getUserId(){
        return getUser().getId();
    }

    /**
     * 获取当前登录人用户id
     *
     */
    public static void setContext(User user){
        CONTEXT.set(user);
    }


    /**
     *清空当前用户
     */
    public static void clear(){
        CONTEXT.remove();
    }

}
