package com.edusmart.service.ui;

import com.edusmart.dto.DiscussionDTO;
import com.edusmart.dto.DiscussionThreadDTO;
import com.edusmart.service.DiscussionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UIDiscussionService {
    @Autowired
    private DiscussionService discussionService;

    public List<DiscussionDTO> getDiscussionByCourse(Long courseID){
        return discussionService.getDiscussionsByCourse(courseID);
    }

    public List<DiscussionDTO> getDiscussionsByUser(Long userID){
        return discussionService.getDiscussionsByUser(userID);
    }

    public List<DiscussionThreadDTO> getThreadByCourse(Long courseId){
        return discussionService.getThreadByCourse(courseId);
    }

    public List<DiscussionDTO> getMessageByThread(Long threadId){
        return discussionService.getMessageByThread(threadId);
    }
}
