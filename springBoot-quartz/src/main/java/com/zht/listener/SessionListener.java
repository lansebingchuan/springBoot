package com.zht.listener;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * @author zht
 * @create 2019-09-05 15:49
 */
@WebListener
public class SessionListener implements HttpSessionListener {

    public static int online = 0;

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        httpSessionEvent.getSession().setMaxInactiveInterval(60*10);//设置session时间
        System.out.println("session创建，过期剩余："+httpSessionEvent.getSession().getMaxInactiveInterval()+"s");
        online+=1;
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        System.out.println("session销毁");
        if (online > 0){
            online-=1;
        }
    }
}
