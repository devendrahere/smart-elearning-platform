package com.edusmart.service;

import com.edusmart.dto.DiscussionDTO;
import com.edusmart.dto.DiscussionThreadDTO;

import java.util.List;

public interface DiscussionService {
    DiscussionDTO createDiscussion(DiscussionDTO discussionDTO);
    List<DiscussionDTO> getDiscussionsByCourse(Long courseId);
    List<DiscussionDTO> getDiscussionsByUser(Long userId);
    void deleteDiscussion(Long discussionId, Long actingUserId);
    DiscussionThreadDTO createThread(Long courseId, String title, Long createdBy);
    List<DiscussionThreadDTO> getThreadByCourse(Long courseId);
    List<DiscussionDTO> getMessageByThread(Long threadId);
    DiscussionDTO postMessage(Long threadId, DiscussionDTO messageDTO);
    DiscussionThreadDTO getThreadById(Long threadId);
    void deleteThread(Long threadId,Long actingUserId);
}
