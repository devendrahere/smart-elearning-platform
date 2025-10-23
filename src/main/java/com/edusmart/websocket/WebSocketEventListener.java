package com.edusmart.websocket;


import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class WebSocketEventListener {

    private static final Logger logger= LoggerFactory.getLogger(WebSocketEventListener.class);

    private static final Set<String> activeUsers= ConcurrentHashMap.newKeySet();

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event){
        StompHeaderAccessor accessor= StompHeaderAccessor.wrap(event.getMessage());

        String sessionId=accessor.getSessionId();

        String username=accessor.getUser() !=null ? accessor.getUser().getName() :sessionId;

        activeUsers.add(username);

        logger.info("websocket connected : User : {} (SessionId:{})",username ,sessionId);

        messagingTemplate.convertAndSend("/topic/system",username+" connected");
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event){
        StompHeaderAccessor accessor=StompHeaderAccessor.wrap(event.getMessage());

        String sessionId=accessor.getSessionId();
        String username=accessor.getUser() !=null ?accessor.getUser().getName():sessionId;

        activeUsers.remove(username);

        logger.info("websocket disconnected : User : {} (SessionId: {})",username,sessionId);

        messagingTemplate.convertAndSend("/topic/system",username+" disconnected");
    }

    private Set<String> getActiveUsers(){
        return activeUsers;
    }
}
