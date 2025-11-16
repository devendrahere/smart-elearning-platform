package com.edusmart.controller;


import com.edusmart.dto.AnnouncementDTO;
import com.edusmart.service.AnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/announcements")
@CrossOrigin(origins = "*")
public class AnnouncementController {

    @Autowired
    private AnnouncementService announcementService;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;


    @PostMapping("/{courseId}")
    public ResponseEntity<AnnouncementDTO> createAnnouncement(
            @PathVariable Long courseId,
            @RequestParam Long instructorId,
            @RequestParam String message
    ){
            AnnouncementDTO saved= announcementService.createAnnouncement(courseId,message,instructorId);
            simpMessagingTemplate.convertAndSend("/topic/announcement/"+courseId,saved);
            return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<List<AnnouncementDTO>> getAnnouncementsForCourse(
            @PathVariable Long courseId
    ){
        List<AnnouncementDTO> announcements=announcementService.getAnnouncementsForCourse(courseId);
        return ResponseEntity.ok(announcements);
    }
}
