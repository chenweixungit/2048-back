package com.axun.game.controller;


import com.axun.game.constants.StatusEnum;
import com.axun.game.entity.ChatMessage;
import com.axun.game.entity.GameMessage;
import com.axun.game.entity.MessageReply;
import com.axun.game.response.CommonReturnType;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


@Controller
public class GameController extends BaseController{


    // 存储用户状态

    private Vector<String> players = new Vector<>();

    private ConcurrentHashMap<String,Integer> playerStatus = new ConcurrentHashMap<>();

    private Integer curPlayers = 0;
    // 判断游戏是否在进行中
    private boolean isGameing = false;



    @MessageMapping("/startGame")
    @SendTo("/topic/game")
    public MessageReply addUser(@Payload String name, SimpMessageHeaderAccessor headerAccessor) throws JsonProcessingException {
        if(!players.contains(name)){
            curPlayers++;
            players.add(name);
            playerStatus.put(name,1);
        }
        MessageReply messageReply = new MessageReply();
        messageReply.setPlayers(players);
        if(curPlayers == 4){
            messageReply.setisGame(true);
            isGameing = true;
        }else if(curPlayers < 4){
            messageReply.setisGame(false);
        }else if(curPlayers >= 5){
            return null;
        }
        return messageReply;
        //        MessageReply message = new MessageReply();
//        String name = chatMessage.getName();
//        CommonReturnType result = new CommonReturnType();
//
//        if (userToStatus.containsKey(sender)) {
//            message.setCode(201);
//            message.setStatus("该用户名已存在");
//            message.setChatMessage(result);
////            log.warn("addUser[" + sender + "]: " + message.toString());
//        } else {
//            result.setContent(mapper.writeValueAsString(userToStatus.keySet().stream().filter(k -> userToStatus.get(k).equals(StatusEnum.IDLE)).toArray()));
//            message.setCode(200);
//            message.setStatus("成功");
//            message.setChatMessage(result);
//            userToStatus.put(sender, StatusEnum.IDLE);
//            headerAccessor.getSessionAttributes().put("username",sender);
////            log.warn("addUser[" + sender + "]: " + message.toString());
//        }
//        return null;
    }



    @MessageMapping("/gameing")
    @SendTo("/topic/game")
    public GameMessage doExam(@Payload GameMessage gameMessage) throws JsonProcessingException {
        Integer status = gameMessage.getStatus();
        String name = gameMessage.getName();
        if(playerStatus.get(name) == 1 && status == 0){
            curPlayers--;
            playerStatus.put(name,0);
        }
        if(curPlayers == 0){
            for(String player:players){
                playerStatus.remove(player);
            }
            players.clear();
        }
        return gameMessage;
//        MessageReply message = new MessageReply();
//        String sender = chatMessage.getSender();
//        String receiver = userToPlay.get(sender);
//        ChatMessage result = new ChatMessage();
//        result.setType(MessageTypeEnum.DO_EXAM);
////        log.warn("userToStatus:" + mapper.writeValueAsString(userToStatus));
//        if (userToStatus.containsKey(receiver) && userToStatus.get(receiver).equals(StatusEnum.IN_GAME)) {
//            result.setContent(chatMessage.getContent());
//            result.setSender(sender);
//            result.setReceiver(Collections.singletonList(receiver));
//            message.setCode(200);
//            message.setStatus("成功");
//            message.setChatMessage(result);
////            log.warn("doExam[" + receiver + "]: " + message.toString());
//        }else{
//            result.setReceiver(Collections.singletonList(sender));
//            message.setCode(203);
//            message.setStatus("该用户不存在或已退出游戏");
//            message.setChatMessage(result);
////            log.warn("doExam[" + sender + "]: " + message.toString());
//        }
//        return null;
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String username = (String) headerAccessor.getSessionAttributes().get("username");
        if(username != null) {
//            log.info("User Disconnected : " + username);
//            userToStatus.remove(username);
//            userToPlay.remove(username);
        }
    }
}