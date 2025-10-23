package com.edusmart.events;

import com.edusmart.service.UserPresenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
public class WebSocketPresenceEventListener {
    @Autowired
    private UserPresenceService userPresenceService;

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectEvent event){
        StompHeaderAccessor accessor=StompHeaderAccessor.wrap(event.getMessage());
        Long userId=parseLong(accessor.getFirstNativeHeader("userId"));
        Long courseId=parseLong(accessor.getFirstNativeHeader("courseId"));

        if(userId !=null && courseId!=null){
            userPresenceService.userJoined(courseId,userId);
            System.out.println("User "+userId+" joined Course "+courseId);
        }
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event){
        StompHeaderAccessor accessor=StompHeaderAccessor.wrap(event.getMessage());
        Long userId=parseLong(accessor.getFirstNativeHeader("userId"));
        Long courseId=parseLong(accessor.getFirstNativeHeader("courseId"));

        if(userId!=null && courseId!=null){
            userPresenceService.userLeft(courseId,userId);
            System.out.println("User "+userId+" Left course "+courseId);
        }
    }

    private Long parseLong(String value){
        try {
            return (value!=null) ?Long.parseLong(value) :null;
        }catch (NumberFormatException e){
            return null;
        }
    }
}
