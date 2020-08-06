package com.zht.interceptor;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * <p>
 *  0、可以判断是否登录
 *  1、WebSocket握手请求的拦截器. 检查握手请求和响应, 对WebSocketHandler传递属性
 *  2、在握手之前执行该方法, 继续握手返回true, 中断握手返回false. 通过attributes参数设置WebSocketSession的属性
 * </p>
 *
 * @author ZHT
 * @since 2020/8/3
 */
public class WebSocketHandshakeInterceptor implements HandshakeInterceptor {

    @Override
    public boolean beforeHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Map<String, Object> map) throws Exception {
        HttpSession session = getSession(serverHttpRequest);
        //这里添加属性，为session。之后在通道中通过（“StompHeaderAccessor 的 getSessionAttributes().get("HTTP_SESSION") ”）获取当前session
        map.put("HTTP_SESSION", session);
        System.out.println("握手前置处理！~");
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Exception e) {
        System.out.println("握手后置处理！~");
    }

    private HttpSession getSession(ServerHttpRequest request) {
        if (request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest serverRequest = (ServletServerHttpRequest) request;
            return serverRequest.getServletRequest().getSession(false);
        }
        return null;
    }

}
