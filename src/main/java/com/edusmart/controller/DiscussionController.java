package com.edusmart.controller;

import com.edusmart.dto.DiscussionDTO;
import com.edusmart.dto.DiscussionThreadDTO;
import com.edusmart.service.DiscussionService;
import com.edusmart.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/discussions")
public class DiscussionController {

    @Autowired
    private DiscussionService discussionService;

    @Autowired
    private UserService userService;

    //url /api/discussion
    @PostMapping("/")
    public ResponseEntity<DiscussionDTO> createDiscussion(@RequestBody DiscussionDTO discussion){
        return ResponseEntity.status(HttpStatus.CREATED).body(discussionService.createDiscussion(discussion));
    }

    //url /api/discussion/{courseId}
    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<DiscussionDTO>> getDiscussionByCourse(@PathVariable Long courseId){
        return ResponseEntity.ok(discussionService.getDiscussionsByCourse(courseId));
    }

    //url /api/discussion/{userId}
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<DiscussionDTO>> getDiscussionByUser(@PathVariable Long userId){
        return ResponseEntity.ok(discussionService.getDiscussionsByUser(userId));
    }

    // delete discussion (enforce permission using authenticated user)
    @DeleteMapping("/{discussionId}")
    public ResponseEntity<Void> deleteDiscussion(@PathVariable Long discussionId, Authentication authentication){
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        var user = userService.getUserByUsername(authentication.getName());
        try {
            discussionService.deleteDiscussion(discussionId, user.getUserId());
        } catch (AccessDeniedException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{courseId}/threads")
    public ResponseEntity<DiscussionThreadDTO> createThread(
            @PathVariable Long courseId,
            @RequestParam String title,
            Authentication authentication
    ) {
        // Ensure user is authenticated
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        var user = userService.getUserByUsername(authentication.getName());
        DiscussionThreadDTO thread = discussionService.createThread(courseId, title, user.getUserId());
        return ResponseEntity.status(HttpStatus.CREATED).body(thread);
    }

    @PostMapping("/thread/{threadId}/messages")
    public ResponseEntity<DiscussionDTO> postMessages(
            @PathVariable Long threadId,
            @RequestBody DiscussionDTO dto
    ){
        DiscussionDTO saved=discussionService.postMessage(threadId,dto);
        return new ResponseEntity<>(saved,HttpStatus.CREATED);
    }

    @GetMapping("/thread/{threadId}/message")
    public ResponseEntity<List<DiscussionDTO>> getMessageByThread(
            @PathVariable Long threadId
    ){
        List<DiscussionDTO> dtos=discussionService.getMessageByThread(threadId);
        return ResponseEntity.ok(dtos);
    }

    // delete a thread (only the creator can delete)
    @DeleteMapping("/thread/{threadId}")
    public ResponseEntity<Void> deleteThread(
            @PathVariable Long threadId,
            Authentication authentication) {

        // Ensure user is logged in
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // Get logged-in user details
        var user = userService.getUserByUsername(authentication.getName());

        try {
            discussionService.deleteThread(threadId, user.getUserId());
            return ResponseEntity.noContent().build(); // 204 on success
        } catch (AccessDeniedException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build(); // 403 if not creator
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); // 400 fallback
        }
    }


}