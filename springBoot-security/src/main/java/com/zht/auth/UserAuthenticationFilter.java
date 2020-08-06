package com.zht.auth;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p>
 * 安全框架过滤器
 * </p>
 *
 * @author ZHT
 * @since 2020/6/14
 */
public class UserAuthenticationFilter extends AbstractAuthenticationProcessingFilter {


    /**
     * 默认登录表单
     */
    public UserAuthenticationFilter() {
        super("/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {
        //登录名
        String username = httpServletRequest.getParameter("username");
        //密码
        String password = httpServletRequest.getParameter("password");
        //什么权限
        //String auth = httpServletRequest.getParameter(FORM_AUTH_KEY);

        if(username == null) {
            username = "";
        }

        if(password == null) {
            password = "";
        }
        //什么权限
     /*   if(auth == null) {
            auth = "";
        }*/

        username = username.trim();
        password = password.trim();
        //auth = auth.trim();

        // 将需要的参数用 / 拼接起来传递进入spring security
        //String param = username + "," + auth;
        String param = username;

        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(param, password);

        authRequest.setDetails(authenticationDetailsSource.buildDetails(httpServletRequest));

        return this.getAuthenticationManager().authenticate(authRequest);
    }
}
