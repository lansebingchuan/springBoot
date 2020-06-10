package com.zht.auth.filter;

import cn.hutool.extra.servlet.ServletUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zht.auth.token.JwtToken;
import com.zht.dto.ApiResult;
import com.zht.exception.ErrorCode;
import org.apache.dubbo.common.utils.StringUtils;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * shiro过滤器，验证 jwt令牌登录是否有效
 *
 * @author ZHT
 */
public class JwtFilter extends AuthorizationFilter {



    /**
     * 日志记录
     */
    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 请求前拦截，获取http请求的头部“Authorization”
     * @param request
     * @param response
     * @param mappedValue
     * @return
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        HttpServletRequest httpRequest = WebUtils.toHttp(request);
        String authorization = httpRequest.getHeader("Authorization");
        if (StringUtils.isBlank(authorization)) {
            return false;
        }
        JwtToken jwtToken = new JwtToken(authorization);
        try {
            getSubject(request, response).login(jwtToken);
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        HttpServletResponse httpResponse = WebUtils.toHttp(response);
        ObjectMapper mapper = new ObjectMapper();
        String responseBody = mapper.writeValueAsString(ApiResult.error(ErrorCode.AUTH_FAILED));
        ServletUtil.write(httpResponse, responseBody, "application/json; charset=utf-8");
        return false;
    }


    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = WebUtils.toHttp(request);
        HttpServletResponse httpServletResponse = WebUtils.toHttp(response);
        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return false;
        }
        return super.preHandle(request, response);
    }

}
