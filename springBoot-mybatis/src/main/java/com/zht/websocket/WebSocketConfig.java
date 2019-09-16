package com.zht.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * @author zht
 * @create 2019-09-03 16:00
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        //以启用一个简单的基于内存的消息代理，以在前缀为“/ topic”的目标上将问候消息传回客户端
        config.enableSimpleBroker("/topic");//客户端请求消息的路径
        //指定“/ app”前缀。此前缀将用于定义所有消息映射
        config.setApplicationDestinationPrefixes("/app");//客户端发送消息给服务器，之前的  stompClient.send("/app/hello",)以App开头
    }

//    启用SockJS后备选项，以便在WebSocket不可用时可以使用备用传输。SockJS客户端将尝试
// 连接到“/ gs-guide-websocket”并使用可用的最佳传输（websocket，xhr-streaming，xhr-polling等）
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/gs-guide-websocket").withSockJS();//客户端连接的webserver
    }

}
