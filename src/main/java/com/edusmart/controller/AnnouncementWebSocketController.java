package com.edusmart.controller;

import com.edusmart.dto.AnnouncementDTO;
import com.edusmart.service.AnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class AnnouncementWebSocketController {
    @Autowired
    private AnnouncementService announcementService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping
    public void  broadcastAnnouncement(
            @DestinationVariable Long courseId,
            @Payload AnnouncementDTO dto
            ){
        AnnouncementDTO saved=announcementService.createAnnouncement(courseId,dto.getMessage(),dto.getPostedBy());
        messagingTemplate.convertAndSend("/topic/announcement/"+courseId,saved);
    }
}
