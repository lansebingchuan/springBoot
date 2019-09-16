package com.zht.websocket1;


import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/bitcoinServer")/////接受websocket请求路径
@Component//必须注册为一个bean
public class BitCoinServer {//统一接收服务
     
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    public Session session;
 
    @OnOpen
    public void onOpen(Session session){//每打开一个窗口连接，就好创建一个session
        this.session = session;
        ServerManager.add(session);//集中管理session服务
    }
    @OnClose
    public void onClose(){
        ServerManager.remove(this.session);
    }
 
    @OnMessage
    public void onMessage(String message, Session session) {//接收消息；当然也可以发送消息
        boolean constent = ServerManager.constent(session);//判断session是否存在
        if (constent){
            System.out.println("存在session");
        }else {
            System.out.println("不存在session");
        }
        System.out.println("来自客户端的消息:" + message);
    }
 
    @OnError
    public void onError(Session session, Throwable error){
        System.out.println("发生错误");
        error.printStackTrace();
    }
 
}