package com.edusmart.service.ui;

import com.edusmart.dto.AnnouncementDTO;
import com.edusmart.service.AnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UIAnnouncementService {

    @Autowired
    private AnnouncementService announcementService;

    public List<AnnouncementDTO> getAnnouncementsForCourse(Long courseId) {
        return announcementService.getAnnouncementsForCourse(courseId);
    }

    public AnnouncementDTO createAnnouncement(Long courseId, Long instructorId, String message) {
        return announcementService.createAnnouncement(courseId, message, instructorId);
    }

    public boolean isInstructorOfCourse(Long courseId, Long userId) {
        return announcementService.isInstructorOfCourse(courseId, userId);
    }

    public void deleteAnnouncement(Long courseId, Long announcementId, Long userId) {
        announcementService.deleteAnnouncement(courseId, announcementId, userId);
    }
}