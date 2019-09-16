package com.zht.websocket;

/**
 * @author zht
 * @create 2019-09-03 16:11
 */
public class WebServerName {

    private String name;//客户端发送来的消息

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public WebServerName(String name) {
        this.name = name;
    }

    public WebServerName() {
    }
}
