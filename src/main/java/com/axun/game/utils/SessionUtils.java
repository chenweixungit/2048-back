package com.axun.game.utils;

import org.springframework.stereotype.Component;

import javax.websocket.Session;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SessionUtils {
    public static Map<String, Session> sessions = new ConcurrentHashMap<>();

}
