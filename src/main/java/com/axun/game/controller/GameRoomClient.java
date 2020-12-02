package com.axun.game.controller;

import com.axun.game.utils.SessionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;


@ServerEndpoint("/chatroom/{token}")
@Component
public class GameRoomClient {
    private static int players = 4;
    private String token;
    private Logger log = LoggerFactory.getLogger(GameRoomClient.class);
    private boolean isGameing = false;
    private int cur_player = 0;

    @OnOpen
    public void onOpen(@PathParam("token")String token, Session session) throws IOException {
        this.token = token;
        SessionUtils.sessions.put(token,session);
        log.info("打开新链接:{}", token);
    }

    @OnClose
    public void onlose() {
        log.info("链接关闭:{}", this.token);
    }

    /**
     * 主动向客户端发送推送
     * @param message
     * @param session
     * @throws IOException
     */
    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        log.info("收到用户{}的新消息{}", token, message);
        session.getBasicRemote().sendText( "用户："+ message);
        // 正在游戏中
//        String[] messages = message.split(",");
//        Integer status = messages[0].charAt(messages[0].length() - 1) - '0';

//        if(isGameing){
//            // 前端发来游戏结束的请求
//            if(message){
//                this.isGameing = false;
//            }
//            for(Map.Entry<String, Session> e: SessionUtils.sessions.entrySet()){
//                e.getValue().getAsyncRemote().sendText(e.getKey() + "收到消息：" + message);
//            }
//        }else{
//            // 前端发来的开始游戏的请求
//            if(message){
//                this.cur_player++;
//            }
//            if(cur_player == players){
//                this.cur_player = 0;
//                this.isGameing = true;
//            }
//        }
//      0表示请求游戏准备，1表示游戏正在进行，2表示游戏结束
//      status:0,user_id:16,row1:2 0 0 0,row2:0 0 0 0,row3:0 0 0 0,row4:0 0 0 0
    }

    @OnError
    public void onError(Session session, Throwable error) {
        log.info("用户{}链接出现错误", token);
        error.printStackTrace();
    }

    public static void main(String[] args) {
        String message = "status:0,user_id:16,row1:2 0 0 0,row2:0 0 0 0,row3:0 0 0 0,row4:0 0 0 0";
        String[] messages = message.split(",");
        Integer status = messages[0].charAt(messages[0].length() - 1) - '0';
        System.out.println(status);
    }
}
