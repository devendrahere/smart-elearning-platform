package com.edusmart.service;

import com.edusmart.dto.DiscussionDTO;

import java.util.List;

public interface DiscussionService {
    DiscussionDTO createDiscussion(DiscussionDTO discussionDTO);
    List<DiscussionDTO> getDiscussionsByCourse(Long courseId);
    List<DiscussionDTO> getDiscussionsByUser(Long userId);
    void deleteDiscussion(Long discussionId);
}
