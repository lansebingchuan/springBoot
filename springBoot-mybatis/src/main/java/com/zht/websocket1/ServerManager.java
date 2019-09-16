package com.zht.websocket1;

import javax.websocket.EncodeException;
import javax.websocket.Session;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class ServerManager {
 
    private static Collection<Session> sessions = Collections.synchronizedCollection(new ArrayList<>());
     
    public static void broadCast(String msg) {//全部发送消息
        for (Session session : sessions) {
            try {
                session.getBasicRemote().sendText(msg);////发送消息
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
     
    public static int getTotal(){
        return sessions.size();
    }//session获取连接总数

    public static void add(Session session){//客户端连接时添加
        System.out.println("有新连接加入！ 当前总连接数是："+ (sessions.size()+1));
        sessions.add(session);
    }
    public static void remove(Session session){//移除这个session
        sessions.remove(session);
        System.out.println("有连接退出！ 当前总连接数是："+ sessions.size());
    }

    public static boolean constent(Session s) {//查看是否包含  这个客户端
        for (Session session : sessions) {
            if (session == s){
                return true;
            }
        }
        return false;
    }
}