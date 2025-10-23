package com.edusmart.controller;

import com.edusmart.service.UserPresenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("api/presence")
@CrossOrigin(origins = "*")
public class UserPresenceController {

    @Autowired
    private UserPresenceService userPresenceService;

    @GetMapping("/{courseId}")
    public ResponseEntity<Set<Long>> getOnlineUsers(
            @PathVariable Long courseId
    ){
        Set<Long> onlineUsers=userPresenceService.getOnlineUsers(courseId);
        return ResponseEntity.ok(onlineUsers);
    }

}
