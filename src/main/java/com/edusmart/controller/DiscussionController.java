package com.edusmart.controller;

import com.edusmart.dto.DiscussionDTO;
import com.edusmart.service.DiscussionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/discussions")
public class DiscussionController {

    @Autowired
    private DiscussionService discussionService;

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

    @DeleteMapping("/{discussionId}")
    public ResponseEntity<Void> deleteDiscussion(@PathVariable Long discussionId){
        discussionService.deleteDiscussion(discussionId);
        return ResponseEntity.noContent().build();
    }
}
