package com.zht.intercepter;


import com.zht.util.rides.RedisUtils;
import org.junit.Test;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zht
 * @create 2019-09-05 15:03
 */
public class LoginInterceptor implements HandlerInterceptor {

    private static final  String REMOTEHOSTNAME = "_session";//redis 主机名后缀
    private static final  int COUNT = 20;//  限制登录次数


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        String remoteHost = request.getRemoteHost();//获取主机地址
        request.getSession();//创建session
        remoteHost += REMOTEHOSTNAME;
        Integer i = (Integer)RedisUtils.getObject(remoteHost);
        if (i != null){//没有进行访问
            i+=1;
            if (i > COUNT){
                System.out.println("次数超出");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().print("次数超出");
                if (i == (COUNT + 1 )){//超出限制，设置过期时间
                    ///设置了 key 的值得时候，那么 设置的过期时间将会取消 ，所以要么先设置值，要么设置值，并且设置时间
                    //方法1
                    //RedisUtils.setObject(remoteHost, i);
                    //RedisUtils.expired(remoteHost, 10);//设置 KEY 过期的值，为 5
                    RedisUtils.setObject(remoteHost, i, 10);//设置值，并且和过期时间
                }
                return false;//拦截取消
            }
            RedisUtils.setObject(remoteHost, i);
        }else {
            i = 1;
            RedisUtils.setObject(remoteHost, i);
        }
        System.out.println("第 "+i+"次进入");
        return true;//拦截通过
    }
}
