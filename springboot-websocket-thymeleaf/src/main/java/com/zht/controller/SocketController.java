package com.zht.controller;

import com.alibaba.fastjson.JSON;
import com.zht.auth.WebSocketUserAuthentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

/**
 * <p>
 * 1、socket前端控制器
 * 2、对于订阅者
 *      （1）、对于个人订阅需要加上前缀。如：/user/userTest/callBack
 *      （2）、对于发送公共消息的，订阅者则不需要加（“/user”）。如: /userTest/[[${groupId}]]
 * 3、控制器的注解
 *      （1）、@SendTo：是发送给公共订阅此消息的人。如： /topicTest/hello
 *      （2）、@SendToUser:是发送给本人，因为在发送的时候回在（“/userTest/own”）
 *              前面加上（“/user/sessionId”）,变为（“/user/sessionId/userTest/own”）。订阅者就为-> “/user/userTest/own”）
 *       (3)、simpMessagingTemplate的使用，
 *          1、可以发送给某个人，使用： （“convertAndSendToUser(sessionId, 订阅地址 , 消息)”）
 *          2、可以发送给某公共，使用： （“convertAndSend(公共订阅地址, 消息体)”）
 * </p>
 *
 * @author ZHT
 * @since 2020/8/3
 */
@Controller
public class SocketController {

    private static final Logger log = LoggerFactory.getLogger(SocketController.class);

    private final SimpMessagingTemplate messagingTemplate;

    @RequestMapping("/springWebSocketStomp/{groupId}")
    public String springWebSocketStomp (@PathVariable String groupId, Model model, HttpSession session){
        // 这里封装一个登录的用户组参数，模拟进入通讯后的简单初始化
        model.addAttribute("groupId","user_groupId_"+ groupId);
        model.addAttribute("session_id",session.getId());
        System.out.println("跳转：" + session.getId());
        session.setAttribute("loginName",session.getId());
        return "/webSocket/springWebSocketStomp";
    }

