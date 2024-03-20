package com.ruoyi.framework.websocket;

import cn.hutool.core.util.ObjectUtil;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.framework.web.service.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author ruoyi
 */
@Slf4j
//通讯地址:ws://localhost:8080//websocket/token
@ServerEndpoint(value = "/websocket/{token}")
@Component
public class WebSocketServerMessage {
    private TokenService tokenService = SpringUtils.getBean(TokenService.class);

    /**
     * 存储session集合
     */
    private static ConcurrentHashMap<Long, Session> sessionMap = new ConcurrentHashMap<>();

    /**
     * 存储session集合
     */
    private static ConcurrentHashMap<Long, LoginUser> userMap = new ConcurrentHashMap<>();

    private final static Logger logger = LogManager.getLogger(WebSocketServerMessage.class);

    /**
     * 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的
     */

    private static int onlineCount = 0;

    /**
     * concurrent包的线程安全Map，用来存放每个客户端对应的MyWebSocket对象
     */
    private static ConcurrentHashMap<Long, WebSocketServerMessage> webSocketMap = new ConcurrentHashMap<>();

    /**
     * 与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    private Session session;
    private Long userId;


    /**
     * @param session
     * @param token
     * @description:连接建立成功调用的方法
     * @author: Mr.Kai
     * @time: 2023/4/3 15:48
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("token") String token) {
        System.out.println("★webSocket连接成功★,token为:" + token);
        LoginUser loginUser = tokenService.getLoginUser(token);
        if (ObjectUtil.isNull(loginUser)) {
            log.error("token失效或无法解析");
        }

        setMap(session, loginUser);
    }

    private void setMap(Session session, LoginUser loginUser) {
        //获取用户id
        Long userId = loginUser.getUserId();
        //存储会话到会话集合
        sessionMap.put(userId, session);
        //存储
        webSocketMap.put(userId, this);
        //存储用户信息到用户集合
        userMap.put(userId, loginUser);
        //获取会话长度(就是在线人数)
        int size = sessionMap.size();
        log.warn("用户连接:{},昵称:{},当前在线人数:{}", userId, loginUser.getUsername(), size);
    }


    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        System.out.println("★webSocket退出成功★");
        removeMap(session);
    }

    private void removeMap(Session session) {
        Long userId = getUserIdBySession(session);
        if (ObjectUtil.isNull(userId)) {
            return;
        }
        sessionMap.remove(userId);
        userMap.remove(userId);
        webSocketMap.remove(userId);
    }

    /**
     * 根据session拿到用户id
     *
     * @param session
     * @return
     */
    private Long getUserIdBySession(Session session) {
        for (Long userId : sessionMap.keySet()) {
            if (sessionMap.get(userId) != null && session != null
                    && sessionMap.get(userId).getId().equals(session.getId())) {
                return userId;
            }
        }
        return null;
    }


    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("★webSocket接收成功★内容为:" + message);
        if(StringUtils.equals("ping",message)){
            try {
                session.getBasicRemote().sendText("pong");
            } catch (IOException ignored) {
                log.error("发送给{}的消息出错", session.getId());
            }
        }
        LoginUser loginUser = getUserBySession(session);
        if (ObjectUtil.isNull(loginUser)) {
            return;
        }
        if (loginUser.getUser() != null) {
            //系统用户
            handlePCMsg(loginUser, message);
        } else {
            //app用户
            handleAPPMsg(loginUser, message);
        }
    }

    private void handleAPPMsg(LoginUser loginUser, String message) {
        log.info("APP用户:{},消息", loginUser.getUsername(), message);
    }

    private void handlePCMsg(LoginUser loginUser, String message) {
        log.info("系统用户:{},消息", loginUser.getUsername(), message);
    }

    private LoginUser getUserBySession(Session session) {
        Long userId = getUserIdBySession(session);
        if (ObjectUtil.isNull(userId)) {
            return null;
        }
        return userMap.get(userId);
    }


    /**
     * 发生错误时调用
     *
     * @OnError
     */
    @OnError
    public void onError(Session session, Throwable error) {
        logger.error("用户错误:" + this.userId + ",原因:" + error.getMessage());
        error.printStackTrace();
    }

    /**
     * 向客户端发送消息
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    /**
     * 通过userId向客户端发送消息
     */
    public void sendMessageByUserId(Long userId, String message) throws IOException {
        logger.info("服务端发送消息到{},消息：{}", userId, message);
        if (userId != null && webSocketMap.containsKey(userId)) {
            webSocketMap.get(userId).sendMessage(message);
        } else {
            logger.error("用户{}不在线", userId);
        }

    }

    /**
     * 群发自定义消息
     */
    public static void sendInfo(String message) throws IOException {
        for (Long item : webSocketMap.keySet()) {
            try {
                webSocketMap.get(item).sendMessage(message);
            } catch (IOException e) {
                continue;
            }
        }
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketServerMessage.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketServerMessage.onlineCount--;
    }


    /**
     * @param toUserId
     * @description:发送自定义消息方法
     * @author: Mr.Kai
     * @time: 2023/4/3 16:30
     */
    public static void sendInfo(String message, Long toUserId) {
        log.info("发送消息到:{},消息内容:{}", toUserId, message);
        if (ObjectUtil.isNull(toUserId) || StringUtils.isBlank(message)) {
            log.error("消息体不完整");
            return;
        }
        try {
            sendMessage(sessionMap.get(toUserId), message);
        } catch (Exception e) {
            log.error("发送给{}的消息出错", toUserId);
        }
    }

    public static void sendMessage(Session session, String message) throws IOException {
        session.getBasicRemote().sendText(message);
    }
}


