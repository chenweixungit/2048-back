package com.axun.game.service.impl;

import com.axun.game.entity.PushMessage;
import com.axun.game.service.SocketIOService;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 */
@Service(value = "socketIOService")
public class SocketIOServiceImpl implements SocketIOService {

    // 用来存已连接的客户端
    private static Map<String, SocketIOClient> clientMap = new ConcurrentHashMap<>();
    private static Map<String,String[]> gameRooms = new ConcurrentHashMap<>();
    private static Integer curPlayers = 0;
    private static String roomName = "";
    private static String[] roomClients = new String[4];
//    private static synchronized Integer players = 0;
    @Autowired
    private SocketIOServer socketIOServer;

    /**
     * Spring IoC容器创建之后，在加载SocketIOServiceImpl Bean之后启动
     *
     * @throws Exception
     */
    @PostConstruct
    private void autoStartup() throws Exception {
        start();
    }

    /**
     * Spring IoC容器在销毁SocketIOServiceImpl Bean之前关闭,避免重启项目服务端口占用问题
     *
     * @throws Exception
     */
    @PreDestroy
    private void autoStop() throws Exception {
        stop();
    }

    @Override
    public void start() throws Exception {
        // 监听客户端连接
        socketIOServer.addConnectListener(client -> {
            String loginUserNum = getParamsByClient(client);
            if (loginUserNum != null) {
                System.out.println(loginUserNum);
                System.out.println("SessionId:  " + client.getSessionId());
                System.out.println("RemoteAddress:  " + client.getRemoteAddress());
                System.out.println("Transport:  " + client.getTransport());
                clientMap.put(loginUserNum, client);
            }
        });

        // 监听客户端断开连接
        socketIOServer.addDisconnectListener(client -> {
            String loginUserNum = getParamsByClient(client);
            if (loginUserNum != null) {
                clientMap.remove(loginUserNum);
                System.out.println("断开连接： " + loginUserNum);
                System.out.println("断开连接： " + client.getSessionId());
                client.disconnect();
            }
        });

        // 处理自定义的事件，与连接监听类似
        // 处理正在游戏中的信息
        socketIOServer.addEventListener("gameing", Object.class, (client, data, ackSender) -> {
            // TODO do something
            client.getHandshakeData();
            Map<String,Object> map = (Map<String, Object>) data;
            String room = map.get("room").toString();
            String[] players = gameRooms.get(room);
            for(String player: players){
                clientMap.get(player).sendEvent(PUSH_EVENT,data);
            }
            System.out.println(" 正在游戏中：************ " + "游戏信息已转发 " + data.toString());
        });


        // 处理自定义的事件，与连接监听类似
        // 处理游戏结束的信息
        socketIOServer.addEventListener("endGame", Object.class, (client, data, ackSender) -> {
            // TODO do something
            client.getHandshakeData();
            Map<String,Object> map = (Map<String, Object>) data;
            String room = map.get("room").toString();
            if(gameRooms.get(room) != null){
                gameRooms.remove(room);
            }
            System.out.println(" 游戏结束：************ " + "游戏房间已销毁 " + room);
        });

        // 处理请求游戏连接的请求
        socketIOServer.addEventListener("startGame", Object.class, (client, data, ackSender) -> {
            // TODO do something
            client.getHandshakeData();
            Map<String,String> map =  (Map<String, String>) data;
            if(curPlayers == 0){
                roomClients = new String[4];
                roomClients[curPlayers++] = map.get("name");
            }else if(curPlayers == 3){
                roomClients[3] = map.get("name");
                Date date = new Date();
                String roomName = date.toString().replace(" ","");
                gameRooms.put(roomName,roomClients);
                Map<String,Object> startMessage = new HashMap<>();
                startMessage.put("room",roomName);
                startMessage.put("players",roomClients);
                for(String s : roomClients){
                    clientMap.get(s).sendEvent(START_GAME,startMessage);
                }
                curPlayers = 0;
                roomClients = null;
                System.out.println("房间号: "+roomName+ " 游戏已经开始");
            }else{
                roomClients[curPlayers++] = map.get("name");
            }
            System.out.println(" 客户端：************ " + data);
        });

        socketIOServer.addEventListener("comment", Object.class, (client, data, ackSender) -> {
            // TODO do something
            client.getHandshakeData();
            Map<String,Object> map = (Map<String, Object>) data;
            String room = map.get("room").toString();
            String[] players = gameRooms.get(room);
            for(String player: players){
                clientMap.get(player).sendEvent(PUSH_COMMENT,data);
            }
            System.out.println(" 正在游戏中：************ " + "弹幕信息已转发 " + data.toString());
        });

        socketIOServer.start();
    }


    @Override
    public void stop() {
        if (socketIOServer != null) {
            socketIOServer.stop();
            socketIOServer = null;
        }
    }

    @Override
    public void pushMessageToUser(PushMessage pushMessage) {
        String loginUserNum = String.valueOf(pushMessage.getLoginUserNum());
        if (StringUtils.isNotBlank(loginUserNum)) {
            SocketIOClient client = clientMap.get(loginUserNum);
            if (client != null)
                client.sendEvent(PUSH_EVENT, pushMessage);
        }
    }

    /**
     * 此方法为获取client连接中的参数，可根据需求更改
     *
     * @param client
     * @return
     */
    private String getParamsByClient(SocketIOClient client) {
        // 从请求的连接中拿出参数（这里的loginUserNum必须是唯一标识）
        Map<String, List<String>> params = client.getHandshakeData().getUrlParams();
        List<String> list = params.get("loginUserNum");
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    public static Map<String, SocketIOClient> getClientMap() {
        return clientMap;
    }

    public static void setClientMap(Map<String, SocketIOClient> clientMap) {
        SocketIOServiceImpl.clientMap = clientMap;
    }


    public static void main(String[] args) {
        Date date = new Date();
        String s = date.toString().replace(" ","");

        System.out.println(s);
    }
}