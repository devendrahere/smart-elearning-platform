package com.edusmart.service;

import com.edusmart.dto.AnnouncementDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AnnouncementService {
    AnnouncementDTO createAnnouncement(Long courseId, String message, Long instructorId);
    List<AnnouncementDTO> getAnnouncementsForCourse(Long courseId);
    void deleteAnnouncement(Long courseId, Long announcementId, Long userId);
    boolean isInstructorOfCourse(Long courseId, Long userId);
}
