package com.zht.controller;

import com.zht.websocket.WebServerContent;
import com.zht.websocket.WebServerName;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.HtmlUtils;

/**
 * @author zht
 * @create 2019-09-03 15:03
 */
@Controller
public class WebScokeController {

    @MessageMapping("/hello")//客户端请求的地址
    @SendTo("/topic/greetings")//发送到那个地址
    public WebServerContent greeting(WebServerName message) throws Exception {//message为客户端发送来的信息
        //Thread.sleep(1000); // 线程等待，模拟异步
        return new WebServerContent("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");//服务器返回的信息
    }

    @RequestMapping("/webserver")
    public String webserver(){
        return "webserver";
    }

}
