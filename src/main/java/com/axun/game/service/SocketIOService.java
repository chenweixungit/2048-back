package com.axun.game.service;


import com.axun.game.entity.PushMessage;

/**
 * 功能描述
 *
 * @author: zyu
 * @description:
 * @date: 2019/4/23 10:41
 */
public interface SocketIOService {

    //推送的事件
    public static final String PUSH_EVENT = "push_event";

    //推送弹幕
    public static final String PUSH_COMMENT = "push_comment";

    //开始游戏
    public static final String START_GAME = "start_game";

    //结束游戏
    public static final String END_GAME = "end_game";

    // 启动服务
    void start() throws Exception;

    // 停止服务
    void stop();

    // 推送信息
    void pushMessageToUser(PushMessage pushMessage);

}
