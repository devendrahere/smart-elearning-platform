package com.edusmart.controller;

import com.edusmart.dto.DiscussionDTO;
import com.edusmart.entity.Discussions;
import com.edusmart.service.DiscussionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class DiscussionWebSocketController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private DiscussionService discussionService;

    @MessageMapping("/discussion/{threadId}")
    public void sendMessage(@DestinationVariable Long threadId, @Payload DiscussionDTO message) {
        if (message.getUserId() == null || message.getUsername() == null || message.getUsername().isBlank()) {
            return; // Ignore invalid messages instead of throwing exception
        }
        DiscussionDTO saved = discussionService.postMessage(threadId, message);
        messagingTemplate.convertAndSend("/topic/discussion/" + threadId, saved);
    }


    @MessageMapping("/course/{courseId}/new-thread")
    public void notifyNewThread(
            @DestinationVariable Long courseId,
            @Payload String threadTitle){
        messagingTemplate.convertAndSend("/topic/course/"+courseId+"/threads",threadTitle);
    }

}