    /**
     * 实例化Controller的时候，注入SimpMessagingTemplate
     * @param messagingTemplate 消息模板
     */
    @Autowired(required = false)
    public SocketController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }


    /**
     * 发送公共消息
     *      * 发送广播消息，所有订阅了此路径的用户都会收到此消息
     *      * 这里做个restful风格，其实无所谓，根据项目实际情况进行配置
     *      * restful风格的接口，在springMVC中，我们使用@PathVariable注解，
     *
     * @param groupId 组id
     * @param json 发送json字符串
     * @param stompHeaderAccessor 消息封装的实体
     * @return 推送到公共的消息
     */
    @MessageMapping("/sendChatMsg/{groupId}")
    @SendTo("/topicTest/hello")
    public Map<String, Object> sendMsg(@DestinationVariable(value = "groupId") String groupId, String json, StompHeaderAccessor stompHeaderAccessor){

        // 这里拿到的user对象是在MyPrincipalHandshakeHandler拦截器中绑定上的对象
        WebSocketUserAuthentication user = (WebSocketUserAuthentication) stompHeaderAccessor.getUser();

        log.info("公告controller 中获取用户登录令牌：" + user.getName());
        log.info("公告拿到客户端传递分组参数:" + groupId);
        // 这里拿到的json 字符串，其实可以自动绑定到对象上
        System.out.println("公告获取客户端传递过来的JSON 字符串：" + json);
        Map msg = (Map) JSON.parse(json);
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("msg", "公告服务器收到客户端请求，发送广播消息:"+ msg.get("msg"));
        return data;
    }

    /**
     * 自己推送给自己
     *      * 发送私信消息，只是想简单的用websocket向服务器请求资源而已，
     *      * 然后服务器你就把资源给我就行了，别的用户就不用你广播推送了，简单点，就是我请求，你就推送给我
     *      * 如果一个帐号打开了多个浏览器窗口，也就是打开了多个websocket session通道，
     *      * 这时，spring webscoket默认会把消息推送到同一个帐号不同的session，
     *      * 可以利用broadcast = false把避免推送到所有的session中
     *
     * @param json 消息字符串
     * @param stompHeaderAccessor 消息封装的实体
     * @return 推送给自己的消息
     */
    @MessageMapping("/sendChatMsgByOwn")
    @SendToUser("/userTest/own")
    public Map<String, Object> sendChatMsgByOwn(String json, StompHeaderAccessor stompHeaderAccessor){
        // 这里拿到的user对象是在WebSocketChannelInterceptor拦截器中绑定上的对象
        WebSocketUserAuthentication user = (WebSocketUserAuthentication) stompHeaderAccessor.getUser();

        log.info("私信controller 中获取用户登录令牌：" + user.getName());
        // 这里拿到的json 字符串，其实可以自动绑定到对象上
        System.out.println("私信获取客户端传递过来的JSON 字符串：" + json);
        Map msg = (Map) JSON.parse(json);
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("msg", "私信服务器收到客户端请求，发送私信消息:"+ msg.get("msg"));
        return data;
    }


    /**
     * 进行点对点的发送消息->发送给某一个人
     *
     * @param accountId 获取消息的一个人
     * @param json 发送的数据字符串
     * @param stompHeaderAccessor 封装的发送消息人对象
     * @return 发送消息成功
     */
    @MessageMapping("/sendChatMsgById/{accountId}")
    @SendToUser("/userTest/callBack")
    public Map<String, Object> sendChatMsgById(@DestinationVariable(value = "accountId") String accountId, String json, StompHeaderAccessor stompHeaderAccessor){
        WebSocketUserAuthentication user = (WebSocketUserAuthentication) stompHeaderAccessor.getUser();

        Map msg = (Map)JSON.parse(json);
        Map<String, Object> data = new HashMap<String, Object>();

        log.info("{} 给 {} 发送消息！", user.getName(), accountId);

        log.info("SendToUser controller 中获取用户登录令牌：" + user.getName()
                + " socketId:" + stompHeaderAccessor.getSessionId());

        // 向用户发送消息,第一个参数是接收人、第二个参数是浏览器订阅的地址，第三个是消息本身
        // 如果服务端要将消息发送给特定的某一个用户，
        // 可以使用SimpleMessageTemplate的convertAndSendToUser方法(第一个参数是用户的登陆名username)
        String address = "/userTest/callBack";
        messagingTemplate.convertAndSendToUser(accountId, address, msg.get("msg"));

        //这个是返回给自己的消息，表示消息以及推送的提示信息
        data.put("msg", "callBack 消息已推送，消息内容：" + msg.get("msg"));
        return data;
    }

    /**
     * 1、给指定的组发送消息
     * 2、返回消息发送成功（推送给自己）
     *
     * @param groupId 需要发送的组的id
     * @param json 发送的json字符串
     * @param stompHeaderAccessor 请求体的封装
     * @return 回调发送成功
     */
    @MessageMapping("/sendChatMsgByGroupId/{groupId}")
    @SendToUser("/userTest/callBack")
    public Map<String, Object> sendChatMsgByGroupId(@DestinationVariable(value = "groupId") String groupId, String json, StompHeaderAccessor stompHeaderAccessor){
        WebSocketUserAuthentication user = (WebSocketUserAuthentication) stompHeaderAccessor.getUser();

        Map msg = (Map)JSON.parse(json);
        Map<String, Object> data = new HashMap<String, Object>();

        log.info("{} 给 {} 组，发送消息！", user.getName(), groupId);

        // 向用户发送消息,第一个参数是接收人、第二个参数是浏览器订阅的地址，第三个是消息本身
        // 如果服务端要将消息发送给特定的某一个用户，
        // 可以使用SimpleMessageTemplate的convertAndSendToUser方法(第一个参数是用户的登陆名username)
        String address = "/userTest/"+ groupId;
        messagingTemplate.convertAndSend(address, msg.get("msg"));


        //这个是返回给自己的消息，表示消息以及推送的提示信息
        data.put("msg", "callBack 消息已推送，消息内容：" + msg.get("msg"));
        return data;
    }
}
