package com.zht.websocket;

/**
 * @author zht
 * @create 2019-09-03 16:16
 */
public class WebServerContent {//服务器返回的问候语

    private String content;

    public String getContent() {//返回的
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public WebServerContent(String content) {
        this.content = content;
    }

    public WebServerContent() {
    }
}
